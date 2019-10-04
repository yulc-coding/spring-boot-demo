package org.ylc.frame.springboot.biz.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 角色、菜单关联表
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-10-04
 */
@Data
public class RoleMenuDTO{

    private Integer id;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "菜单ID")
    private Integer menuId;

}
