package org.ylc.frame.springboot.biz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import org.ylc.frame.springboot.biz.dto.UserDTO;
import org.ylc.frame.springboot.biz.entity.User;
import org.ylc.frame.springboot.biz.params.LoginParam;
import org.ylc.frame.springboot.biz.params.UserPageParams;
import org.ylc.frame.springboot.biz.vo.LoginResponseVO;
import org.ylc.frame.springboot.biz.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
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
     * 上传用户头像
     *
     * @param request 请求
     * @param avatar  头像图片
     * @param id      用户ID（可以为空）
     * @return 头像地址
     */
    String uploadAvatar(HttpServletRequest request, MultipartFile avatar, Long id);

    /**
     * 新增
     *
     * @param dto DTO实体
     */
    void addInfo(UserDTO dto);

    /**
     * 根据ID删除
     *
     * @param id 主键
     */
    void delInfo(long id);

    /**
     * 批量删除
     *
     * @param ids ID列表
     */
    void delMulti(List<Long> ids);

    /**
     * 更新信息
     *
     * @param dto DTO实体
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
     *
     * @param id 主键
     * @return DTO实体
     */
    UserDTO getInfoById(long id);

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
    LoginResponseVO login(LoginParam args);

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
