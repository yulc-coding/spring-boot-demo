package org.ylc.frame.springboot.biz.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.ylc.frame.springboot.biz.common.AbstractConverter;
import org.ylc.frame.springboot.biz.sys.entity.User;
import org.ylc.frame.springboot.setting.validated.InsertGroup;
import org.ylc.frame.springboot.setting.validated.UpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    @NotNull(groups = {UpdateGroup.class}, message = "ID不能为空")
    @ApiModelProperty(value = "员工编号")
    private Long id;

    @NotBlank(groups = {InsertGroup.class, UpdateGroup.class}, message = "姓名不能为空")
    @ApiModelProperty(value = "姓名")
    private String name;

    @NotBlank(groups = {InsertGroup.class, UpdateGroup.class}, message = "账号不能为空")
    @ApiModelProperty(value = "账号")
    private String username;

    @NotBlank(groups = {InsertGroup.class, UpdateGroup.class}, message = "部门不能为空")
    @ApiModelProperty(value = "部门CODE")
    private String depCode;

    @ApiModelProperty(value = "部门名称", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String depName;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @NotBlank(groups = {InsertGroup.class, UpdateGroup.class}, message = "性别不能为空")
    @ApiModelProperty(value = "性别，0未知，1男，2女")
    private String gender;

    @ApiModelProperty(value = "性别描述", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String genderDesc;

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
