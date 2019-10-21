package org.ylc.frame.springboot.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户、角色关联表
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-10-04
 */
@Getter
@Setter
public class UserRoleVO {

    private Long id;

    @ApiModelProperty(value = "员工ID")
    private Long userId;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

}
