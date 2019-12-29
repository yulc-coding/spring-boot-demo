package org.ylc.frame.springboot.setting.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger配置
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/3/30 11:09
 */
@Configuration
@EnableSwagger2
@Profile("dev")
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        List<Parameter> parameters = new ArrayList<>();

        Parameter tokenPar = new ParameterBuilder()
                // 参数类型支持header, cookie, body, query etc
                .parameterType("header")
                // 参数名
                .name("token")
                // 默认值
                .defaultValue("")
                .description("请输入token")
                // 指定参数值的类型
                .modelRef(new ModelRef("string"))
                // 非必需，这里是全局配置，然而在登陆的时候是不用验证的
                .required(false).build();

        parameters.add(tokenPar);

        Predicate<RequestHandler> sys = RequestHandlerSelectors.basePackage("org.ylc.frame.springboot.biz.sys.controller");
        Predicate<RequestHandler> crawler = RequestHandlerSelectors.basePackage("org.ylc.frame.springboot.biz.crawler.controller");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(Predicates.or(sys, crawler))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2")
                .description("接口列表")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
