package org.ylc.frame.springboot.biz.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ylc.frame.springboot.biz.sys.dto.MenuDTO;
import org.ylc.frame.springboot.biz.sys.entity.Menu;
import org.ylc.frame.springboot.biz.sys.vo.MenuVO;
import org.ylc.frame.springboot.biz.common.tree.BaseTree;
import org.ylc.frame.springboot.biz.common.tree.MenuTree;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
public interface MenuService extends IService<Menu> {

    /**
     * 新增
     */
    void addInfo(MenuDTO dto);

    /**
     * 根据ID删除
     */
    void delInfo(Long id);

    /**
     * 更新信息
     */
    void updateInfo(MenuDTO dto);

    /**
     * 根据ID查询
     */
    MenuVO getInfoById(Long id);

    /**
     * 获取菜单树
     */
    MenuTree getMenuTree();

    /**
     * 获取基本的树形结构
     *
     * @return baseTree
     */
    BaseTree getBaseTree();

}
