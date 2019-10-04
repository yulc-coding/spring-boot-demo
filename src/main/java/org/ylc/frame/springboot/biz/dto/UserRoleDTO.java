package org.ylc.frame.springboot.biz.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户、角色关联表
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-10-04
 */
@Data
public class UserRoleDTO {

    private Integer id;

    @ApiModelProperty(value = "员工ID")
    private Integer userId;

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

}
