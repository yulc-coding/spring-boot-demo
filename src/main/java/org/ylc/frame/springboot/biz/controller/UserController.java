package org.ylc.frame.springboot.biz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ylc.frame.springboot.biz.service.UserService;
import org.ylc.frame.springboot.common.annotation.Permission;
import org.ylc.frame.springboot.common.base.HttpResult;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Api(value = "UserController")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "新增")
    @PostMapping
    @Permission("user:add")
    public HttpResult addInfo() {

        return HttpResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}}")
    @Permission("user:del")
    public HttpResult delInfo(@ApiParam(name = "id", value = "id")
                              @PathVariable("id") Long id) {

        return HttpResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping
    @Permission("user:update")
    public HttpResult updateInfo() {

        return HttpResult.success();
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("/page/{size}/{current}")
    @Permission("pc")
    public HttpResult pageInfo(@ApiParam(name = "size", value = "每页显示", defaultValue = "10") @PathVariable Long size,
                               @ApiParam(name = "current", value = "当前页", defaultValue = "1") @PathVariable Long current) {
        return HttpResult.success(userService.page(new Page<>(current, size)));
    }

    @ApiOperation(value = "根据ID查询信息")
    @GetMapping("/{id}}")
    @Permission("pc")
    public HttpResult getInfoById(@ApiParam(name = "id", value = "id")
                                  @PathVariable(name = "id") Long id) {

        return HttpResult.success(userService.getById(id));
    }

    public static void main(String[] args) {
        System.out.println(1);
    }

}
