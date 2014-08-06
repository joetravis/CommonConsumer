package com.newco.hackathon.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.newco.hackathon.configuration.ControllerConfig;
import com.newco.hackathon.configuration.DataSourceConfig;

@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan({ "com.newco.hackathon.controller" })
@Import({ ControllerConfig.class, DataSourceConfig.class })
@PropertySource("classpath:META-INF/jdbc.properties")
public class RestApplication {

    public static void main(String[] args) {

        SpringApplication.run(RestApplication.class, args);

    }
}
