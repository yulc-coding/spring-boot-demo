package org.ylc.frame.springboot.biz.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.ylc.frame.springboot.biz.entity.Menu;

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
@Getter
@Setter
public class MenuDTO extends AbstractConverter<Menu> {

    private Long id;

    @NotNull(message = "上级菜单不能为空")
    @ApiModelProperty(value = "上级菜单")
    private Long pid;

    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "需要的权限")
    private String permission;

    @NotBlank(message = "菜单类别不能为空")
    @ApiModelProperty(value = "菜单类别：1文件、2页面、3按钮、4权限")
    private String type;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "图标")
    private String icon;

    @NotNull(message = "排序不能为空")
    @ApiModelProperty(value = "排序")
    private Integer seq;

    @NotBlank(message = "访问方式不能为空")
    @ApiModelProperty(value = "访问方式，1PC，2APP，3通用")
    private String loginFrom;

    @ApiModelProperty(value = "备注")
    private String remark;

    @Override
    public Menu convertToEntity() {
        Menu menu = new Menu();
        BeanUtils.copyProperties(this, menu);
        return menu;
    }
}
