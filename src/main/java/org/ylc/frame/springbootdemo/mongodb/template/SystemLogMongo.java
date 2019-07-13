package org.ylc.frame.springbootdemo.mongodb.template;

import org.springframework.stereotype.Component;
import org.ylc.frame.springbootdemo.mongodb.AbstractBaseMongoTemplate;
import org.ylc.frame.springbootdemo.mongodb.bean.SystemLog;

/**
 * 日志模板
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/4/16 21:23
 */
@Component
public class SystemLogMongo extends AbstractBaseMongoTemplate<SystemLog> {

    @Override
    protected Class<SystemLog> getEntityClass() {
        return SystemLog.class;
    }
}
