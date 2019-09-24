package org.ylc.frame.springboot.common.exception;

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

    public OperationException() {
    }

    public OperationException(String message) {
        super(message);
    }

    public OperationException(Throwable cause) {
        super(cause);
    }

    public OperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
