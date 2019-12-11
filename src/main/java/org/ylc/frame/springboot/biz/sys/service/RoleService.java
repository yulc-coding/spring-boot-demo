package org.ylc.frame.springboot.biz.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ylc.frame.springboot.biz.common.entity.SelectEntity;
import org.ylc.frame.springboot.biz.sys.dto.RoleDTO;
import org.ylc.frame.springboot.biz.sys.entity.Role;
import org.ylc.frame.springboot.biz.sys.vo.RoleVO;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
public interface RoleService extends IService<Role> {

    /**
     * 新增
     */
    void addInfo(RoleDTO dto);

    /**
     * 根据ID删除
     */
    void delInfo(Long id);

    /**
     * 更新信息
     */
    void updateInfo(RoleDTO dto);

    /**
     * 获取角色列表
     */
    List<RoleVO> getList();

    /**
     * 获取角色下拉
     *
     * @return select
     */
    List<SelectEntity<Long>> getRoleSelect();

    /**
     * 获取角色的菜单列表
     *
     * @param roleId 角色ID
     * @return menus
     */
    List<String> roleMenus(Long roleId);

    /**
     * 角色绑定菜单
     *
     * @param roleId  角色ID
     * @param menuIds 菜单列表
     */
    void bindMenus(Long roleId, List<Long> menuIds);
}
