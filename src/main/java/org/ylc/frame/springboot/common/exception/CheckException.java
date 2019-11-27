package org.ylc.frame.springboot.common.exception;

import org.ylc.frame.springboot.common.constant.ConfigConstants;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 参数校验类异常
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/24
 */
public class CheckException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误code
     */
    private int errCode = ConfigConstants.Return.OPERATION_FAILED;

    public CheckException(String message) {
        super(message);
    }

    public CheckException(String message, int code) {
        super(message);
        this.errCode = code;
    }

    public CheckException(Throwable cause) {
        super(cause);
    }

    public CheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getErrCode() {
        return errCode;
    }
}
