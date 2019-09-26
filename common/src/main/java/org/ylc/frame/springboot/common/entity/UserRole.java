package org.ylc.frame.springboot.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户、角色关联表
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Data
@TableName("sys_user_role")
@ApiModel(value = "UserRole对象", description = "用户、角色关联表")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "员工ID")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "角色ID")
    @TableField("role_id")
    private Integer roleId;


}