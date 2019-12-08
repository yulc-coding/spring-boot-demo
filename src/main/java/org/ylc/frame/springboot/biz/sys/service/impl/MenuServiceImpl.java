package org.ylc.frame.springboot.biz.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ylc.frame.springboot.biz.sys.dto.MenuDTO;
import org.ylc.frame.springboot.biz.sys.entity.Menu;
import org.ylc.frame.springboot.biz.sys.entity.RoleMenu;
import org.ylc.frame.springboot.biz.sys.mapper.MenuMapper;
import org.ylc.frame.springboot.biz.sys.mapper.RoleMenuMapper;
import org.ylc.frame.springboot.biz.sys.service.MenuService;
import org.ylc.frame.springboot.biz.sys.vo.MenuVO;
import org.ylc.frame.springboot.biz.common.tree.BaseTree;
import org.ylc.frame.springboot.biz.common.tree.MenuTree;
import org.ylc.frame.springboot.util.OperationCheck;
import org.ylc.frame.springboot.util.ParamCheck;
import org.ylc.frame.springboot.util.TreeBuildUtil;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final RoleMenuMapper roleMenuMapper;

    public MenuServiceImpl(RoleMenuMapper roleMenuMapper) {
        this.roleMenuMapper = roleMenuMapper;
    }

    @Override
    public void addInfo(MenuDTO dto) {
        Menu entity = dto.convertToEntity();
        baseMapper.insert(entity);
    }

    /**
     * 存在子菜单的不能删除
     * 删除菜单
     * 删除角色菜单关系表
     *
     * @param id 主键
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delInfo(Long id) {
        int children = baseMapper.selectCount(
                new QueryWrapper<Menu>()
                        .eq("pid", id)
        );
        ParamCheck.notExists(children, "删除失败：存在子节点");

        baseMapper.deleteById(id);
        roleMenuMapper.delete(
                new QueryWrapper<RoleMenu>()
                        .eq("menu_id", id)
        );
    }

    @Override
    public void updateInfo(MenuDTO dto) {
        Menu entity = dto.convertToEntity();
        OperationCheck.isExecute(baseMapper.updateById(entity), "无效的数据");

    }

    @Override
    public MenuVO getInfoById(Long id) {
        Menu entity = baseMapper.selectById(id);
        return MenuVO.entityConvertToVo(entity);
    }

    @Override
    public MenuTree getMenuTree() {
        List<MenuTree> menuTrees = baseMapper.getMenuList();
        // 首层根目录
        MenuTree menuTree = new MenuTree();
        menuTree.setId(0L);
        menuTree.setName("根目录");
        TreeBuildUtil.build(menuTree, menuTrees);
        return menuTree;
    }

    @Override
    public BaseTree getBaseTree() {
        List<BaseTree> baseTrees = baseMapper.getBaseTree();
        // 首层根目录
        BaseTree baseTree = new BaseTree();
        baseTree.setId(0L);
        baseTree.setName("根目录");
        TreeBuildUtil.build(baseTree, baseTrees);
        return baseTree;
    }
}
