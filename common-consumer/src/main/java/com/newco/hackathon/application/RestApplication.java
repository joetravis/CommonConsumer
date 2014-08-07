package com.newco.hackathon.application;

import com.newco.hackathon.configuration.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.newco.hackathon.configuration.ControllerConfig;
import com.newco.hackathon.configuration.DataSourceConfig;
import com.newco.hackathon.configuration.HibernateConfig;
import com.newco.hackathon.repository.ConsumerRepository;

@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@EnableJpaRepositories(basePackageClasses = { ConsumerRepository.class })
@ComponentScan({ "com.newco.hackathon.controller",
        "com.newco.hackathon.service" })
@Import({ ControllerConfig.class, DataSourceConfig.class, HibernateConfig.class, AppConfig.class})
@ImportResource("classpath:META-INF/elasticsearchContext.xml")
public class RestApplication {

    public static void main(String[] args) {

        SpringApplication.run(RestApplication.class, args);

    }
}
