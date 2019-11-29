package org.ylc.frame.springboot.biz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ylc.frame.springboot.biz.dto.RoleBindMenuDTO;
import org.ylc.frame.springboot.biz.dto.RoleDTO;
import org.ylc.frame.springboot.biz.service.RoleService;
import org.ylc.frame.springboot.biz.vo.RoleVO;
import org.ylc.frame.springboot.common.annotation.Permission;
import org.ylc.frame.springboot.common.entity.HttpResult;

import javax.validation.Valid;
import java.util.List;

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
@RequestMapping("/sys/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    @Permission("role:add")
    public HttpResult<Object> addInfo(@RequestBody @Valid RoleDTO dto) {
        roleService.addInfo(dto);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除")
    @GetMapping("/delete/{id}")
    @Permission("role:delete")
    public HttpResult<Object> delInfo(@ApiParam(name = "id", value = "id")
                                      @PathVariable("id") Long id) {
        roleService.delInfo(id);
        return HttpResult.success();
    }

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    @Permission("role:update")
    public HttpResult<Object> updateInfo(@RequestBody @Valid RoleDTO dto) {
        roleService.updateInfo(dto);
        return HttpResult.success();
    }

    @ApiOperation(value = "获取角色列表")
    @GetMapping("/list")
    @Permission("pc")
    public HttpResult<List<RoleVO>> list() {
        return HttpResult.success(roleService.getList());
    }

    @ApiOperation(value = "角色绑定菜单")
    @PostMapping("/bindMenu")
    @Permission("role:bindMenu")
    public HttpResult<Object> bindMenu(@RequestBody @Valid RoleBindMenuDTO roleBindMenuDTO) {
        roleService.bindMenu(roleBindMenuDTO.getRoleId(), roleBindMenuDTO.getMenuIds());
        return HttpResult.success();
    }
}
