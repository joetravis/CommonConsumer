package com.newco.hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class RestApplication {

	public static void main(String[] args) {
		
        SpringApplication.run(RestApplication.class, args);

	}
}
