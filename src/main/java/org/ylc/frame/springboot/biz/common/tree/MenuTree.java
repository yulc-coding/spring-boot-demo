package org.ylc.frame.springboot.biz.common.tree;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 菜单的树状结构
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/30
 */
@Getter
@Setter
public class MenuTree extends BaseTree {

    private static final long serialVersionUID = 5742943846696025578L;

    @ApiModelProperty(value = "权限")
    private String permission;

    @ApiModelProperty(value = "权限类别：1文件、2页面、3按钮、4权限")
    private String type;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "排序")
    private Integer seq;

    @ApiModelProperty(value = "访问方式，1PC，2APP，3通用")
    private String loginFrom;

    @ApiModelProperty(value = "备注")
    private String remark;

}
