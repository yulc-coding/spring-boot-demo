package org.ylc.frame.springboot.api.biz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.ylc.frame.springboot.api.biz.mapper.UserMapper;
import org.ylc.frame.springboot.api.setting.component.redis.RedisUtils;
import org.ylc.frame.springboot.common.constant.CacheConst;
import org.ylc.frame.springboot.common.entity.User;
import org.ylc.frame.springboot.common.service.UserService;
import org.ylc.frame.springboot.common.util.JWTUtils;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 登出
     * 1、解析token
     * 2、清除token
     * 3、清除用户的权限缓存
     *
     * @param token token信息
     */
    @Async
    @Override
    public void logout(String token) {
        if (StringUtils.isEmpty(token)) {
            return;
        }
        JSONObject tokenJson = JWTUtils.parseJWT(token);
        if (tokenJson == null) {
            return;
        }
        String userId = tokenJson.getString("userId");
        String loginFrom = tokenJson.getString("loginFrom");
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(loginFrom)) {
            return;
        }
        redisUtils.del(CacheConst.USER_TOKEN_PREFIX + userId + ":" + loginFrom);
        redisUtils.del(CacheConst.USER_PERMISSION_PREFIX + userId + ":" + loginFrom);
    }
}
