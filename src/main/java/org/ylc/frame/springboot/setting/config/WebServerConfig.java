package org.ylc.frame.springboot.setting.config;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 文件上传配置
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/12/4 20:32
 */
@Configuration
public class WebServerConfig {

    @Value("${doc-base.path}")
    String docBasePath;

    @Value("${spring.servlet.multipart.location}")
    String tempLocation;

    private static final Logger logger = LoggerFactory.getLogger(WebServerConfig.class);

    @Bean
    public ConfigurableServletWebServerFactory tomcatEmbedded() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        /*
         * 解决多次进入全局异常的办法
         * 修改tomcat的maxSwallowSize配置
         */
        tomcat.addConnectorCustomizers(connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                // -1 means unlimited, accept bytes
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });
        // 配置文件上传的根目录
        File documentRoot = new File(docBasePath);
        dirExists(documentRoot);
        File documentTemp = new File(tempLocation);
        dirExists(documentTemp);
        tomcat.setDocumentRoot(documentRoot);
        return tomcat;
    }

    // 判断文件夹是否存在
    private static void dirExists(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                logger.info("dir exists");
            } else {
                logger.info("the same name file exists, can not create dir");
            }
        } else {
            logger.info("dir not exists, create it ...");
            if (file.mkdirs()) {
                logger.info("dir create success...");
            } else {
                logger.info("dir create fail...");
            }
        }
    }
}
