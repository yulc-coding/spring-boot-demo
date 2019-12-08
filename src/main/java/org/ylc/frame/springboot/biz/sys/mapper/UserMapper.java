package org.ylc.frame.springboot.biz.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import org.ylc.frame.springboot.biz.sys.entity.User;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Component
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据id得到名字
     *
     * @param userId 用户ID
     * @return name
     */
    String selectNameById(Long userId);

    /**
     * 获取用户头像地址
     *
     * @param userId 用户ID
     * @return 头像地址
     */
    String selectAvatarById(Long userId);

}
