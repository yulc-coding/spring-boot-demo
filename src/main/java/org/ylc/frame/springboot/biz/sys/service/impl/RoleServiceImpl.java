package org.ylc.frame.springboot.biz.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ylc.frame.springboot.biz.sys.dto.RoleDTO;
import org.ylc.frame.springboot.biz.sys.entity.Role;
import org.ylc.frame.springboot.biz.sys.entity.RoleMenu;
import org.ylc.frame.springboot.biz.sys.mapper.RoleMapper;
import org.ylc.frame.springboot.biz.sys.service.RoleMenuService;
import org.ylc.frame.springboot.biz.sys.service.RoleService;
import org.ylc.frame.springboot.biz.sys.vo.RoleVO;
import org.ylc.frame.springboot.util.OperationCheck;

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

    private final RoleMenuService roleMenuService;

    @Autowired
    public RoleServiceImpl(RoleMenuService roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    @Override
    public void addInfo(RoleDTO dto) {
        Role entity = new Role(null, dto.getName(), dto.getRemark());
        baseMapper.insert(entity);
    }

    /**
     * 删除角色
     * 删除角色菜单关联表
     *
     * @param id 主键
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delInfo(Long id) {
        OperationCheck.isExecute(baseMapper.deleteById(id), "删除失败");
        roleMenuService.remove(
                new QueryWrapper<RoleMenu>()
                        .eq("role_id", id)
        );
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

    @Override
    public List<String> roleMenus(Long roleId) {
        return roleMenuService.listObjs(
                new QueryWrapper<RoleMenu>()
                        .select("menu_id")
                        .eq("role_id", roleId),
                Object::toString
        );
    }

    /**
     * 绑定角色：
     * 先删除原先的绑定关系
     * 批量插入新的绑定关系
     *
     * @param roleId  角色ID
     * @param menuIds 菜单列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindMenus(Long roleId, List<Long> menuIds) {
        List<RoleMenu> roleMenus = new ArrayList<>();
        for (Long menuId : menuIds) {
            roleMenus.add(new RoleMenu(roleId, menuId));
        }
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
        roleMenuService.saveBatch(roleMenus);
    }
}
