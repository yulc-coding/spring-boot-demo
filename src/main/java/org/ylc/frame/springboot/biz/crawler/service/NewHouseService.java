package org.ylc.frame.springboot.biz.crawler.service;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.ylc.frame.springboot.biz.crawler.dao.NewHouseDao;
import org.ylc.frame.springboot.biz.crawler.entity.NewHouse;
import org.ylc.frame.springboot.biz.crawler.param.HousePageArg;
import org.ylc.frame.springboot.biz.crawler.param.PriceTrendArg;
import org.ylc.frame.springboot.biz.crawler.vo.NewHouseVO;
import org.ylc.frame.springboot.biz.crawler.vo.NewTrendVO;
import org.ylc.frame.springboot.component.mongodb.base.Pagination;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class NewHouseService {

    private final NewHouseDao newHouseDao;

    @Autowired
    public NewHouseService(NewHouseDao newHouseDao) {
        this.newHouseDao = newHouseDao;
    }

    /**
     * 分页查询
     */
    public Pagination<NewHouseVO> page(HousePageArg args) {
        Pagination<NewHouse> entityPage = newHouseDao.getPage(args.generatePageQuery(), args.getCurPage(), args.getPageSize());
        Pagination<NewHouseVO> voPage = new Pagination<>(args.getCurPage(), args.getPageSize(), 0);
        List<NewHouse> entityList = entityPage.getRecords();
        if (CollectionUtil.isNotEmpty(entityList)) {
            List<NewHouseVO> voList = new ArrayList<>();
            NewHouseVO vo;
            for (NewHouse entity : entityList) {
                vo = new NewHouseVO();
                BeanUtils.copyProperties(entity, vo);
                voList.add(vo);
            }
            BeanUtils.copyProperties(entityPage, voPage);
            voPage.setRecords(voList);
        }
        return voPage;
    }

    /**
     * 房价趋势
     */
    public NewTrendVO priceTrend(PriceTrendArg args) {
        List<NewHouse> newList = newHouseDao.find(args.generatePageQuery());
        NewTrendVO vo = new NewTrendVO();
        if (CollectionUtils.isEmpty(newList)) {
            return vo;
        }
        int size = newList.size();
        Integer[] days = new Integer[size];
        BigDecimal[] price = new BigDecimal[size];
        for (int i = 0; i < size; i++) {
            days[i] = newList.get(i).getReportDate();
            price[i] = newList.get(i).getPriceNu();
        }
        vo.setName(args.getName());
        vo.setDays(days);
        vo.setPrice(price);
        return vo;
    }

}
