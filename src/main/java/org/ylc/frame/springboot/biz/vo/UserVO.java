package org.ylc.frame.springboot.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.ylc.frame.springboot.biz.entity.User;
import org.ylc.frame.springboot.common.constant.EnumConst;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-10-05
 */
@Getter
@Setter
public class UserVO {

    @ApiModelProperty(value = "员工编号")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "所属部门")
    private String depName;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 实体转换为vo
     */
    public static UserVO entityConvertToVo(User entity) {
        UserVO vo = new UserVO();
        if (entity != null) {
            BeanUtils.copyProperties(entity, vo);
            // 性别
            vo.setGender(EnumConst.UserGenderEnum.getValueByCode(entity.getGender()));
            vo.setState(EnumConst.UserStateEnum.getValueByCode(entity.getState()));
        }
        return vo;
    }

}
