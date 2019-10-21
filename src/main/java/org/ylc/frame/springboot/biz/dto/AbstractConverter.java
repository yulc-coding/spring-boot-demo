package org.ylc.frame.springboot.biz.dto;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 实体转换接口
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/10/21
 */
public abstract class AbstractConverter<T> {

    /**
     * bean之间相互转化
     */
    protected abstract T convertToEntity();

}
