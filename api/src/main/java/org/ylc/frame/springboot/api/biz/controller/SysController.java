package org.ylc.frame.springboot.api.biz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ylc.frame.springboot.common.base.HttpResult;
import org.ylc.frame.springboot.common.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 系统级接口
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/27
 */
@Api(value = "SysController")
@RestController
@RequestMapping("/sys")
public class SysController {

    @Autowired
    private UserService userService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public HttpResult login() {

        return HttpResult.success();
    }


    @ApiOperation("登出")
    @GetMapping("/logout")
    public HttpResult logout(HttpServletRequest request) {
        userService.logout(request.getHeader("token"));
        return HttpResult.success();
    }
}
