package org.ylc.frame.springboot.biz.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.ylc.frame.springboot.biz.entity.Department;
import org.ylc.frame.springboot.common.validated.InsertGroup;
import org.ylc.frame.springboot.common.validated.UpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 部门
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/10/2 19:50
 */
@Getter
@Setter
public class DepartmentDTO extends AbstractConverter<Department> {

    @NotNull(groups = {UpdateGroup.class}, message = "ID不能为空")
    private Long id;

    @NotBlank(groups = {InsertGroup.class, UpdateGroup.class}, message = "部门名称不能为空")
    @ApiModelProperty(value = "部门名称")
    private String name;

    @NotNull(groups = {InsertGroup.class}, message = "上级部门不能为空")
    @ApiModelProperty(value = "上级部门ID")
    private Long pid;

    @ApiModelProperty(value = "备注")
    private String remark;

    @Override
    public Department convertToEntity() {
        Department dep = new Department();
        BeanUtils.copyProperties(this, dep);
        return dep;
    }
}
