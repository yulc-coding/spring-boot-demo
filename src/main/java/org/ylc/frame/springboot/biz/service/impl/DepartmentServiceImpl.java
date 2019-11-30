package org.ylc.frame.springboot.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    public Long addInfo(DepartmentDTO dto) {
        nameRepeatCheck(dto.getName(), null);
        Department entity = dto.convertToEntity();
        entity.setCode(generateCode(dto.getPid()));
        baseMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void delInfo(long id) {
        OperationCheck.isExecute(baseMapper.deleteById(id), "无效数据");
    }

    @Override
    public void updateInfo(DepartmentDTO dto) {
        Department oldEntity = baseMapper.selectById(dto.getId());
        nameRepeatCheck(dto.getName(), dto.getId());
        ParamCheck.notNull(oldEntity, "无效数据");
        Department entity = dto.convertToEntity();
        baseMapper.updateById(entity);
    }

    @Override
    public DepartmentVO getInfoById(long id) {
        Department entity = baseMapper.selectById(id);
        return DepartmentVO.entityConvertToVo(entity);
    }

    @Override
    public DepartmentTree getDepTree() {
        List<DepartmentTree> depList = baseMapper.depTreeList();
        // 首层根目录
        DepartmentTree depTree = new DepartmentTree();
        depTree.setId(0L);
        depTree.setName("根目录");
        TreeBuildUtil.build(depTree, depList);
        return depTree;
    }

    /**
     * 重名校验
     *
     * @param name 部门名称
     * @param id   主键，更新时需要排除自己
     */
    private void nameRepeatCheck(String name, Long id) {
        int count = baseMapper.selectCount(
                new QueryWrapper<Department>()
                        .eq("name", name)
                        .ne(id != null, "id", id)
        );
        ParamCheck.assertTrue(count <= 0, "已存在相同名称的部门");
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
