package org.ylc.frame.springboot.biz.crawler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.ylc.frame.springboot.biz.crawler.dao.SaleHouseDao;
import org.ylc.frame.springboot.biz.crawler.entity.SaleHouseMongo;
import org.ylc.frame.springboot.biz.crawler.param.HousePageArg;
import org.ylc.frame.springboot.biz.crawler.param.PriceTrendArg;
import org.ylc.frame.springboot.biz.crawler.vo.SaleTrendVO;
import org.ylc.frame.springboot.component.mongodb.base.Pagination;

import java.math.BigDecimal;
import java.util.List;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019-12-23
 */
@Service
public class SaleHouseService {

    private final SaleHouseDao saleHouseDao;

    @Autowired
    public SaleHouseService(SaleHouseDao saleHouseDao) {
        this.saleHouseDao = saleHouseDao;
    }

    /**
     * 分页查询
     */
    public Pagination<SaleHouseMongo> page(HousePageArg args) {
        return saleHouseDao.getPage(args.generatePageQuery(), args.getCurPage(), args.getPageSize());
    }

    /**
     * 房价趋势
     */
    public SaleTrendVO priceTrend(PriceTrendArg args) {
        List<SaleHouseMongo> saleList = saleHouseDao.find(args.generatePageQuery());
        SaleTrendVO vo = new SaleTrendVO();
        if (CollectionUtils.isEmpty(saleList)) {
            return vo;
        }
        int size = saleList.size();
        Integer[] days = new Integer[size];
        BigDecimal[] unitPrice = new BigDecimal[size];
        BigDecimal[] allPrice = new BigDecimal[size];
        for (int i = 0; i < size; i++) {
            days[i] = saleList.get(i).getReportDate();
            unitPrice[i] = saleList.get(i).getUnitPriceNu();
            allPrice[i] = saleList.get(i).getAllPriceNu();
        }
        vo.setName(args.getName());
        vo.setDays(days);
        vo.setUnitPrice(unitPrice);
        vo.setAllPrice(allPrice);
        return vo;
    }

}
