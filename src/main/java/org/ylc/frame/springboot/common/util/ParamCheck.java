package org.ylc.frame.springboot.common.util;


import org.ylc.frame.springboot.common.exception.CheckException;

import java.util.Collection;
import java.util.Map;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 参数校验
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/24
 */
public class ParamCheck {

    public static void assertTrue(boolean expression, String message) {
        if (!expression) {
            throw new CheckException(message);
        }
    }

    /**
     * 不为NULL
     */
    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new CheckException(message);
        }
    }

    /**
     * 不存在数据
     *
     * @param nu      数量
     * @param message 错误提示
     */
    public static void notExists(int nu, String message) {
        if (nu > 0) {
            throw new CheckException(message);
        }
    }

    /**
     * 非空字符
     *
     * @param str     str
     * @param message 错误提示
     */
    public static void notEmptyStr(Object str, String message) {
        if (str == null || "".equals(str)) {
            throw new CheckException(message);
        }
    }

    /**
     * 非空集合
     *
     * @param collection col
     * @param message    错误提示
     */
    public static void notEmptyCollection(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new CheckException(message);
        }
    }

    /**
     * 非空map
     *
     * @param map     map
     * @param message 错误提示
     */
    public static void notEmptyMap(Map<?, ?> map, String message) {
        if (map == null || map.isEmpty()) {
            throw new CheckException(message);
        }
    }

    public static void assertEquals(Object obj1, Object obj2, String message) {
        if (!obj1.equals(obj2)) {
            throw new CheckException(message);
        }
    }
}
