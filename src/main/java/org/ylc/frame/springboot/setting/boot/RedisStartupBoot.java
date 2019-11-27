package org.ylc.frame.springboot.setting.boot;

import cn.hutool.core.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.ylc.frame.springboot.biz.service.CacheService;
import org.ylc.frame.springboot.setting.component.redis.RedisUtils;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目启动后初始化Redis缓存
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/3/30 11:29
 */
@Component
@Order(2)
public class RedisStartupBoot implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(RedisStartupBoot.class);

    private final RedisUtils redisUtils;

    private final CacheService cacheService;

    @Autowired
    public RedisStartupBoot(RedisUtils redisUtils, CacheService cacheService) {
        this.redisUtils = redisUtils;
        this.cacheService = cacheService;
    }

    @Override
    public void run(String... args) {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostName = addr.getHostName();
            Map<String, Object> map = new HashMap<>(4);
            map.put("startup-time", DateUtil.today());
            map.put("startup-at", hostName);
            redisUtils.hashPut("startup-info:" + hostName, map);

            // 初始化redis中的数据
            cacheService.updateCache();

        } catch (Exception e) {
            logger.error("redis服务器连接设置参数错误{}", e.getLocalizedMessage());
            System.exit(1);
        }
        logger.info(">>>>....This is 【RedisStartupBoot】 Order=2....<<<<");
    }
}
