package org.ylc.frame.springbootdemo.util;

import org.springframework.util.Assert;
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

    /**
     * 获取用户信息
     */
    public static UserInfo getUser() {
        return threadLocal.get();
    }

    /**
     * 获取用户ID
     */
    public static String getUserId() {
        UserInfo userInfo = getUser();
        Assert.notNull(userInfo, "未登录");
        return getUser().getUserId();
    }

    /**
     * 清除所有数据
     */
    public static void clearAll() {
        threadLocal.remove();
    }

}
