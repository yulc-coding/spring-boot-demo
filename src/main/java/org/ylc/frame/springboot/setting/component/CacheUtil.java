package org.ylc.frame.springboot.setting.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 缓存更新工具
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/3/30 15:59
 */
@Component
public class CacheUtil {

    private static final Logger logger = LoggerFactory.getLogger(CacheUtil.class);

    public void updateCache() {
        logger.info("初始化缓存。。。");
    }
}
