package org.ylc.frame.springboot.common.tree;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 基础树结构
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/30
 */
@Data
public class BaseTree<T> implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父id
     */
    private Long pid;

    /**
     * 子类
     */
    private List<T> children;


}
