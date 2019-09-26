package org.ylc.frame.springboot.api.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.ylc.frame.springboot.api.biz.mapper.UserMapper;
import org.ylc.frame.springboot.common.entity.User;
import org.ylc.frame.springboot.common.service.UserService;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
