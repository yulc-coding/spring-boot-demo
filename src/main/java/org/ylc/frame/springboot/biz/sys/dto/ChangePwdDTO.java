package org.ylc.frame.springboot.biz.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 修改密码
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/10/12
 */
@Getter
@Setter
public class ChangePwdDTO {

    @ApiModelProperty(value = "原密码")
    @NotBlank(message = "原密码不能为空")
    private String oldPwd;

    @ApiModelProperty(value = "新密码")
    @Size(min = 6, message = "新密码不能小于6位")
    @NotBlank(message = "新密码不能为空")
    private String newPwd;

    @ApiModelProperty(value = "重复新密码")
    @NotBlank(message = "重复新密码不能为空")
    private String repeatPwd;
}
