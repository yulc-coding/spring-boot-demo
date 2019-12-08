package org.ylc.frame.springboot.biz.common.tree;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 部门树
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/10/3 16:47
 */
@Getter
@Setter
public class DepartmentTree extends BaseTree {

    private static final long serialVersionUID = -5364169028774066356L;

    @ApiModelProperty(value = "部门编码")
    private String code;

    @ApiModelProperty(value = "备注")
    private String remark;

}
