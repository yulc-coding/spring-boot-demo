package org.ylc.frame.springboot.biz.common.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ylc.frame.springboot.biz.sys.entity.Department;
import org.ylc.frame.springboot.biz.sys.entity.User;
import org.ylc.frame.springboot.biz.sys.mapper.DepartmentMapper;
import org.ylc.frame.springboot.biz.sys.mapper.UserMapper;
import org.ylc.frame.springboot.component.redis.RedisUtils;
import org.ylc.frame.springboot.constant.CacheConstants;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 缓存相关
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/11/27
 */
@Service
public class CacheService {

    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

    private final RedisUtils redisUtils;

    private final DepartmentMapper departmentMapper;

    private final UserMapper userMapper;

    @Autowired
    public CacheService(RedisUtils redisUtils, DepartmentMapper departmentMapper, UserMapper userMapper) {
        this.redisUtils = redisUtils;
        this.departmentMapper = departmentMapper;
        this.userMapper = userMapper;
    }

    /**
     * 更新缓存信息
     */
    public void updateCache() {
        updateDepName();
        updateUserInfo();
    }


    /**
     * 根据部门code获取部门名称
     *
     * @param code 部门code
     * @return name
     */
    public String getDepNameByCode(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }
        String key = CacheConstants.DEP_NAME_CODE_PREFIX + code;
        String value = redisUtils.get(key);
        if (value == null) {
            return dataSourceReturn(key, departmentMapper.selectNameByCode(code));
        }
        return value;
    }


    /**
     * 获取用户姓名
     *
     * @param userId 用户ID
     * @return name
     */
    public String getUserName(Long userId) {
        if (ObjectUtil.isNull(userId)) {
            return null;
        }
        String key = CacheConstants.USER_NAME_PREFIX + userId;
        String value = redisUtils.get(key);
        if (value == null) {
            return dataSourceReturn(key, userMapper.selectNameById(userId));
        }
        return value;
    }


    /**
     * 获取用户头像地址
     *
     * @param userId 用户ID
     * @return 头像地址
     */
    public String getUserAvatar(Long userId) {
        if (ObjectUtil.isNull(userId)) {
            return null;
        }
        String key = CacheConstants.USER_AVATAR_PREFIX + userId;
        String value = redisUtils.get(key);
        if (value == null) {
            return dataSourceReturn(key, userMapper.selectAvatarById(userId));
        }
        return value;
    }


    /**
     * 缓存中不存在，从数据库中查询
     * <p>
     * 不存在，返回null
     * 存在，更新缓存，返回数据
     */
    private String dataSourceReturn(String key, String name) {
        if (name == null) {
            return null;
        }
        redisUtils.set(key, name);
        return name;
    }

    /**
     * 通用删除缓存
     *
     * @param keyPrefix 前缀
     */
    private void commonDelRedis(String keyPrefix) {
        String delKey = keyPrefix + "*";
        logger.info("==============================  开始删除 {}  ==============================", delKey);
        Set<String> keys = redisUtils.keys(delKey);
        redisUtils.delete(keys);
    }

    /**
     * 更新部门名称
     */
    private void updateDepName() {
        String keyPrefix = CacheConstants.DEP_NAME_CODE_PREFIX;
        commonDelRedis(keyPrefix);
        logger.info("==============================  开始更新部门名称  ==============================");
        List<Department> depList = departmentMapper.selectList(
                new QueryWrapper<Department>()
                        .select("code,name")
        );
        if (CollectionUtil.isNotEmpty(depList)) {
            depList.forEach(o -> redisUtils.set(keyPrefix + o.getCode(), o.getName()));
        }
        logger.info("=============================  完成部门名称更新[{}]  =============================", depList.size());
    }

    /**
     * 更新用户信息
     * 姓名
     * 头像地址
     */
    private void updateUserInfo() {
        String nameKeyPrefix = CacheConstants.USER_NAME_PREFIX;
        commonDelRedis(nameKeyPrefix);
        String avatarKeyPrefix = CacheConstants.USER_AVATAR_PREFIX;
        commonDelRedis(avatarKeyPrefix);
        logger.info("==============================  开始更新用户信息  ==============================");
        List<User> userList = userMapper.selectList(
                new QueryWrapper<User>()
                        .select("id,name,avatar")
        );
        AtomicInteger i = new AtomicInteger();
        if (CollectionUtil.isNotEmpty(userList)) {
            userList.forEach(o -> {
                redisUtils.set(nameKeyPrefix + o.getId(), o.getName());
                if (StrUtil.isNotBlank(o.getAvatar())) {
                    i.getAndIncrement();
                    redisUtils.set(avatarKeyPrefix + o.getId(), o.getAvatar());
                }
            });
        }
        logger.info("==========================  完成用户信息更新，姓名[{}],头像[{}]  ==========================",
                userList.size(), i.get());
    }
}
