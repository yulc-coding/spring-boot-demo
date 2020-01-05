package org.ylc.frame.springboot.biz.crawler.dao;

import org.springframework.stereotype.Repository;
import org.ylc.frame.springboot.biz.crawler.entity.NewHouse;
import org.ylc.frame.springboot.component.mongodb.base.AbstractMongoDbDao;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 楼盘数据操作
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019-12-23
 */
@Repository
public class NewHouseDao extends AbstractMongoDbDao<NewHouse> {
    @Override
    protected Class<NewHouse> getEntityClass() {
        return NewHouse.class;
    }
}
