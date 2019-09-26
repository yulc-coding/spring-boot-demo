package org.ylc.frame.springboot.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.ylc.frame.springboot.common.base.UserInfo;

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
        MDC.put(KEY_USER, userInfo.getUserId());
    }

    /**
     * 获取用户信息
     */
    public static UserInfo getUser() {
        return THREAD_LOCAL.get();
    }

    /**
     * 获取用户ID
     */
    public static String getUserId() {
        UserInfo userInfo = getUser();
        ParamCheck.notNull(userInfo, "未登录");
        return userInfo.getUserId();
    }

    /**
     * 清除所有数据
     */
    public static void clearAll() {
        THREAD_LOCAL.remove();
    }

}
