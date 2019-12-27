package org.ylc.frame.springboot.biz.crawler.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ylc.frame.springboot.biz.common.entity.HttpResult;
import org.ylc.frame.springboot.biz.crawler.dao.NewHouseDao;
import org.ylc.frame.springboot.biz.crawler.entity.NewHouseMongo;
import org.ylc.frame.springboot.biz.crawler.param.HousePageArg;
import org.ylc.frame.springboot.biz.crawler.param.PriceTrendArg;
import org.ylc.frame.springboot.biz.crawler.service.NewHouseService;
import org.ylc.frame.springboot.biz.crawler.vo.NewTrendVO;
import org.ylc.frame.springboot.component.mongodb.base.Pagination;

import javax.validation.Valid;


/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 楼盘相关
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019-12-23
 */
@RestController
@RequestMapping("/crawler/new-house")
public class NewHouseController {

    private final NewHouseService newHouseService;

    private final NewHouseDao newHouseDao;

    @Autowired
    public NewHouseController(NewHouseService newHouseService, NewHouseDao newHouseDao) {
        this.newHouseService = newHouseService;
        this.newHouseDao = newHouseDao;
    }

    @ApiOperation(value = "查询指定日期的列表，默认当天")
    @PostMapping("/page")
    public HttpResult<Pagination<NewHouseMongo>> page(@RequestBody @Valid HousePageArg args) {
        return HttpResult.success(newHouseService.page(args));
    }

    @ApiOperation(value = "查询单条数据的明细")
    @GetMapping("/findById/{id}")
    public HttpResult<NewHouseMongo> findById(@PathVariable(value = "id") String id) {
        return HttpResult.success(newHouseDao.findById(id));
    }

    @ApiOperation(value = "某个楼盘的房价趋势，默认显示30天的数据")
    @PostMapping("/trend")
    public HttpResult<NewTrendVO> priceTrend(@RequestBody @Valid PriceTrendArg args) {
        return HttpResult.success(newHouseService.priceTrend(args));
    }
}
