package org.ylc.frame.springboot.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import org.ylc.frame.springboot.biz.entity.Department;
import org.ylc.frame.springboot.common.tree.DepartmentTree;

import java.util.List;

/**
 * <p>
 * 部门 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Component
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 获取部门树结构
     *
     * @return treeList
     */
    List<DepartmentTree> depTreeList();

    /**
     * 根据code获取部门名称
     *
     * @param code 编码
     * @return name
     */
    String selectNameByCode(String code);

}
