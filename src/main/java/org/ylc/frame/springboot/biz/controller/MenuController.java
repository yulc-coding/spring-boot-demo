package org.ylc.frame.springboot.biz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ylc.frame.springboot.biz.dto.MenuDTO;
import org.ylc.frame.springboot.biz.service.MenuService;
import org.ylc.frame.springboot.biz.vo.MenuVO;
import org.ylc.frame.springboot.common.annotation.Permission;
import org.ylc.frame.springboot.common.base.HttpResult;
import org.ylc.frame.springboot.common.tree.MenuTree;

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
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation(value = "新增")
    @PostMapping
    @Permission("menu:add")
    public HttpResult addInfo(@RequestBody @Valid MenuDTO dto) {
        menuService.addInfo(dto);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}}")
    @Permission("menu:del")
    public HttpResult delInfo(@ApiParam(name = "id", value = "id")
                              @PathVariable("id") Long id) {
        menuService.delInfo(id);
        return HttpResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping
    @Permission("menu:update")
    public HttpResult updateInfo(@RequestBody @Valid MenuDTO dto) {
        menuService.updateInfo(dto);
        return HttpResult.success();
    }

    @ApiOperation(value = "获取树形结构")
    @GetMapping("/tree")
    @Permission("pc")
    public HttpResult<MenuTree> tree() {
        return HttpResult.success(menuService.getMenuTree());
    }

    @ApiOperation(value = "根据ID查询信息")
    @GetMapping("/{id}}")
    @Permission("pc")
    public HttpResult<MenuVO> getInfoById(@ApiParam(name = "id", value = "id")
                                          @PathVariable(name = "id") Long id) {
        return HttpResult.success(menuService.getInfoById(id));
    }

}
