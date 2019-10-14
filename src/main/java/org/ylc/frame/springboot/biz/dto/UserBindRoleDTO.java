package org.ylc.frame.springboot.biz.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
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
@Data
public class UserBindRoleDTO {

    @NotBlank(message = "绑定用户不能为空")
    @ApiModelProperty(value = "绑定用户ID")
    private Long userId;

    @NotEmpty(message = "绑定角色不能为空")
    @ApiModelProperty(value = "绑定角色列表")
    private List<Long> roles;
}
