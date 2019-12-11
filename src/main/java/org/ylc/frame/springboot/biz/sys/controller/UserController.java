package org.ylc.frame.springboot.biz.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.ylc.frame.springboot.biz.sys.dto.UserBindRoleDTO;
import org.ylc.frame.springboot.biz.sys.dto.UserDTO;
import org.ylc.frame.springboot.biz.common.params.IdParam;
import org.ylc.frame.springboot.biz.sys.params.UserPageParams;
import org.ylc.frame.springboot.biz.sys.service.UserService;
import org.ylc.frame.springboot.biz.sys.vo.UserVO;
import org.ylc.frame.springboot.setting.annotation.Permission;
import org.ylc.frame.springboot.biz.common.entity.HttpResult;
import org.ylc.frame.springboot.biz.common.entity.SelectEntity;
import org.ylc.frame.springboot.setting.validated.InsertGroup;
import org.ylc.frame.springboot.setting.validated.UpdateGroup;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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


    @ApiOperation(value = "上传头像，返回头像地址")
    @PostMapping("/uploadAvatar")
    @Permission("user:uploadAvatar")
    public HttpResult<String> submitAvatar(HttpServletRequest request,
                                           @ApiParam(name = "avatar", value = "头像")
                                           @RequestParam(name = "avatar") MultipartFile avatar) {
        return HttpResult.success(userService.uploadAvatar(request, avatar));
    }

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    @Permission("user:add")
    public HttpResult<Object> addInfo(@RequestBody @Validated({InsertGroup.class}) UserDTO dto) {
        userService.addInfo(dto);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除")
    @GetMapping("/delete/{id}")
    @Permission("user:delete")
    public HttpResult<Object> delInfo(@ApiParam(name = "id", value = "id")
                                      @PathVariable("id") Long id) {
        userService.delInfo(id);
        return HttpResult.success();
    }

    @ApiOperation(value = "批量删除")
    @PostMapping("/delMulti")
    @Permission("user:delMulti")
    public HttpResult<Object> delMulti(@RequestBody @Validated IdParam<Long> idParam) {
        userService.delMulti(idParam.getIdList());
        return HttpResult.success();
    }

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    @Permission("user:update")
    public HttpResult<Object> updateInfo(@RequestBody @Validated({UpdateGroup.class}) UserDTO dto) {
        userService.updateInfo(dto);
        return HttpResult.success();
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/page")
    @Permission("pc")
    public HttpResult<IPage<UserVO>> pageInfo(@RequestBody @Validated UserPageParams page) {
        return HttpResult.success(userService.pageInfo(page));
    }

    @ApiOperation(value = "根据ID查询信息，用于编辑")
    @GetMapping("/get/{id}")
    @Permission("pc")
    public HttpResult<UserDTO> getInfoById(@ApiParam(name = "id", value = "id")
                                           @PathVariable(name = "id") Long id) {
        return HttpResult.success(userService.getInfoById(id));
    }

    @ApiOperation(value = "获取用户角色")
    @GetMapping("/userRoles/{userId}")
    @Permission("pc")
    public HttpResult<List<SelectEntity<Long>>> getUserRoles(@ApiParam(name = "userId", value = "userId")
                                                             @PathVariable(name = "userId") Long userId) {
        return HttpResult.success(userService.getUserRoles(userId));
    }

    @ApiOperation(value = "绑定角色")
    @PostMapping("/bindRole")
    @Permission("user:bindRole")
    public HttpResult<Object> bindRole(@RequestBody @Valid UserBindRoleDTO userBindRoleDTO) {
        userService.bindRole(userBindRoleDTO.getUserIds(), userBindRoleDTO.getRoles());
        return HttpResult.success();
    }

    @ApiOperation(value = "重置密码")
    @GetMapping("/resetPwd/{userId}")
    @Permission("user:resetPwd")
    public HttpResult<Object> resetPwd(@ApiParam(name = "userId", value = "userId")
                                       @PathVariable(name = "userId") Long userId) {
        userService.resetPwd(userId);
        return HttpResult.success();
    }

}
