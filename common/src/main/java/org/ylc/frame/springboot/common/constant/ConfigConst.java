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
     * token 过期时间
     */
    public static Long DEFAULT_TOKEN_INVALID_TIME = 1000 * 60 * 30L;


    /**
     * 请求返回
     */
    public static class RETURN_RESULT {
        /**
         * 成功
         */
        public static final int RESULT_SUCCESS = 200;
        /**
         * 操作失败，统一返回代码编号，直接打印出msg信息
         */
        public static final int RESULT_OPERATION_FAILED = 500;
        /**
         * token验证失败,提示非法操作
         */
        public static final int RESULT_TOKEN_INVALID = 403;
        /**
         * token过期，引导到登录界面
         */
        public static final int RESULT_TOKEN_EXPIRED = 401;
    }

}
