package org.ylc.frame.springboot.biz.vo;

import org.springframework.beans.BeanUtils;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 基础VO类
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/10/21
 */
public class BaseVO {

    public static BaseVO entityConvertVo(Object entity) {
        BaseVO vo = new BaseVO();
        if (entity != null) {
            BeanUtils.copyProperties(entity, vo);
        }
        return vo;
    }

}
