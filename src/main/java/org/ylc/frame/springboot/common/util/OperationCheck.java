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

    /**
     * 数据库是否执行成功
     *
     * @param executeCount 执行成功条数
     * @param message      异常信息
     */
    public static void isExecute(int executeCount, String message) {
        if (executeCount <= 0) {
            throw new OperationException(message);
        }
    }

    /**
     * 数据库是否执行成功
     *
     * @param executeResult 执行结果
     * @param message       异常信息
     */
    public static void isExecute(boolean executeResult, String message) {
        if (!executeResult) {
            throw new OperationException(message);
        }
    }

}
