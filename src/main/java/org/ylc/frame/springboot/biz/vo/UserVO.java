package org.ylc.frame.springboot.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-10-05
 */
@Data
public class UserVO {

    @ApiModelProperty(value = "员工编号")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "性别，0未知，1男，2女")
    private String gender;

    @ApiModelProperty(value = "备注")
    private String remark;

}
