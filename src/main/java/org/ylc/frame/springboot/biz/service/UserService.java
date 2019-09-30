package org.ylc.frame.springboot.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ylc.frame.springboot.biz.entity.User;
import org.ylc.frame.springboot.biz.params.LoginArg;
import org.ylc.frame.springboot.biz.vo.LoginResponseVO;

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

}
