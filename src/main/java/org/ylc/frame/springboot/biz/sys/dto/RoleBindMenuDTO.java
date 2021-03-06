package org.ylc.frame.springboot.biz.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 角色绑定菜单
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/10/14
 */
@Getter
@Setter
public class RoleBindMenuDTO {

    @NotNull(message = "绑定角色不能为空")
    @ApiModelProperty(value = "绑定角色ID")
    private Long roleId;

    @NotEmpty(message = "绑定菜单不能为空")
    @ApiModelProperty(value = "绑定菜单列表")
    private List<Long> menuIds;
}
