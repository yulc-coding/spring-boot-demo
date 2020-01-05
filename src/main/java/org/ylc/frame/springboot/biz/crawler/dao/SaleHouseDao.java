package org.ylc.frame.springboot.biz.crawler.dao;

import org.springframework.stereotype.Repository;
import org.ylc.frame.springboot.biz.crawler.entity.SaleHouse;
import org.ylc.frame.springboot.component.mongodb.base.AbstractMongoDbDao;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019-12-23
 */
@Repository
public class SaleHouseDao extends AbstractMongoDbDao<SaleHouse> {

    @Override
    protected Class<SaleHouse> getEntityClass() {
        return SaleHouse.class;
    }
}
