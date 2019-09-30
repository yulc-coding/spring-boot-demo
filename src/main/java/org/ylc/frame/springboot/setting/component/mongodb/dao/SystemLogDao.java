package org.ylc.frame.springboot.setting.component.mongodb.dao;

import org.springframework.stereotype.Repository;
import org.ylc.frame.springboot.setting.component.mongodb.base.AbstractMongoDbDao;
import org.ylc.frame.springboot.setting.component.mongodb.entity.SystemLog;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 系统日志
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/24
 */
@Repository
public class SystemLogDao extends AbstractMongoDbDao<SystemLog> {

    @Override
    protected Class<SystemLog> getEntityClass() {
        return SystemLog.class;
    }

}
