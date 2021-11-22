package com.dci.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@Configuration
public class Swagger2Conf {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("wanliw")
                .apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.dci.controller"))
                .build();
    }
    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("wwl")
                .apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.dci.controller"))
                .build();
    }
    private ApiInfo apiInfo(){
        //作者信息
        Contact contact = new Contact("wanliw","http://www.chinadci.com/","1272913141@qq.com");
        return new ApiInfo(
             "Chinadci-Machao-Api",
             "描述",
             "1.0",
             "http://www.chinadci.com/",
                contact,
             "Apache 2.0",
             "http://www.chinadci.com/",
             new ArrayList());
    }
}
