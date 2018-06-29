package net.engining.sccc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.annotations.ApiOperation;
import net.engining.sccc.config.props.CommonProperties;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 校验器相关的Context配置
 * @author Administrator
 *
 */
@Configuration
//@EnableSwagger2//这个配置移到顶层Config；打开swagger，访问/swagger-ui.html
@Profile({"dev","test","sit","uat"})//TODO 这个配置没有解决prod时不加载swagger
public class Swagger2ContextConfig {
	
	@Autowired
	CommonProperties commonProperties;
	
	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.basePackage(commonProperties.getSwaggerBasePackage()))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger2 RESTful APIs")
                .description("Web服务项目使用的基于Swagger2 RESTful APIs文档")
                .version("0.0.1")
                .build();
    }
}
