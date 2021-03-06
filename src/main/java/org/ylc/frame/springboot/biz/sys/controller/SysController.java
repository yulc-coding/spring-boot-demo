package org.ylc.frame.springboot.biz.sys.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.ylc.frame.springboot.biz.sys.dto.ChangePwdDTO;
import org.ylc.frame.springboot.biz.sys.params.LoginParam;
import org.ylc.frame.springboot.biz.sys.service.UserService;
import org.ylc.frame.springboot.biz.sys.vo.LoginResponseVO;
import org.ylc.frame.springboot.setting.annotation.Permission;
import org.ylc.frame.springboot.biz.common.entity.HttpResult;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

    private final UserService userService;

    public SysController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public HttpResult<LoginResponseVO> login(@RequestBody @Valid LoginParam args) {
        return HttpResult.success(userService.login(args));
    }

    @ApiOperation("登出")
    @GetMapping("/logout")
    public HttpResult<Object> logout(HttpServletRequest request) {
        userService.logout(request.getHeader("token"));
        return HttpResult.success();
    }

    @ApiOperation(value = "修改密码")
    @PostMapping("/changePwd")
    @Permission("pc")
    public HttpResult<Object> changePwd(@RequestBody @Validated ChangePwdDTO pwd) {
        userService.changePwd(pwd.getOldPwd(), pwd.getNewPwd(), pwd.getRepeatPwd());
        return HttpResult.success();
    }

}
