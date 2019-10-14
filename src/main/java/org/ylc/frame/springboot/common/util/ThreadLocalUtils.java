package org.ylc.frame.springboot.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.ylc.frame.springboot.common.entity.UserInfo;

/**
 * 线程本地变量
 * 用于存放用户基本信息
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/4/15 21:52
 */
public class ThreadLocalUtils {

    private static final Logger logger = LoggerFactory.getLogger(ThreadLocalUtils.class);

    private static final String KEY_USER = "user";

    private static final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();

    public static void setUser(UserInfo userInfo) {
        THREAD_LOCAL.set(userInfo);
        // 把用户信息放到log4j
        MDC.put(KEY_USER, userInfo.getUserId().toString());
    }

    /**
     * 获取用户信息
     */
    public static UserInfo getUser() {
        UserInfo userInfo = THREAD_LOCAL.get();
        ParamCheck.notNull(userInfo, "未登录");
        return userInfo;
    }

    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        return getUser().getUserId();
    }

    /**
     * 获取用户ID，如果没有则返回0
     */
    public static Long getUserIdDefaultZero() {
        UserInfo userInfo = THREAD_LOCAL.get();
        if (userInfo == null) {
            return 0L;
        }
        return userInfo.getUserId();
    }


    /**
     * 获取用户ID，没有返回NULL
     *
     * @return userId
     */
    public static Long getUserIdDefaultNull() {
        UserInfo userInfo = THREAD_LOCAL.get();
        return userInfo == null ? null : userInfo.getUserId();
    }

    /**
     * 清除所有数据
     */
    public static void clearAll() {
        THREAD_LOCAL.remove();
    }

}
