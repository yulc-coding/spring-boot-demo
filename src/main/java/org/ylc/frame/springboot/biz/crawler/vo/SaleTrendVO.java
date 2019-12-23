package org.ylc.frame.springboot.biz.crawler.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 价格趋势
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019-12-23
 */
@Getter
@Setter
public class SaleTrendVO {

    private String name;

    private Integer[] days;

    private BigDecimal[] unitPrice;

    private BigDecimal[] allPrice;

}
