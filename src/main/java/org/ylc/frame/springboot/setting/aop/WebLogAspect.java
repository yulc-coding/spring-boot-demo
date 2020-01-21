package org.ylc.frame.springboot.setting.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.ylc.frame.springboot.biz.common.entity.UserInfo;
import org.ylc.frame.springboot.component.mongodb.dao.SystemLogDao;
import org.ylc.frame.springboot.component.mongodb.entity.SystemLog;
import org.ylc.frame.springboot.component.redis.RedisUtils;
import org.ylc.frame.springboot.constant.CacheConstants;
import org.ylc.frame.springboot.constant.ConfigConstants;
import org.ylc.frame.springboot.setting.annotation.Permission;
import org.ylc.frame.springboot.setting.exception.CheckException;
import org.ylc.frame.springboot.util.IpUtil;
import org.ylc.frame.springboot.util.JWTUtils;
import org.ylc.frame.springboot.util.ParamCheck;
import org.ylc.frame.springboot.util.ThreadLocalUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

    private final SystemLogDao systemLogDao;

    private final RedisUtils redisUtils;

    public WebLogAspect(SystemLogDao systemLogDao, RedisUtils redisUtils) {
        this.systemLogDao = systemLogDao;
        this.redisUtils = redisUtils;
    }

    @Pointcut("execution(public org.ylc.frame.springboot.biz.common.entity.HttpResult org.ylc.frame.springboot.biz.*.controller..*(..))")
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

        Long userId = null;
        String loginFrom = null;
        // 权限验证
        if (userPermission != null && !StringUtils.isEmpty(userPermission.value())) {
            String token = request.getHeader("token");
            if (token == null) {
                throw new CheckException("非法操作", ConfigConstants.Return.ACCESS_RESTRICTED);
            }
            // 1 校验 Token
            JSONObject tokenJson = checkToken(token);
            userId = tokenJson.getLong("userId");
            loginFrom = tokenJson.getString("loginFrom");
            // 2 校验权限
            checkPermission(userPermission.value(), userId, loginFrom);
            // 3 保存用户信息
            saveThreadLocal(tokenJson);
            // 4 更新Token和权限缓存时间
            updateTokenAndPermissionExpireTime(userId, loginFrom);
        }

        long methodStart = System.currentTimeMillis();
        // 执行具体方法
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();

        // MongoDB 保存请求信息
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(userId);
        systemLog.setLoginFrom(loginFrom);
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
     * 校验Token
     * <p>
     * token是否合法；
     * token是否还未过期；
     *
     * @param token 传入token
     * @return json
     */
    private JSONObject checkToken(String token) {
        // 解密token
        JSONObject tokenJson = JWTUtils.parseJWT(token);
        if (tokenJson == null) {
            throw new CheckException("非法操作", ConfigConstants.Return.ACCESS_RESTRICTED);
        }
        // Redis是否过期
        String redisToken = redisUtils.get(CacheConstants.USER_TOKEN_PREFIX + tokenJson.getLong("userId") + ":" + tokenJson.getString("loginFrom"));
        if (redisToken == null) {
            throw new CheckException("长时间未使用，登录已过期，请重新登录", ConfigConstants.Return.TOKEN_EXPIRED);
        }
        return tokenJson;
    }

    /**
     * 校验权限
     * <p>
     * 从redis权限集合中查询是否存在对应的权限
     *
     * @param permission 接口权限值
     * @param userId     用户ID
     * @param loginFrom  登录方式
     */
    private void checkPermission(String permission, Long userId, String loginFrom) {
        if (!redisUtils.memberInSet(CacheConstants.USER_PERMISSION_PREFIX + userId + ":" + loginFrom, permission)) {
            throw new CheckException("非法操作", ConfigConstants.Return.ACCESS_RESTRICTED);
        }
    }

    /**
     * 将用户信息保存到本地线程变量中
     *
     * @param tokenJson userInfo
     */
    private void saveThreadLocal(JSONObject tokenJson) {
        // 保存用户信息
        UserInfo userInfo = new UserInfo(
                tokenJson.getLong("userId"),
                tokenJson.getString("username"),
                tokenJson.getString("name"),
                tokenJson.getString("depCode")
        );
        ThreadLocalUtils.setUser(userInfo);
    }

    /**
     * 更新token和权限的过期时间
     *
     * @param userId    用户ID
     * @param loginFrom 登入方式
     */
    private void updateTokenAndPermissionExpireTime(Long userId, String loginFrom) {
        long expireTime = 0;
        if (ConfigConstants.LOGIN_PC.equals(loginFrom)) {
            expireTime = ConfigConstants.DEFAULT_PC_TOKEN_INVALID_TIME;
        } else if (ConfigConstants.LOGIN_APP.equals(loginFrom)) {
            expireTime = ConfigConstants.DEFAULT_APP_TOKEN_INVALID_TIME;
        }
        redisUtils.expire(CacheConstants.USER_TOKEN_PREFIX + userId + ":" + loginFrom, expireTime);
        redisUtils.expire(CacheConstants.USER_PERMISSION_PREFIX + userId + ":" + loginFrom, expireTime);
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
