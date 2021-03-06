package org.ylc.frame.springboot.biz.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.ylc.frame.springboot.biz.sys.entity.Menu;
import org.ylc.frame.springboot.biz.common.tree.BaseTree;
import org.ylc.frame.springboot.biz.common.tree.MenuTree;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取菜单树形列表
     */
    List<MenuTree> getMenuList();

    /**
     * 获取基本的树形结构
     */
    List<BaseTree> getBaseTree();

    /**
     * 获取登录用户的菜单列表
     */
    List<Menu> getUserMenuList(@Param("userId") long userId, @Param("loginFrom") String loginFrom);

    /**
     * 获取用户的权限列表
     *
     * @param userId    用户ID
     * @param loginFrom 登录方式
     * @return list
     */
    List<String> getUserPermissions(@Param("userId") Long userId, @Param("loginFrom") String loginFrom);

}
