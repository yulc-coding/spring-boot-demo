package org.ylc.frame.springboot.biz.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.ylc.frame.springboot.biz.sys.dto.DepartmentDTO;
import org.ylc.frame.springboot.biz.sys.entity.Department;
import org.ylc.frame.springboot.biz.sys.mapper.DepartmentMapper;
import org.ylc.frame.springboot.biz.sys.service.DepartmentService;
import org.ylc.frame.springboot.biz.sys.vo.DepartmentVO;
import org.ylc.frame.springboot.setting.exception.CheckException;
import org.ylc.frame.springboot.biz.common.tree.DepartmentTree;
import org.ylc.frame.springboot.util.OperationCheck;
import org.ylc.frame.springboot.util.ParamCheck;
import org.ylc.frame.springboot.util.TreeBuildUtil;

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

    /**
     * 部门编码最大长度，用于显示部门树的层数
     */
    private static final int MAX_DEP_CODE_LENGTH = 12;

    /**
     * 新增
     * 重名校验
     * 部门编码生成
     *
     * @return vo
     */
    @Override
    public DepartmentVO addInfo(DepartmentDTO dto) {
        nameRepeatCheck(dto.getName(), null);
        Department entity = dto.convertToEntity();
        entity.setCode(generateCode(dto.getPid()));
        baseMapper.insert(entity);
        DepartmentVO vo = new DepartmentVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    /**
     * 删除
     * 是否存在子菜单校验
     */
    @Override
    public void delInfo(long id) {
        int count = super.count(
                new QueryWrapper<Department>()
                        .eq("pid", id)
        );
        ParamCheck.assertTrue(count <= 0, "存在子部门，请先删除子部门");
        OperationCheck.isExecute(baseMapper.deleteById(id), "无效数据");
    }

    /**
     * 更新
     * 重名校验
     * !！!这里默认不可以调整上级部门，如果可以调整，调整上级部门后，需要重置当前的部门编码
     */
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
        String maxCode = super.getObj(
                new QueryWrapper<Department>().select("max(code)").eq("pid", pid),
                Object::toString
        );
        if (maxCode == null) {
            // 根目录下，001
            if (0 == pid) {
                return DEFAULT_CODE;
            }
            // 非根目录，父类编码+001
            String pCode = super.getObj(
                    new QueryWrapper<Department>().select("code").eq("id", pid),
                    Object::toString
            );
            if (pCode.length() >= MAX_DEP_CODE_LENGTH) {
                throw new CheckException("部门层级最多4层");
            }
            return String.format("%s%s", pCode, DEFAULT_CODE);
        }
        // 获取编码前几位
        String prefix = maxCode.substring(0, maxCode.length() - 3);
        // 最新的排序
        int newIndex = Integer.parseInt(maxCode.substring(maxCode.length() - 3)) + 1;
        return String.format("%s%03d", prefix, newIndex);
    }

}
