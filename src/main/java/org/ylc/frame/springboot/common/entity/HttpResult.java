package org.ylc.frame.springboot.common.entity;

import org.ylc.frame.springboot.common.constant.ConfigConst;

import java.io.Serializable;

/**
 * 统一返回
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/3/30 10:46
 */
public class HttpResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private T data;

    public HttpResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public HttpResult() {
        this.code = ConfigConst.Return.SUCCESS;
        this.msg = "SUCCESS";
    }

    public HttpResult(T data) {
        this();
        this.data = data;
    }

    /**
     * 成功返回
     *
     * @param msg  成功信息
     * @param body 返回数据
     * @return Result
     */
    public static <T> HttpResult<T> success(String msg, T body) {
        return new HttpResult<>(ConfigConst.Return.SUCCESS, msg, body);
    }

    /**
     * 成功返回
     *
     * @param body 返回数据
     * @return Result
     */
    public static <T> HttpResult<T> success(T body) {
        return success("success", body);
    }

    /**
     * 成功返回
     *
     * @return Result
     */
    public static <T> HttpResult<T> success() {
        return success(null);
    }

    /**
     * 失败返回
     *
     * @param code 错误编码
     * @param msg  错误信息
     * @return Result
     */
    public static <T> HttpResult<T> fail(int code, String msg) {
        return new HttpResult<>(code, msg, null);
    }

    /**
     * 普通失败返回
     *
     * @param msg 错误信息
     * @return Result
     */
    public static <T> HttpResult<T> fail(String msg) {
        return fail(ConfigConst.Return.OPERATION_FAILED, msg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

}
