package org.ylc.frame.springboot.biz.common.tree;

import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class BaseTree implements Serializable {

    private static final long serialVersionUID = -86906757902585741L;

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
     * 名称
     */
    private String pName;

    /**
     * 子类
     */
    private List<BaseTree> children;

}
