package org.ylc.frame.springboot.biz.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.ylc.frame.springboot.biz.sys.entity.Menu;

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
public class MenuVO {

    private Long id;

    @ApiModelProperty(value = "上级ID")
    private Long pid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "需要的权限")
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

    /**
     * 实体转换为vo
     */
    public static MenuVO entityConvertToVo(Menu entity) {
        MenuVO vo = new MenuVO();
        if (entity != null) {
            BeanUtils.copyProperties(entity, vo);
        }
        return vo;
    }

}
