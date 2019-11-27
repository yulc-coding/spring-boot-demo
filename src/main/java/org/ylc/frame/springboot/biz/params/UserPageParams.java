package org.ylc.frame.springboot.biz.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 用户分页
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/11/26
 */
@Getter
@Setter
public class UserPageParams extends PageParam {

    @ApiModelProperty(value = "部门")
    private Long depId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "姓名（模糊查询）")
    private String name;

    @ApiModelProperty(value = "状态")
    private Integer state;
}
