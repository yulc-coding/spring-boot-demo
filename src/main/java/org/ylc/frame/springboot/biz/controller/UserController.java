package org.ylc.frame.springboot.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ylc.frame.springboot.biz.dto.ChangePwdDTO;
import org.ylc.frame.springboot.biz.dto.UserBindRoleDTO;
import org.ylc.frame.springboot.biz.dto.UserDTO;
import org.ylc.frame.springboot.biz.params.UserPageParams;
import org.ylc.frame.springboot.biz.service.UserService;
import org.ylc.frame.springboot.biz.vo.UserVO;
import org.ylc.frame.springboot.common.annotation.Permission;
import org.ylc.frame.springboot.common.entity.HttpResult;

import javax.validation.Valid;

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
@RequestMapping("/sys/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    @Permission("user:add")
    public HttpResult addInfo(@RequestBody @Valid UserDTO dto) {
        userService.addInfo(dto);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除")
    @GetMapping("/delete/{id}")
    @Permission("user:delete")
    public HttpResult delInfo(@ApiParam(name = "id", value = "id")
                              @PathVariable("id") Long id) {
        userService.delInfo(id);
        return HttpResult.success();
    }

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    @Permission("user:update")
    public HttpResult updateInfo(@RequestBody @Valid UserDTO dto) {
        userService.updateInfo(dto);
        return HttpResult.success();
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/page")
    @Permission("pc")
    public HttpResult<IPage<UserVO>> pageInfo(@RequestBody @Valid UserPageParams page) {
        return HttpResult.success(userService.pageInfo(page));
    }

    @ApiOperation(value = "根据ID查询信息，用于编辑")
    @GetMapping("/get/{id}")
    @Permission("pc")
    public HttpResult<UserDTO> getInfoById(@ApiParam(name = "id", value = "id")
                                           @PathVariable(name = "id") Long id) {
        return HttpResult.success(userService.getInfoById(id));
    }

    @ApiOperation(value = "绑定角色")
    @PostMapping("/bindRole")
    @Permission("user:bindRole")
    public HttpResult bindRole(@RequestBody @Valid UserBindRoleDTO userBindRoleDTO) {
        userService.bindRole(userBindRoleDTO.getUserId(), userBindRoleDTO.getRoles());
        return HttpResult.success();
    }


    @ApiOperation(value = "修改密码")
    @PostMapping("/changePwd")
    @Permission("pc")
    public HttpResult changePwd(@RequestBody @Valid ChangePwdDTO pwd) {
        userService.changePwd(pwd.getOldPwd(), pwd.getNewPwd(), pwd.getRepeatPwd());
        return HttpResult.success();
    }

}
