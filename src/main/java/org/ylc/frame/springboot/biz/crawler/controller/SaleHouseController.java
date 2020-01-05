package org.ylc.frame.springboot.biz.crawler.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ylc.frame.springboot.biz.common.entity.HttpResult;
import org.ylc.frame.springboot.biz.crawler.dao.SaleHouseDao;
import org.ylc.frame.springboot.biz.crawler.entity.SaleHouse;
import org.ylc.frame.springboot.biz.crawler.param.HousePageArg;
import org.ylc.frame.springboot.biz.crawler.param.PriceTrendArg;
import org.ylc.frame.springboot.biz.crawler.service.SaleHouseService;
import org.ylc.frame.springboot.biz.crawler.vo.SaleHouseVO;
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
@RequestMapping("/crawler/sale-house")
public class SaleHouseController {

    private final SaleHouseService saleHouseService;

    private final SaleHouseDao saleHouseDao;

    @Autowired
    public SaleHouseController(SaleHouseDao saleHouseDao, SaleHouseService saleHouseService) {
        this.saleHouseDao = saleHouseDao;
        this.saleHouseService = saleHouseService;
    }

    @ApiOperation(value = "查询指定日期的列表")
    @PostMapping("/page")
    public HttpResult<Pagination<SaleHouseVO>> page(@RequestBody @Valid HousePageArg args) {
        return HttpResult.success(saleHouseService.page(args));
    }

    @ApiOperation(value = "查询单条数据的明细")
    @GetMapping("/findById/{id}")
    public HttpResult<SaleHouse> findById(@PathVariable(value = "id") String id) {
        return HttpResult.success(saleHouseDao.findById(id));
    }

    @ApiOperation(value = "某个楼盘的房价趋势")
    @PostMapping("/trend")
    public HttpResult<SaleTrendVO> priceTrend(@RequestBody @Valid PriceTrendArg args) {
        return HttpResult.success(saleHouseService.priceTrend(args));
    }
}
