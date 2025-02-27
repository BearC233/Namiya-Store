package com.namiya.store.project.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("解忧杂货店项目API文档")
                        .version("1.0")
                        .description("符合RESTful规范的接口设计示例")
                        .contact(new Contact()
                                .name("开发者支持")
                                .email("support@example.com")));

    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user-api")
                .pathsToMatch("/user/api/**")
                .build();
    }
    @Bean
    public GroupedOpenApi postApi() {
        return GroupedOpenApi.builder()
                .group("post-api")
                .pathsToMatch("/post/api/**")
                .build();
    }
}