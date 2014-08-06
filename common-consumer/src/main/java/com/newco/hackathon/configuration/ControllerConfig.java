package com.newco.hackathon.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

public class ControllerConfig {

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(mappingJackson2HttpMessageConverter());
        requestMappingHandlerAdapter.setMessageConverters(messageConverters);
        return requestMappingHandlerAdapter;
    }

}
