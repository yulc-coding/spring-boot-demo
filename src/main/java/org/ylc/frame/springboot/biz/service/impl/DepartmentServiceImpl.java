package org.ylc.frame.springboot.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.ylc.frame.springboot.biz.dto.DepartmentDTO;
import org.ylc.frame.springboot.biz.entity.Department;
import org.ylc.frame.springboot.biz.mapper.DepartmentMapper;
import org.ylc.frame.springboot.biz.service.DepartmentService;
import org.ylc.frame.springboot.biz.vo.DepartmentVO;
import org.ylc.frame.springboot.common.exception.CheckException;
import org.ylc.frame.springboot.common.tree.DepartmentTree;
import org.ylc.frame.springboot.common.util.OperationCheck;
import org.ylc.frame.springboot.common.util.ParamCheck;
import org.ylc.frame.springboot.common.util.TreeBuildUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 部门 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    /**
     * 默认的编码
     */
    private static final String DEFAULT_CODE = "001";

    @Override
    public void addInfo(DepartmentDTO dto) {
        Department entity = new Department();
        BeanUtils.copyProperties(dto, entity);
        entity.setCode(generateCode(dto.getPid()));
        baseMapper.insert(entity);
    }

    @Override
    public void delInfo(long id) {
        OperationCheck.isExecute(baseMapper.deleteById(id), "无效数据");
    }

    @Override
    public void updateInfo(DepartmentDTO dto) {
        Department oldEntity = baseMapper.selectById(dto.getId());
        ParamCheck.notNull(oldEntity, "无效数据");
        Department entity = new Department();
        BeanUtils.copyProperties(dto, entity);
        // 上级部门不一致的，需要重新生成部门编码
        if (!oldEntity.getPid().equals(dto.getPid())) {
            entity.setCode(generateCode(dto.getPid()));
        }
        baseMapper.updateById(entity);
    }

    @Override
    public DepartmentVO getInfoById(long id) {
        Department entity = baseMapper.selectById(id);
        DepartmentVO vo = new DepartmentVO();
        if (entity != null) {
            BeanUtils.copyProperties(entity, vo);
        }
        return vo;
    }

    @Override
    public DepartmentTree getDepTree() {
        List<DepartmentTree> depList = baseMapper.depTreeList();
        // 首层根目录
        DepartmentTree depTree = new DepartmentTree();
        depTree.setId(0L);
        depTree.setName("根目录");
        depTree.setChildren(new ArrayList<>());
        TreeBuildUtil.build(depTree, depList);
        return depTree;
    }

    /**
     * 生成部门编码  001 001 001 001
     *
     * @param pid 上级编码
     * @return code
     */
    private String generateCode(long pid) {
        // 获取相同父节点下的最大部门编号
        Object maxObj = super.getOne(new QueryWrapper<Department>().select("max(code)").eq("pid", pid));
        if (maxObj == null) {
            // 根目录下，001
            if (0 == pid) {
                return DEFAULT_CODE;
            }
            // 非根目录，父类编码+001
            Object pCode = super.getOne(new QueryWrapper<Department>().select("code").eq("id", pid));
            if (pCode.toString().length() >= 12) {
                throw new CheckException("部门层级最多4层");
            }
            return String.format("%s%s", pCode, DEFAULT_CODE);
        }
        String maxCode = maxObj.toString();
        // 获取编码前几位
        String prefix = maxCode.substring(0, maxCode.length() - 3);
        // 最新的排序
        int newIndex = Integer.parseInt(maxCode.substring(maxCode.length() - 3)) + 1;
        return String.format("%s%03d", prefix, newIndex);
    }

}
