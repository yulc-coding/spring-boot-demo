package org.ylc.frame.springboot.biz.common.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 下拉通用类
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/12/6 22:18
 */
@Getter
@Setter
public class SelectEntity<T> {

    private T key;

    private String value;

}
