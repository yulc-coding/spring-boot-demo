package org.ylc.frame.springboot.setting.exception;

import org.ylc.frame.springboot.constant.ConfigConstants;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 业务操作异常
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/24
 */
public class OperationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误code
     */
    private int errCode = ConfigConstants.Return.OPERATION_FAILED;

    public OperationException(String message) {
        super(message);
    }

    public OperationException(String message, int code) {
        super(message);
        this.errCode = code;
    }

    public OperationException(Throwable cause) {
        super(cause);
    }

    public OperationException(String message, Throwable cause) {
        super(message, cause);
    }


    public int getErrCode() {
        return errCode;
    }
}
