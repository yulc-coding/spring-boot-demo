package org.ylc.frame.springboot.api.biz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ylc.frame.springboot.common.annotation.Permission;
import org.ylc.frame.springboot.common.base.HttpResult;
import org.ylc.frame.springboot.common.service.RoleService;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Api(value = "RoleController")
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "新增")
    @PostMapping
    @Permission("role:add")
    public HttpResult addInfo() {

        return HttpResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}}")
    @Permission("role:del")
    public HttpResult delInfo(@ApiParam(name = "id", value = "id")
                              @PathVariable("id") Long id) {

        return HttpResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping
    @Permission("role:update")
    public HttpResult updateInfo() {

        return HttpResult.success();
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("/page/{size}/{current}")
    @Permission("pc")
    public HttpResult pageInfo(@ApiParam(name = "size", value = "每页显示", defaultValue = "10") @PathVariable Long size,
                               @ApiParam(name = "current", value = "当前页", defaultValue = "1") @PathVariable Long current) {

        return HttpResult.success();
    }

    @ApiOperation(value = "根据ID查询信息")
    @GetMapping("/{id}}")
    @Permission("pc")
    public HttpResult getInfoById(@ApiParam(name = "id", value = "id")
                                  @PathVariable(name = "id") Long id) {

        return HttpResult.success();
    }

}