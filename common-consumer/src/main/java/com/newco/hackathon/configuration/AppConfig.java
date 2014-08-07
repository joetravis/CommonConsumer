package com.newco.hackathon.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newco.hackathon.SimpleCORSFilter;
import com.newco.hackathon.matching.Manager;
import com.newco.hackathon.matching.rule.Email;
import com.newco.hackathon.matching.rule.FirstLNameLastName;
import com.newco.hackathon.matching.rule.MatchRule;
import com.newco.hackathon.matching.rule.SsnLastName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class AppConfig {

    @Autowired
    private SsnLastName ssnLastName;

    @Autowired
    private FirstLNameLastName firstLNameLastName;

    @Autowired
    private Email emailRule;

    @Bean
    public Manager getMatchManager() {
        Manager manager = new Manager();
        manager.setRules(new MatchRule[] {
                ssnLastName,
                emailRule,
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
    public Email getEmail() {
        return new Email();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Filter getCORSFilter() {
        return new SimpleCORSFilter();
    }
}
