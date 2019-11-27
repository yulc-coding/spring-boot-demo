package org.ylc.frame.springboot.biz.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.ylc.frame.springboot.biz.entity.User;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-10-05
 */
@Getter
@Setter
public class UserDTO extends AbstractConverter<User> {

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

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @Override
    public User convertToEntity() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
