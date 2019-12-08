package org.ylc.frame.springboot.biz.sys.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.ylc.frame.springboot.biz.sys.dto.DepartmentDTO;
import org.ylc.frame.springboot.biz.sys.service.DepartmentService;
import org.ylc.frame.springboot.biz.sys.vo.DepartmentVO;
import org.ylc.frame.springboot.setting.annotation.Permission;
import org.ylc.frame.springboot.biz.common.entity.HttpResult;
import org.ylc.frame.springboot.biz.common.tree.DepartmentTree;
import org.ylc.frame.springboot.setting.validated.InsertGroup;
import org.ylc.frame.springboot.setting.validated.UpdateGroup;

/**
 * <p>
 * 部门 前端控制器
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Api(value = "DepartmentController")
@RestController
@RequestMapping("/sys/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @ApiOperation(value = "新增，返回新增信息")
    @PostMapping("/add")
    @Permission("department:add")
    public HttpResult<DepartmentVO> addInfo(@RequestBody @Validated({InsertGroup.class}) DepartmentDTO dto) {
        return HttpResult.success(departmentService.addInfo(dto));
    }

    @ApiOperation(value = "删除")
    @GetMapping("/delete/{id}")
    @Permission("department:delete")
    public HttpResult<Object> delInfo(@ApiParam(name = "id", value = "id")
                                      @PathVariable("id") Long id) {
        departmentService.delInfo(id);
        return HttpResult.success();
    }

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    @Permission("department:update")
    public HttpResult<Object> updateInfo(@RequestBody @Validated({UpdateGroup.class}) DepartmentDTO dto) {
        departmentService.updateInfo(dto);
        return HttpResult.success();
    }

    @ApiOperation(value = "根据ID查询信息")
    @GetMapping("/get/{id}")
    @Permission("pc")
    public HttpResult<DepartmentVO> getInfoById(@ApiParam(name = "id", value = "id")
                                                @PathVariable(name = "id") Long id) {
        return HttpResult.success(departmentService.getInfoById(id));
    }

    @ApiOperation(value = "获取部门树")
    @GetMapping("/tree")
    @Permission("pc")
    public HttpResult<DepartmentTree> tree() {
        return HttpResult.success(departmentService.getDepTree());
    }

}
