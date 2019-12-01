package org.ylc.frame.springboot.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ylc.frame.springboot.biz.dto.DepartmentDTO;
import org.ylc.frame.springboot.biz.entity.Department;
import org.ylc.frame.springboot.biz.vo.DepartmentVO;
import org.ylc.frame.springboot.common.tree.DepartmentTree;

/**
 * <p>
 * 部门 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
public interface DepartmentService extends IService<Department> {

    /**
     * 新增
     */
    DepartmentVO addInfo(DepartmentDTO dto);

    /**
     * 根据ID删除
     */
    void delInfo(long id);

    /**
     * 更新信息
     */
    void updateInfo(DepartmentDTO dto);

    /**
     * 根据ID查询
     */
    DepartmentVO getInfoById(long id);

    /**
     * 获取部门树
     */
    DepartmentTree getDepTree();
}
