package com.mastery.java.task.simple;

import com.mastery.java.task.simple.service.converter.CustomConverterFromIntegerToEmployee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication(exclude = {
        TaskSchedulingAutoConfiguration.class,
        SecurityAutoConfiguration.class
})
public class SimpleApplication implements WebMvcConfigurer {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mastery.java.task.simple.rest"))
                .build()
                .useDefaultResponseMessages(false);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CustomConverterFromIntegerToEmployee());
    }

    public static void main(final String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }
}
