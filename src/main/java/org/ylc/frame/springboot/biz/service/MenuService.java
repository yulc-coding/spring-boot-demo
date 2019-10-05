package org.ylc.frame.springboot.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ylc.frame.springboot.biz.dto.MenuDTO;
import org.ylc.frame.springboot.biz.entity.Menu;
import org.ylc.frame.springboot.biz.vo.MenuVO;
import org.ylc.frame.springboot.common.tree.MenuTree;

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
    void delInfo(long id);

    /**
     * 更新信息
     */
    void updateInfo(MenuDTO dto);

    /**
     * 根据ID查询
     */
    MenuVO getInfoById(long id);

    /**
     * 获取菜单树
     */
    MenuTree getMenuTree();

}
