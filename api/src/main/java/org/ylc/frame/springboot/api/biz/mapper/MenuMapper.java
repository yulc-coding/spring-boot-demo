package org.ylc.frame.springboot.api.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.ylc.frame.springboot.common.entity.Menu;

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

    List<String> getEmpPermissions(String userId, String loginFrom);

}
