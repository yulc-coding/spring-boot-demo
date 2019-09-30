package org.ylc.frame.springboot.setting.Handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.ylc.frame.springboot.common.util.ThreadLocalUtils;

import java.time.LocalDateTime;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 自动填充
 * 如果在其他线程或者定时任务中进行新增、更新操作时，
 * ThreadLocalUtils.getUserId() 可能取不到，需要额外处理
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/26
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setInsertFieldValByName("createUser", ThreadLocalUtils.getUserId(), metaObject);
        this.setInsertFieldValByName("createTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setUpdateFieldValByName("updateUser", ThreadLocalUtils.getUserId(), metaObject);
        this.setUpdateFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

}
