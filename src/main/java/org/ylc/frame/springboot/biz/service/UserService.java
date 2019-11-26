package org.ylc.frame.springboot.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.ylc.frame.springboot.biz.dto.UserDTO;
import org.ylc.frame.springboot.biz.entity.User;
import org.ylc.frame.springboot.biz.params.LoginArg;
import org.ylc.frame.springboot.biz.params.UserPageParams;
import org.ylc.frame.springboot.biz.vo.LoginResponseVO;
import org.ylc.frame.springboot.biz.vo.UserVO;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
public interface UserService extends IService<User> {

    /**
     * 新增
     */
    void addInfo(UserDTO dto);

    /**
     * 根据ID删除
     */
    void delInfo(long id);

    /**
     * 更新信息
     */
    void updateInfo(UserDTO dto);

    /**
     * 分页查询
     *
     * @param page 参数
     * @return IPage
     */
    IPage<UserVO> pageInfo(UserPageParams page);

    /**
     * 根据ID查询
     */
    UserVO getInfoById(long id);

    /**
     * 绑定角色
     *
     * @param userId 用户ID
     * @param roles  角色列表
     */
    void bindRole(long userId, List<Long> roles);

    /**
     * 修改密码
     *
     * @param oldPwd    就密码
     * @param newPwd    新密码
     * @param repeatPwd 重复密码
     */
    void changePwd(String oldPwd, String newPwd, String repeatPwd);

    /**
     * 登录
     *
     * @param args 参数
     * @return 用户信息
     */
    LoginResponseVO login(LoginArg args);

    /**
     * 登出
     *
     * @param token token信息
     */
    void logout(String token);

    /**
     * 重置密码
     *
     * @param userId 用户ID
     */
    void resetPwd(long userId);

}
