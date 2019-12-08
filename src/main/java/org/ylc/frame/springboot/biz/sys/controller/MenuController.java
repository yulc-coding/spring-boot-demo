package org.ylc.frame.springboot.biz.sys.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ylc.frame.springboot.biz.sys.dto.MenuDTO;
import org.ylc.frame.springboot.biz.sys.service.MenuService;
import org.ylc.frame.springboot.biz.sys.vo.MenuVO;
import org.ylc.frame.springboot.setting.annotation.Permission;
import org.ylc.frame.springboot.biz.common.entity.HttpResult;
import org.ylc.frame.springboot.biz.common.tree.BaseTree;
import org.ylc.frame.springboot.biz.common.tree.MenuTree;

import javax.validation.Valid;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Api(value = "MenuController")
@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    @Permission("menu:add")
    public HttpResult<Object> addInfo(@RequestBody @Valid MenuDTO dto) {
        menuService.addInfo(dto);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除")
    @GetMapping("/delete/{id}")
    @Permission("menu:delete")
    public HttpResult<Object> delInfo(@ApiParam(name = "id", value = "id")
                                      @PathVariable("id") Long id) {
        menuService.delInfo(id);
        return HttpResult.success();
    }

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    @Permission("menu:update")
    public HttpResult<Object> updateInfo(@RequestBody @Valid MenuDTO dto) {
        menuService.updateInfo(dto);
        return HttpResult.success();
    }

    @ApiOperation(value = "获取树形结构")
    @GetMapping("/tree")
    @Permission("pc")
    public HttpResult<MenuTree> tree() {
        return HttpResult.success(menuService.getMenuTree());
    }

    @ApiOperation(value = "获取基本的树形结构")
    @GetMapping("/simpleTree")
    @Permission("pc")
    public HttpResult<BaseTree> simpleTree() {
        return HttpResult.success(menuService.getBaseTree());
    }

    @ApiOperation(value = "根据ID查询信息")
    @GetMapping("/get/{id}")
    @Permission("pc")
    public HttpResult<MenuVO> getInfoById(@ApiParam(name = "id", value = "id")
                                          @PathVariable(name = "id") Long id) {
        return HttpResult.success(menuService.getInfoById(id));
    }

}
