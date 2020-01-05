package org.ylc.frame.springboot.biz.crawler.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 二手房信息
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019-12-23
 */
@Getter
@Setter
@Document(collection = "sale_house")
public class SaleHouse {

    @Id
    private String id;

    private String city;

    private String name;

    @Field("house_type")
    private String houseType;

    private String address;

    private String tags;

    @Field("all_price")
    private String allPrice;

    @Field("all_price_nu")
    private BigDecimal allPriceNu;

    @Field("unit-price")
    private String unitPrice;

    @Field("unit-price_nu")
    private BigDecimal unitPriceNu;

    private String pic;

    private String authenticity;

    @Field("report_date")
    private Integer reportDate;

}
