package org.ylc.frame.springbootdemo.mongodb;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * mongodb
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/4/16 21:18
 */
public abstract class AbstractBaseMongoTemplate<T> implements ApplicationContextAware {

    private MongoTemplate mongoTemplate;

    private void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MongoTemplate mongoTemplate = applicationContext.getBean(MongoTemplate.class);
        setMongoTemplate(mongoTemplate);
    }


    /**
     * 保存一个对象到mongodb
     *
     * @param bean
     * @return
     */
    public T save(T bean) {
        mongoTemplate.save(bean);
        return bean;
    }

    /**
     * 获取需要操作的实体类class
     *
     * @return
     */
    protected abstract Class<T> getEntityClass();

}
