package com.rabbiter.bms.config;


import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig
{
        public Docket createApi(){
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.miao.springbootswagger"))
                    .paths(PathSelectors.any())
                    .build();
        }

        // 创建api的基本信息
        private ApiInfo apiInfo(){
            return new ApiInfoBuilder()
                    .title("springBoot 整合 Swagger2 实例")
                    .description("更多技术内容分享见博客：https://blog.csdn.net/qq_24871519")
                    .termsOfServiceUrl("https://blog.csdn.net/qq_24871519")
                    .version("1.0")
                    .build();
        }
}

