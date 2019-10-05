package org.ylc.frame.springboot.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.ylc.frame.springboot.biz.dto.RoleDTO;
import org.ylc.frame.springboot.biz.entity.Role;
import org.ylc.frame.springboot.biz.mapper.RoleMapper;
import org.ylc.frame.springboot.biz.service.RoleService;
import org.ylc.frame.springboot.biz.vo.RoleVO;
import org.ylc.frame.springboot.common.util.OperationCheck;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public void addInfo(RoleDTO dto) {
        Role entity = new Role(null, dto.getName(), dto.getRemark());
        baseMapper.insert(entity);
    }

    @Override
    public void delInfo(long id) {
        OperationCheck.isExecute(baseMapper.deleteById(id), "无效数据");
    }

    @Override
    public void updateInfo(RoleDTO dto) {
        Role entity = new Role(dto.getId(), dto.getName(), dto.getRemark());
        OperationCheck.isExecute(baseMapper.updateById(entity), "无效数据");
    }

    @Override
    public List<RoleVO> getList() {
        List<Role> roles = baseMapper.selectList(new QueryWrapper<>());
        List<RoleVO> voList = new ArrayList<>();
        for (Role role : roles) {
            voList.add(new RoleVO(role.getId(), role.getName(), role.getRemark()));
        }
        return voList;
    }
}
