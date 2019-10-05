package org.ylc.frame.springboot.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.ylc.frame.springboot.biz.dto.RoleDTO;
import org.ylc.frame.springboot.biz.entity.Role;
import org.ylc.frame.springboot.biz.vo.RoleVO;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
public interface RoleService extends IService<Role> {

    /**
     * 新增
     */
    void addInfo(RoleDTO dto);

    /**
     * 根据ID删除
     */
    void delInfo(long id);

    /**
     * 更新信息
     */
    void updateInfo(RoleDTO dto);

    /**
     * 获取列表
     */
    List<RoleVO> getList();


}
