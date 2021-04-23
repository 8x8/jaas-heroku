package com.example.demo3.config;


import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .apiInfo(new ApiInfo(
                        "8x8 KEY gen Service API",
                        "8x8 key gen service",
                        "1",
                        "Placeholder Terms of Service",
                        new Contact("8x8", "http://8x8.com", "vmms@8x8.com"),
                        null, null
                        , emptyList()))
                .globalOperationParameters(singletonList(new ParameterBuilder()
                        .name("Authorization")
                        .description("Http request Authorization header")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build())
                );
    }
}
