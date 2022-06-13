package com.mastery.java.task.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication(exclude = {
        TaskSchedulingAutoConfiguration.class,
        SecurityAutoConfiguration.class
})
public class SimpleApplication implements WebMvcConfigurer {

    public static void main(final String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }
}
