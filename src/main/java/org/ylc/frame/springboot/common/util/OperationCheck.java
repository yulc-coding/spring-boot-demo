package org.ylc.frame.springboot.common.util;

import org.ylc.frame.springboot.common.exception.OperationException;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 业务操作校验
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/24
 */
public class OperationCheck {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new OperationException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new OperationException(message);
        }
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new OperationException(message);
        }
    }
}
