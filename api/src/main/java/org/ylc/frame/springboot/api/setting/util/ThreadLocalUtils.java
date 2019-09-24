package org.ylc.frame.springboot.api.setting.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.ylc.frame.springboot.common.base.UserInfo;
import org.ylc.frame.springboot.common.util.ParamCheck;

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

    private static final ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();

    public static void setUser(UserInfo userInfo) {
        threadLocal.set(userInfo);
        // 把用户信息放到log4j
        MDC.put(KEY_USER, userInfo.getUserId());
    }

    /**
     * 获取用户信息
     */
    public static UserInfo getUser() {
        UserInfo userInfo = threadLocal.get();
        ParamCheck.notNull(userInfo, "未登录");
        return userInfo;
    }

    /**
     * 获取用户ID
     */
    public static String getUserId() {
        return getUser().getUserId();
    }

    /**
     * 清除所有数据
     */
    public static void clearAll() {
        threadLocal.remove();
    }

}
