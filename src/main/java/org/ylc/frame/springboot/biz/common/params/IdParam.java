package org.ylc.frame.springboot.biz.common.params;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * idList
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/12/5 21:58
 */
@Getter
@Setter
@ToString
public class IdParam<T> {

    @NotEmpty(message = "ID列表不能为空")
    private List<T> idList;
}
