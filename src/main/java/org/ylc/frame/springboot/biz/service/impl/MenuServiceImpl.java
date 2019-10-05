package org.ylc.frame.springboot.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.ylc.frame.springboot.biz.dto.MenuDTO;
import org.ylc.frame.springboot.biz.entity.Menu;
import org.ylc.frame.springboot.biz.mapper.MenuMapper;
import org.ylc.frame.springboot.biz.service.MenuService;
import org.ylc.frame.springboot.biz.vo.MenuVO;
import org.ylc.frame.springboot.common.tree.MenuTree;
import org.ylc.frame.springboot.common.util.OperationCheck;
import org.ylc.frame.springboot.common.util.TreeBuildUtil;

import java.util.ArrayList;
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

    @Override
    public void addInfo(MenuDTO dto) {
        Menu entity = new Menu();
        BeanUtils.copyProperties(dto, entity);
        baseMapper.insert(entity);
    }

    @Override
    public void delInfo(long id) {
        OperationCheck.isExecute(baseMapper.deleteById(id), "无效的数据");
    }

    @Override
    public void updateInfo(MenuDTO dto) {
        Menu entity = new Menu();
        BeanUtils.copyProperties(dto, entity);
        OperationCheck.isExecute(baseMapper.updateById(entity), "无效的数据");

    }

    @Override
    public MenuVO getInfoById(long id) {
        Menu entity = baseMapper.selectById(id);
        MenuVO vo = new MenuVO();
        if (entity != null) {
            BeanUtils.copyProperties(entity, vo);
        }
        return vo;
    }

    @Override
    public MenuTree getMenuTree() {
        List<MenuTree> menuTrees = baseMapper.getMenuList();
        // 首层根目录
        MenuTree menuTree = new MenuTree();
        menuTree.setId(0L);
        menuTree.setName("根目录");
        menuTree.setChildren(new ArrayList<>());
        TreeBuildUtil.build(menuTree, menuTrees);
        return menuTree;
    }
}
