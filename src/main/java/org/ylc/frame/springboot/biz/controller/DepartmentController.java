package org.ylc.frame.springboot.biz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ylc.frame.springboot.biz.dto.DepartmentDTO;
import org.ylc.frame.springboot.biz.service.DepartmentService;
import org.ylc.frame.springboot.biz.vo.DepartmentVO;
import org.ylc.frame.springboot.common.annotation.Permission;
import org.ylc.frame.springboot.common.entity.HttpResult;
import org.ylc.frame.springboot.common.tree.DepartmentTree;

import javax.validation.Valid;

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

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    @Permission("department:add")
    public HttpResult addInfo(@RequestBody @Valid DepartmentDTO dto) {
        departmentService.addInfo(dto);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除")
    @GetMapping("/delete/{id}}")
    @Permission("department:delete")
    public HttpResult delInfo(@ApiParam(name = "id", value = "id")
                              @PathVariable("id") Long id) {
        departmentService.delInfo(id);
        return HttpResult.success();
    }

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    @Permission("department:update")
    public HttpResult updateInfo(@RequestBody @Valid DepartmentDTO dto) {
        departmentService.updateInfo(dto);
        return HttpResult.success();
    }

    @ApiOperation(value = "根据ID查询信息")
    @GetMapping("/get/{id}}")
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
