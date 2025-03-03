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
                        .description("RESTful风格的接口设计,前端提供model")
                        .contact(new Contact()
                                .name("熊C")
                                .email("hanzhe_1230@163.com")));

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
    @Bean
    public GroupedOpenApi capsuleApi() {
        return GroupedOpenApi.builder()
                .group("capsule-api")
                .pathsToMatch("/capsule/api/**")
                .build();
    }
    @Bean
    public GroupedOpenApi commentApi() {
        return GroupedOpenApi.builder()
                .group("comment-api")
                .pathsToMatch("/comment/api/**")
                .build();
    }
}