package org.ylc.frame.springboot.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色、菜单关联表
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-10-04
 */
@Getter
@Setter
public class RoleMenuVO {

    private Long id;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

}
