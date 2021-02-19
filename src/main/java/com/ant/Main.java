package com.ant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Starter
 * @author daiyuanyang
 * @date 2021-02-19 20:15
 **/
@SpringBootApplication
@ComponentScan(basePackages = "com")
public class Main {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Main.class);
        application.run(args);
    }
}
