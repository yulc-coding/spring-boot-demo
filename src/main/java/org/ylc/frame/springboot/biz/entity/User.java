package org.ylc.frame.springboot.biz.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-10-01
 */
@Data
@TableName("sys_user")
@ApiModel(value = "User对象", description = "用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 员工编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 账号
     */
    @TableField("username")
    private String username;

    /**
     * 所属部门ID
     */
    @TableField("dep_id")
    private Long depId;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 加密盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 电话号码
     */
    @TableField("phone")
    private String phone;

    /**
     * 性别，0未知，1男，2女
     */
    @TableField("gender")
    private String gender;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 是否启用，0未启用，1启用，99冻结
     */
    @TableField("state")
    private Integer state;

    /**
     * 用户头像
     */
    @TableField("avatar")
    private String avatar;

    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private Long updateUser;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
