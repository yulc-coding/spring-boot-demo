package org.ylc.frame.springboot.common.constant;

/**
 * 缓存常量
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/7/6 20:23
 */
public class CacheConstants {

    /**
     * redis 默认过期时间（1天）
     */
    public static final Long DEFAULT_REDIS_EXPIRE = 1000 * 60 * 60 * 24L;

    /**
     * token 前缀 -> 前缀:用户ID:登入方式
     */
    public static final String USER_TOKEN_PREFIX = "USER_TOKEN:";

    /**
     * 权限前缀 -> 前缀:员工ID:登录方式
     */
    public static final String USER_PERMISSION_PREFIX = "USER_PERMISSION:";

    /**
     * 部门名称CODE前缀 -> 前缀:部门CODE
     */
    public static final String DEP_NAME_CODE_PREFIX = "DEP_NAME_CODE:";

    /**
     * 用户名称前缀 -> 前缀:员工ID
     */
    public static final String USER_NAME_PREFIX = "USER_NAME:";

    /**
     * 用户头像前缀 -> 前缀:员工ID
     */
    public static final String USER_AVATAR_PREFIX = "USER_AVATAR:";

}
