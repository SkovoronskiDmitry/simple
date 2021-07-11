package com.mastery.java.task.simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {
        TaskSchedulingAutoConfiguration.class,
        SecurityAutoConfiguration.class
})
//@EnableSwagger2
//@EnableJms
public class SimpleApplication {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mastery.java.task.simple.rest"))
                .build()
                .useDefaultResponseMessages(false);
    }

    public static void main(final String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }
}
