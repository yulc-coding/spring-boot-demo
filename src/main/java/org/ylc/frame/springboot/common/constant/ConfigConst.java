package org.ylc.frame.springboot.common.constant;

/**
 * 系统配置常量
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/7/6 20:25
 */
public class ConfigConst {

    /**
     * 默认密码
     */
    public static String DEFAULT_PWD = "123456";

    /**
     * PC登入
     */
    public static String LOGIN_PC = "PC";
    /**
     * app登入
     */
    public static String LOGIN_APP = "APP";


    /**
     * PC token 过期时间 30分
     */
    public static Long DEFAULT_PC_TOKEN_INVALID_TIME = 1000 * 60 * 30L;
    /**
     * APP token 过期时间 7天
     */
    public static Long DEFAULT_APP_TOKEN_INVALID_TIME = 1000 * 60 * 60 * 24 * 7L;

    /**
     * 逻辑已删除
     */
    public static final Integer LOGIC_DEL = 0;
    /**
     * 逻辑未删除
     */
    public static final Integer LOGIC_NOT_DEL = 1;


    /**
     * 请求返回
     */
    public static class Return {
        /**
         * 成功
         */
        public static final int SUCCESS = 200;
        /**
         * 操作失败，统一返回代码编号，直接打印出msg信息
         */
        public static final int OPERATION_FAILED = 500;
        /**
         * 没有访问权限,提示非法操作
         */
        public static final int ACCESS_RESTRICTED = 403;
        /**
         * token过期，引导到登录界面
         */
        public static final int TOKEN_EXPIRED = 401;
    }

}
