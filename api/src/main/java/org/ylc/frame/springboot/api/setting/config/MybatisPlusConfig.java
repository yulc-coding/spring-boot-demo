package org.ylc.frame.springboot.api.setting.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * MybatisPlus 配置
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/24
 */
@EnableTransactionManagement
@Configuration
@MapperScan("org.ylc.frame.springboot.api.biz.dao")
public class MybatisPlusConfig {
    /**
     * 分页插件
     * <p>
     * 你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制
     * paginationInterceptor.setLimit
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
