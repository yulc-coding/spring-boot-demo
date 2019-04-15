package org.ylc.frame.springbootdemo.util;

import org.ylc.frame.springbootdemo.base.UserInfo;

/**
 * 线程本地变量
 * 用于存放用户基本信息
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/4/15 21:52
 */
public class ThreadLocalUtils {

    private static final ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();

    public static void setUser(UserInfo userInfo) {
        threadLocal.set(userInfo);
    }

    public static UserInfo getUser() {
        return threadLocal.get();
    }

    public static String getUserId() {
        return getUser().getUserId();
    }

    public static void clearAll() {
        threadLocal.remove();
    }

}
