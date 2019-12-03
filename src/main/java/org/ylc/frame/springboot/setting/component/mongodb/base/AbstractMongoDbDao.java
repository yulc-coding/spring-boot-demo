package org.ylc.frame.springboot.setting.component.mongodb.base;

import cn.hutool.core.util.ReflectUtil;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * MongoDB通用操作抽象类
 * <p>
 * 参考： https://www.jianshu.com/p/a2dcc02e2767
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/24
 */
public abstract class AbstractMongoDbDao<T> {

    /**
     * 反射获取泛型类型
     *
     * @return class<T></>
     */
    protected abstract Class<T> getEntityClass();

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存一个对象
     *
     * @param entity 实体
     * @return 实体
     */
    public T save(T entity) {
        return this.mongoTemplate.save(entity);
    }

    /**
     * 异步把偶才能
     *
     * @param entity 实体
     * @return 实体
     */
    @Async
    public T saveAsync(T entity) {
        return this.mongoTemplate.save(entity);
    }

    /**
     * 根据id查询对象
     *
     * @param id 主键
     * @return 实体
     */
    public T findById(String id) {
        return this.mongoTemplate.findById(id, this.getEntityClass());
    }

    /**
     * 根据条件查询集合
     *
     * @param entity 查询实体
     * @return 列表
     */
    public List<T> find(T entity) {
        return mongoTemplate.find(getQueryByObject(entity), this.getEntityClass());
    }

    /**
     * 根据条件查询集合
     *
     * @param query 查询条件
     * @return 列表
     */
    public List<T> find(Query query) {
        return mongoTemplate.find(query, this.getEntityClass());
    }

    /**
     * 根据条件查询只返回一个文档
     *
     * @param entity 查询条件
     * @return 实体
     */
    public T queryOne(T entity) {
        return mongoTemplate.findOne(getQueryByObject(entity), this.getEntityClass());
    }

    /***
     * 根据条件分页查询
     * @param entity 查询条件
     * @param curPage 当前页
     * @param pageSize 每页大小
     * @return 列表
     */
    public Pagination<T> getPage(T entity, int curPage, int pageSize) {
        Query query = getQueryByObject(entity);
        // 获取总条数
        long totalCount = getCount(query);
        Pagination<T> pagination = new Pagination<>(curPage, pageSize, totalCount);
        if (totalCount > 0) {
            // skip相当于从那条记录开始
            query.skip(pagination.getFirstResult());
            // 从skip开始,取多少条记录
            query.limit(pageSize);
            List<T> records = this.find(query);
            pagination.setRecords(records);
        }
        return pagination;
    }

    /***
     * 根据条件查询库中符合条件的记录数量
     * @param entity 查询实体
     * @return 条数
     */
    public Long getCount(T entity) {
        return this.mongoTemplate.count(getQueryByObject(entity), this.getEntityClass());
    }

    /***
     * 根据条件查询库中符合条件的记录数量
     * @param query 查询条件
     * @return 条数
     */
    public Long getCount(Query query) {
        return this.mongoTemplate.count(query, this.getEntityClass());
    }

    /***
     * 删除对象
     * @param entity 要删除的对象
     * @return 删除条数
     */
    public long delete(T entity) {
        return this.mongoTemplate.remove(entity).getDeletedCount();
    }

    /**
     * 根据id删除
     *
     * @param id 主键
     */
    public void deleteById(String id) {
        Criteria criteria = Criteria.where("_id").is(id);
        Query query = new Query(criteria);
        T obj = this.mongoTemplate.findOne(query, this.getEntityClass());
        if (obj != null) {
            this.delete(obj);
        }
    }

    /*MongoDB中更新操作分为三种
     * 1：updateFirst     修改第一条
     * 2：updateMulti     修改所有匹配的记录
     * 3：upsert  修改时如果不存在则进行添加操作
     * */

    /**
     * 修改匹配到的第一条记录
     *
     * @param queryEntity  条件
     * @param updateEntity 要更新的数据
     */
    public UpdateResult updateFirst(T queryEntity, T updateEntity) {
        Query query = getQueryByObject(queryEntity);
        Update update = getUpdateByObject(updateEntity);
        return this.mongoTemplate.updateFirst(query, update, this.getEntityClass());
    }

    /**
     * 修改匹配到的所有记录
     *
     * @param queryEntity  条件
     * @param updateEntity 要更新的数据
     */
    public void updateMulti(T queryEntity, T updateEntity) {
        Query query = getQueryByObject(queryEntity);
        Update update = getUpdateByObject(updateEntity);
        this.mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }

    /**
     * 修改匹配到的记录，若不存在该记录则进行添加
     *
     * @param queryEntity  条件
     * @param updateEntity 要更新的数据
     */
    public void updateInsert(T queryEntity, T updateEntity) {
        Query query = getQueryByObject(queryEntity);
        Update update = getUpdateByObject(updateEntity);
        this.mongoTemplate.upsert(query, update, this.getEntityClass());
    }

    /**
     * 将查询条件对象转换为query
     *
     * @param entity 查询条件
     * @return Query
     */
    private Query getQueryByObject(T entity) {
        Criteria criteria = new Criteria();

        Field[] fields = entity.getClass().getDeclaredFields();
        Object filedValue;
        for (Field field : fields) {
            filedValue = ReflectUtil.getFieldValue(entity, field);
            if (null != filedValue) {
                criteria.and(field.getName()).is(filedValue);
            }
        }
        Query query = new Query();
        return query.addCriteria(criteria);
    }


    /**
     * 将查询条件对象转换为update
     *
     * @param entity 条件
     * @return Update
     */
    private Update getUpdateByObject(T entity) {
        Update update = new Update();
        Field[] fields = entity.getClass().getDeclaredFields();
        Object filedValue;
        for (Field field : fields) {
            filedValue = ReflectUtil.getFieldValue(entity, field);
            if (null != filedValue) {
                update.set(field.getName(), filedValue);
            }
        }
        return update;
    }

}
