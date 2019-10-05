package org.ylc.frame.springboot.setting.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.ylc.frame.springboot.biz.mapper.MenuMapper;
import org.ylc.frame.springboot.common.annotation.Permission;
import org.ylc.frame.springboot.common.base.UserInfo;
import org.ylc.frame.springboot.common.constant.CacheConst;
import org.ylc.frame.springboot.common.constant.ConfigConst;
import org.ylc.frame.springboot.common.exception.CheckException;
import org.ylc.frame.springboot.common.util.IpUtil;
import org.ylc.frame.springboot.common.util.JWTUtils;
import org.ylc.frame.springboot.common.util.ParamCheck;
import org.ylc.frame.springboot.common.util.ThreadLocalUtils;
import org.ylc.frame.springboot.setting.component.mongodb.dao.SystemLogDao;
import org.ylc.frame.springboot.setting.component.mongodb.entity.SystemLog;
import org.ylc.frame.springboot.setting.component.redis.RedisUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * AOP请求统计
 * 请求日志信息处理
 * mongodb 记录请求相关信息
 * 调用耗时等，用于分析接口优化
 * <p>
 * 有权限注解的进行权限校验
 * 本地解析token，有效后进行权限校验
 * 缓存里面没有权限数据的，从数据库中获取权限列表信息
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/9/24
 */
@Aspect
@Component
public class WebLogAspect {

    private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    private final MenuMapper menuMapper;

    private final SystemLogDao systemLogDao;

    private final RedisUtils redisUtils;

    public WebLogAspect(MenuMapper menuMapper, SystemLogDao systemLogDao, RedisUtils redisUtils) {
        this.menuMapper = menuMapper;
        this.systemLogDao = systemLogDao;
        this.redisUtils = redisUtils;
    }


    @Pointcut("execution(public org.ylc.frame.springboot.common.base.HttpResult org.ylc.frame.springboot.biz.*controller..*(..))")
    public void webLog() {
    }

    /**
     * 在切点之前织入
     */
    @Before("webLog()")
    public void doBefore() {
        if (logger.isDebugEnabled()) {
            logger.info("========================================== Start ==========================================");
        }
    }

    /**
     * 在切点之后织入
     */
    @After("webLog()")
    public void doAfter() {
        ThreadLocalUtils.clearAll();
        if (logger.isDebugEnabled()) {
            logger.info("=========================================== End ===========================================");
            logger.info("");
        }
    }

