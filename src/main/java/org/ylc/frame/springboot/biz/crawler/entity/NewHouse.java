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
 * 楼盘
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019-12-23
 */
@Getter
@Setter
@Document(collection = "new_house")
public class NewHouse {

    @Id
    private String id;

    private String city;

    private String name;

    @Field("house_type")
    private String houseType;

    private String address;

    @Field("address_link")
    private String addressLink;

    private String tags;

    private String price;

    @Field("price_nu")
    private BigDecimal priceNu;

    private String pic;

    @Field("pic_link")
    private String picLink;

    @Field("report_date")
    private Integer reportDate;

}
