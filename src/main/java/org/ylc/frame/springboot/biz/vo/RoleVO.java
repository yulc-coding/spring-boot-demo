package org.ylc.frame.springboot.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-10-04
 */
@Data
public class RoleVO {

    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

    public RoleVO() {
    }

    public RoleVO(Long id, String name, String remark) {
        this.id = id;
        this.name = name;
        this.remark = remark;
    }
}
