package org.ylc.frame.springboot.setting.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 启动检查
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/3/30 11:34
 */
@Component
@Order(8)
public class AppStartupBoot implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(AppStartupBoot.class);

    @Value("${server.port}")
    private String port;

    @Override
    public void run(String... strings) {
        logger.info("==========================================================================");
        logger.info(String.format("启动成功，后端API接口 -请访问：http://localhost:%s/swagger-ui.html", port));
        logger.info("==========================================================================");
    }
}
