package com.dci;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Machao {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Machao.class);
        springApplication.setBannerMode(Banner.Mode.LOG);
        springApplication.run(args);
    }

}

