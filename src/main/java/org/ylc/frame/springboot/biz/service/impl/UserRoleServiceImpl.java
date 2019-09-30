package org.ylc.frame.springboot.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.ylc.frame.springboot.biz.entity.UserRole;
import org.ylc.frame.springboot.biz.mapper.UserRoleMapper;
import org.ylc.frame.springboot.biz.service.UserRoleService;

/**
 * <p>
 * 用户、角色关联表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
