package com.newco.hackathon.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newco.hackathon.matching.Manager;
import com.newco.hackathon.matching.rule.FirstLNameLastName;
import com.newco.hackathon.matching.rule.MatchRule;
import com.newco.hackathon.matching.rule.SsnLastName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Autowired
    private SsnLastName ssnLastName;

    @Autowired
    private FirstLNameLastName firstLNameLastName;

    @Bean
    public Manager getMatchManager() {
        Manager manager = new Manager();
        manager.setRules(new MatchRule[] {
                ssnLastName,
                firstLNameLastName
        });

        return manager;
    }

    @Bean
    public SsnLastName getSsnLastNameRule() {
        return new SsnLastName();
    }

    @Bean
    public FirstLNameLastName getFirstLNameLastName() {
        return new FirstLNameLastName();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
