package org.ylc.frame.springboot.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 角色、菜单关联表
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Data
@TableName("sys_role_menu")
@ApiModel(value = "RoleMenu对象", description = "角色、菜单关联表")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色ID")
    @TableField("role_id")
    private Integer roleId;

    @ApiModelProperty(value = "菜单ID")
    @TableField("menu_id")
    private Integer menuId;


}