    /**
     * 环绕
     * 权限验证
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        ParamCheck.notNull(attributes, "无效请求");
        HttpServletRequest request = attributes.getRequest();

        // 查看是否有注解
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        Permission userPermission = method.getAnnotation(Permission.class);
        // 权限验证
        if (userPermission != null && !StringUtils.isEmpty(userPermission.value())) {
            String token = request.getHeader("token");
            if (token == null) {
                throw new CheckException("非法操作", ConfigConst.RETURN_RESULT.ACCESS_RESTRICTED);
            }
            checkTokenAndPermission(token, userPermission.value());
        }

        long methodStart = System.currentTimeMillis();
        // 执行具体方法
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();

        // MongoDB 保存请求信息
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(ThreadLocalUtils.getUserDefalutNull());
        systemLog.setUrl(request.getRequestURL().toString());
        systemLog.setHttpMethod(request.getMethod());
        systemLog.setController(proceedingJoinPoint.getSignature().getDeclaringTypeName());
        systemLog.setMethod(proceedingJoinPoint.getSignature().getName());
        systemLog.setIp(IpUtil.getIpAddr(request));
        systemLog.setArgs(filterArgs(proceedingJoinPoint.getArgs()));
        systemLog.setMethodTime(endTime - methodStart);
        systemLog.setAllTime(endTime - startTime);
        systemLog.setDate(new Date());
        systemLogDao.saveAsync(systemLog);

        if (logger.isDebugEnabled()) {
            // 打印请求 url
            logger.info("URL            : {}", systemLog.getUrl());
            // 打印 Http method
            logger.info("HTTP Method    : {}", systemLog.getHttpMethod());
            // 打印调用 controller 的全路径以及执行方法
            logger.info("Controller     : {}", systemLog.getController());
            // 调用方法
            logger.info("Method         : {}", systemLog.getMethod());
            // 打印请求的 IP
            logger.info("IP             : {}", systemLog.getIp());
            // 打印请求入参
            logger.info("Request Args   : {}", systemLog.getArgs());
            // 执行耗时
            logger.info("method-time    : {} ms", systemLog.getMethodTime());
            logger.info("all-time       : {} ms", systemLog.getAllTime());
        }
        return result;
    }


    /**
     * 校验token 和权限
     * 1、解析token:
     * token是否合法；
     * token是否还未过期；
     * <p>
     * 2、权限校验
     * 从redis 中获取权限列表
     * 没有查到，再从数据库查询权限列表，并放入缓存
     * 判断当前权限是否在权限列表中
     * 更新token过期时间
     *
     * @param token      token
     * @param permission 访问权限
     */
    private void checkTokenAndPermission(String token, String permission) {
        // 解密token
        JSONObject tokenJson = JWTUtils.parseJWT(token);
        if (tokenJson == null) {
            throw new CheckException("非法操作", ConfigConst.RETURN_RESULT.ACCESS_RESTRICTED);
        }
        Long userId = tokenJson.getLong("userId");
        String loginFrom = tokenJson.getString("loginFrom");
        Map<Object, Object> redisMap = redisUtils.hashEntries(CacheConst.USER_TOKEN_PREFIX + userId + ":" + loginFrom);
        if (redisMap == null) {
            throw new CheckException("长时间未使用，登录已过期，请重新登录", ConfigConst.RETURN_RESULT.TOKEN_EXPIRED);
        }

        // 权限校验
        Object redisPermissions = redisUtils.get(CacheConst.USER_PERMISSION_PREFIX + userId + ":" + loginFrom);
        List<String> permissions;
        if (redisPermissions == null) {
            logger.info("redis没有员工权限缓存，从数据库查询 >>>>>>");
            permissions = menuMapper.getUserPermissions(userId, loginFrom);
            // 加入缓存
            redisUtils.set(CacheConst.USER_PERMISSION_PREFIX + userId + ":" + loginFrom, permissions);
        } else {
            // noinspection unchecked
            permissions = (List<String>) redisPermissions;
        }
        if (CollectionUtils.isEmpty(permissions) || !permissions.contains(permission)) {
            throw new CheckException("非法操作", ConfigConst.RETURN_RESULT.ACCESS_RESTRICTED);
        }

        // 保存用户信息
        String account = redisMap.get("account") == null ? null : String.valueOf(redisMap.get("account"));
        String userName = redisMap.get("userName") == null ? null : String.valueOf(redisMap.get("userName"));
        String depId = redisMap.get("depId") == null ? null : String.valueOf(redisMap.get("depId"));
        UserInfo userInfo = new UserInfo(userId, account, userName, depId);
        ThreadLocalUtils.setUser(userInfo);

        // 更新token过期时间
        updateTokenExpireTime(userId, loginFrom);
    }

    /**
     * 更新token过期时间
     *
     * @param userId    用户ID
     * @param loginFrom 登入方式
     */
    private void updateTokenExpireTime(Long userId, String loginFrom) {
        long expireTime = System.currentTimeMillis();
        if (ConfigConst.LOGIN_PC.equals(loginFrom)) {
            expireTime += ConfigConst.DEFAULT_PC_TOKEN_INVALID_TIME;
        } else if (ConfigConst.LOGIN_APP.equals(loginFrom)) {
            expireTime += ConfigConst.DEFAULT_APP_TOKEN_INVALID_TIME;
        }
        redisUtils.expire(CacheConst.USER_TOKEN_PREFIX + userId + ":" + loginFrom, expireTime);
    }


    /**
     * 序列化时过滤掉request、response和文件类型
     */
    private String filterArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return null;
        }
        List<Object> logArgs = Arrays.stream(args)
                .filter(arg -> (!(arg instanceof HttpServletRequest) &&
                        !(arg instanceof HttpServletResponse) &&
                        !(arg instanceof MultipartFile)
                ))
                .collect(Collectors.toList());
        return JSONObject.toJSONString(logArgs);
    }

}
