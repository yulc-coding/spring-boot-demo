package org.ylc.frame.springboot.biz.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 用户绑定角色
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/10/13 22:51
 */
@Getter
@Setter
public class UserBindRoleDTO {

    @NotEmpty(message = "绑定用户不能为空")
    @ApiModelProperty(value = "绑定用户ID列表")
    private List<Long> userIds;

    @NotEmpty(message = "绑定角色不能为空")
    @ApiModelProperty(value = "绑定角色列表")
    private List<Long> roles;
}
