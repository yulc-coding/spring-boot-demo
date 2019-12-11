package org.ylc.frame.springboot.biz.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import org.ylc.frame.springboot.biz.sys.entity.Role;
import org.ylc.frame.springboot.biz.common.entity.SelectEntity;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Component
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 获取所有角色下拉
     *
     * @return select
     */
    List<SelectEntity<Long>> getRoleSelect();

    /**
     * 获取用户角色下拉
     *
     * @param userId 用户ID
     * @return id, name
     */
    List<SelectEntity<Long>> getUserRoles(Long userId);
}
