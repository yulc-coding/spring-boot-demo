package org.ylc.frame.springboot.biz.crawler.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 列表显示
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-01-05
 */
@Getter
@Setter
public class SaleHouseVO {

    private String id;

    @ApiModelProperty(value = "城市地区")
    private String city;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "户型")
    private String houseType;

    @ApiModelProperty(value = "总价")
    private String allPrice;

    @ApiModelProperty(value = "单价")
    private String unitPrice;
}
