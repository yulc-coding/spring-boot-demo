package org.ylc.frame.springboot.biz.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-10-04
 */
@Data
public class MenuDTO {

    private Long id;

    @NotNull(message = "上级菜单不能为空")
    @ApiModelProperty(value = "上级菜单")
    private Long pid;

    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "名称")
    private String name;

    @NotBlank(message = "权限不能为空")
    @ApiModelProperty(value = "需要的权限")
    private String permission;

    @NotBlank(message = "菜单类别不能为空")
    @ApiModelProperty(value = "菜单类别：1文件、2页面、3按钮、4权限")
    private String type;

    @ApiModelProperty(value = "匹配规则")
    private String url;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "按钮key")
    private String btnKey;

    @ApiModelProperty(value = "图标")
    private String icon;

    @NotNull(message = "排序不能为空")
    @ApiModelProperty(value = "排序")
    private Integer seq;

    @NotBlank(message = "访问方式不能为空")
    @ApiModelProperty(value = "访问方式，1PC，2APP")
    private String visitType;

    @ApiModelProperty(value = "备注")
    private String remark;

}
