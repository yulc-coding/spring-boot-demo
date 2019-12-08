package org.ylc.frame.springboot.biz.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Data
@TableName("sys_menu")
@ApiModel(value = "Menu对象", description = "菜单表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 上级ID
     */
    @TableField("pid")
    private Long pid;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 需要的权限
     */
    @TableField("permission")
    private String permission;

    /**
     * 菜单类别：1文件、2页面、3按钮、4权限
     */
    @TableField("type")
    private String type;

    /**
     * 路径
     */
    @TableField("path")
    private String path;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 排序
     */
    @TableField("seq")
    private Integer seq;

    /**
     * 访问方式，1PC，2APP，3通用
     */
    @TableField("login_from")
    private String loginFrom;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private Long updateUser;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
