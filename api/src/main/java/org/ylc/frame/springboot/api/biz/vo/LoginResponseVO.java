package org.ylc.frame.springboot.api.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.ylc.frame.springboot.common.tree.MenuTree;

import java.util.List;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 登入返回信息
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/30
 */
@Data
public class LoginResponseVO {

    @ApiModelProperty(value = "生成的token")
    private String token;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "菜单列表")
    List<MenuTree> menus;
}
