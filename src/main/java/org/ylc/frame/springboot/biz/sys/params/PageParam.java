package org.ylc.frame.springboot.biz.sys.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 分页参数
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/10/12
 */
@Getter
@Setter
public class PageParam {

    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "页码", required = true)
    private Long page = 1L;

    @NotNull(message = "单页数量不能为空")
    @ApiModelProperty(value = "单页容量", required = true)
    private Long size = 10L;

}
