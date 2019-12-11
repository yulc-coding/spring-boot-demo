package org.ylc.frame.springboot.biz.sys.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ylc.frame.springboot.biz.common.entity.HttpResult;
import org.ylc.frame.springboot.biz.common.entity.SelectEntity;
import org.ylc.frame.springboot.biz.sys.dto.RoleBindMenuDTO;
import org.ylc.frame.springboot.biz.sys.dto.RoleDTO;
import org.ylc.frame.springboot.biz.sys.service.RoleService;
import org.ylc.frame.springboot.biz.sys.vo.RoleVO;
import org.ylc.frame.springboot.setting.annotation.Permission;

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

    @ApiOperation(value = "获取角色下拉")
    @GetMapping("/roleSelect")
    @Permission("pc")
    public HttpResult<List<SelectEntity<Long>>> roleSelect() {
        return HttpResult.success(roleService.getRoleSelect());
    }

    @ApiOperation(value = "获取角色已有的的菜单列表")
    @GetMapping("/roleMenus/{roleId}")
    @Permission("pc")
    public HttpResult<List<String>> roleMenus(@ApiParam(name = "roleId", value = "角色ID")
                                              @PathVariable("roleId") Long roleId) {
        return HttpResult.success(roleService.roleMenus(roleId));
    }

    @ApiOperation(value = "角色绑定菜单")
    @PostMapping("/bindMenus")
    @Permission("role:bindMenus")
    public HttpResult<Object> bindMenus(@RequestBody @Valid RoleBindMenuDTO roleBindMenuDTO) {
        roleService.bindMenus(roleBindMenuDTO.getRoleId(), roleBindMenuDTO.getMenuIds());
        return HttpResult.success();
    }
}
