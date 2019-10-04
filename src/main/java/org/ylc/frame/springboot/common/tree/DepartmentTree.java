package org.ylc.frame.springboot.common.tree;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentTree extends BaseTree<DepartmentTree> {

    @ApiModelProperty(value = "部门编码")
    private String code;

    @ApiModelProperty(value = "备注")
    private String remark;

}
