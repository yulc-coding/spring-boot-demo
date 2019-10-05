package org.ylc.frame.springboot.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ylc.frame.springboot.biz.dto.UserDTO;
import org.ylc.frame.springboot.biz.entity.User;
import org.ylc.frame.springboot.biz.params.LoginArg;
import org.ylc.frame.springboot.biz.vo.LoginResponseVO;
import org.ylc.frame.springboot.biz.vo.UserVO;

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
     * 根据ID查询
     */
    UserVO getInfoById(long id);

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
     */
    void resetPwd();

}
