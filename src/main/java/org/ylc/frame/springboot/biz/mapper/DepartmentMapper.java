package org.ylc.frame.springboot.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface DepartmentMapper extends BaseMapper<Department> {

    List<DepartmentTree> depTreeList();

}
