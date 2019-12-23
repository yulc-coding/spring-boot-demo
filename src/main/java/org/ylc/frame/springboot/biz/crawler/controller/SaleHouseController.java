package org.ylc.frame.springboot.biz.crawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ylc.frame.springboot.biz.common.entity.HttpResult;
import org.ylc.frame.springboot.biz.crawler.dao.SaleHouseDao;
import org.ylc.frame.springboot.biz.crawler.entity.SaleHouseMongo;
import org.ylc.frame.springboot.biz.crawler.param.HousePageArg;
import org.ylc.frame.springboot.biz.crawler.param.PriceTrendArg;
import org.ylc.frame.springboot.biz.crawler.service.SaleHouseService;
import org.ylc.frame.springboot.biz.crawler.vo.SaleTrendVO;
import org.ylc.frame.springboot.component.mongodb.base.Pagination;

import javax.validation.Valid;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 二手房相关
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019-12-23
 */
@RestController
@RequestMapping("/sale-house")
public class SaleHouseController {

    private final SaleHouseService saleHouseService;

    private final SaleHouseDao saleHouseDao;

    @Autowired
    public SaleHouseController(SaleHouseDao saleHouseDao, SaleHouseService saleHouseService) {
        this.saleHouseDao = saleHouseDao;
        this.saleHouseService = saleHouseService;
    }

    /**
     * 分页
     *
     * @param args 分页条件
     * @return page
     */
    @PostMapping("/page")
    public HttpResult<Pagination<SaleHouseMongo>> page(@RequestBody @Valid HousePageArg args) {
        return HttpResult.success(saleHouseService.page(args));
    }

    @GetMapping("/findById/{id}")
    public HttpResult<SaleHouseMongo> findById(@PathVariable(value = "id") String id) {
        return HttpResult.success(saleHouseDao.findById(id));
    }

    @PostMapping("/trend")
    public HttpResult<SaleTrendVO> priceTrend(@RequestBody @Valid PriceTrendArg args) {
        return HttpResult.success(saleHouseService.priceTrend(args));
    }
}
