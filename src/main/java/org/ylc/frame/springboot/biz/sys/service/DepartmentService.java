package org.ylc.frame.springboot.biz.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ylc.frame.springboot.biz.sys.dto.DepartmentDTO;
import org.ylc.frame.springboot.biz.sys.entity.Department;
import org.ylc.frame.springboot.biz.sys.vo.DepartmentVO;
import org.ylc.frame.springboot.biz.common.tree.DepartmentTree;

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
     *
     * @param dto 参数
     * @return VO
     */
    DepartmentVO addInfo(DepartmentDTO dto);

    /**
     * 根据ID删除
     *
     * @param id 主键
     */
    void delInfo(long id);

    /**
     * 更新信息
     *
     * @param dto 参数
     */
    void updateInfo(DepartmentDTO dto);

    /**
     * 根据ID查询
     *
     * @param id 主键
     * @return VO
     */
    DepartmentVO getInfoById(long id);

    /**
     * 返回带有根节点的部门树，需要具体数列表可以只取 children字段
     *
     * @return tree
     */
    DepartmentTree getDepTree();
}
