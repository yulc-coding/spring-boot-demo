package org.ylc.frame.springboot.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 部门
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/10/2 20:23
 */
@Data
public class DepartmentVO {

    private Long id;

    @ApiModelProperty(value = "部门编号")
    private String code;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "上级部门ID")
    private Long pid;

    @ApiModelProperty(value = "备注")
    private String remark;
}
