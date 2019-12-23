package org.ylc.frame.springboot.biz.crawler.controller;

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
@RequestMapping("/new-house")
public class NewHouseController {

    private final NewHouseService newHouseService;

    private final NewHouseDao newHouseDao;

    @Autowired
    public NewHouseController(NewHouseService newHouseService, NewHouseDao newHouseDao) {
        this.newHouseService = newHouseService;
        this.newHouseDao = newHouseDao;
    }

    /**
     * 分页
     *
     * @param args 分页条件
     * @return page
     */
    @PostMapping("/page")
    public HttpResult<Pagination<NewHouseMongo>> page(@RequestBody @Valid HousePageArg args) {
        return HttpResult.success(newHouseService.page(args));
    }

    @GetMapping("/findById/{id}")
    public HttpResult<NewHouseMongo> findById(@PathVariable(value = "id") String id) {
        return HttpResult.success(newHouseDao.findById(id));
    }

    @PostMapping("/trend")
    public HttpResult<NewTrendVO> priceTrend(@RequestBody @Valid PriceTrendArg args) {
        return HttpResult.success(newHouseService.priceTrend(args));
    }
}
