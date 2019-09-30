package org.ylc.frame.springboot.biz.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 登录参数
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/30
 */
@Data
public class LoginArg {

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value = "账号")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotBlank(message = "登入方式，PC,APP")
    @ApiModelProperty(value = "登录来源")
    private String loginFrom;
}
