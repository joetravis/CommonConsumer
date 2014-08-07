package com.newco.hackathon.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newco.hackathon.SimpleCORSFilter;
import com.newco.hackathon.matching.Manager;
import com.newco.hackathon.matching.rule.Email;
import com.newco.hackathon.matching.rule.FirstNameLastName;
import com.newco.hackathon.matching.rule.MatchRule;
import com.newco.hackathon.matching.rule.SsnLastName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class AppConfig {

    @Bean
    public Manager getMatchManager() {
        Manager manager = new Manager();
        manager.setRules(new MatchRule[] {
                getSsnLastNameRule(),
                getEmailRule(),
                getFirstNameLastNameRule()
        });

        return manager;
    }

    @Bean
    public SsnLastName getSsnLastNameRule() {
        return new SsnLastName();
    }

    @Bean
    public FirstNameLastName getFirstNameLastNameRule() {
        return new FirstNameLastName();
    }

    @Bean
    public Email getEmailRule() {
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
