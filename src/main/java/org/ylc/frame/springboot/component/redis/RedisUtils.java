package org.ylc.frame.springboot.component.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redisTemplate配置类
 * 参考：https://www.cnblogs.com/zeng1994/p/03303c805731afc9aa9c60dbbd32a323.html
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/3/30 15:32
 */
@Component
public class RedisUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 处理 Object 类型
     */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 处理String 类型
     */
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisUtils(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    //=============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(毫秒)
     */
    public void expire(String key, long time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(毫秒) 返回0代表为永久有效
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取相同格式的KEY
     *
     * @param pattern 格式
     * @return set
     */
    public Set<String> keys(String pattern) {
        try {
            return redisTemplate.keys(pattern);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    /**
     * 异步删除
     *
     * @param key 可以传一个值 或多个
     */
    @Async
    public void deleteAsync(String... key) {
        delete(key);
    }

    /**
     * 删除
     *
     * @param keys 集合形式
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 异步删除
     *
     * @param keys keys
     */
    public void deleteAsync(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        return key == null ? null : stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 异步缓存放入
     *
     * @param key   键
     * @param value 值
     */
    @Async
    public void setAsync(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(毫秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public void set(String key, String value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
        } else {
            set(key, value);
        }
    }

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hashGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hashEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     */
    public void hashPut(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(毫秒)
     */
    public void hashPut(String key, Map<String, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     */
    public void hashPut(String key, String item, Object value) {
        redisTemplate.opsForHash().put(key, item, value);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(毫秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     */
    public void hashSet(String key, String item, Object value, long time) {
        redisTemplate.opsForHash().put(key, item, value);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hashDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }


    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return set
     */
    public Set<Object> setMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean setHasKey(String key, Object value) {
        try {
            Boolean hasKey = redisTemplate.opsForSet().isMember(key, value);
            if (hasKey == null) {
                return false;
            }
            return hasKey;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return size
     */
    public long getSetSize(String key) {
        try {
            Long size = redisTemplate.opsForSet().size(key);
            if (size == null) {
                return 0;
            }
            return size;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     */
    public void setAdd(String key, Object... values) {
        try {
            redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long setAdd(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (count == null) {
                return 0;
            }
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     */
    public void setRemove(String key, Object... values) {
        try {
            redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    // ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public List<String> strListGet(String key, long start, long end) {
        try {
            return stringRedisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return long
     */
    public long getListSize(String key) {
        try {
            Long size = redisTemplate.opsForList().size(key);
            if (size == null) {
                return 0;
            }
            return size;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 在已有的list中加入单个信息
     *
     * @param key   键
     * @param value 值
     */
    public boolean strListPushOne(String key, String value) {
        try {
            stringRedisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * 在已有的list中加入单个信息，并设置过期时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(毫秒)
     */
    public boolean strListPushOne(String key, String value, long time) {
        try {
            stringRedisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * 将整个list存入缓存
     *
     * @param key     键
     * @param strList 值
     */
    public void strListPushAll(String key, List<String> strList) {
        stringRedisTemplate.opsForList().rightPushAll(key, strList);
    }

    /**
     * 将整个list存入缓存
     *
     * @param key     键
     * @param strList 值
     */
    public void strListPushAll(String key, List<String> strList, long time) {
        stringRedisTemplate.opsForList().rightPushAll(key, strList);
        if (time > 0) {
            expire(key, time);
        }
    }

}
