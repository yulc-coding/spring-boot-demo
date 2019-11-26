package org.ylc.frame.springboot.common.util;

import org.springframework.util.ObjectUtils;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 参数工具类
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/11/26
 */
public class ParamUtils {

    public static boolean isEmpty(Object obj) {
        return ObjectUtils.isEmpty(obj);
    }

    public static boolean notEmpty(Object obj) {
        return !ObjectUtils.isEmpty(obj);
    }


}
