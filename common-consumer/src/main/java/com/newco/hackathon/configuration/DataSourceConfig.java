package com.newco.hackathon.configuration;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DataSourceConfig {

    static final String DATABASE_URL = "jdbc.url";
    static final String DATABASE_USER = "jdbc.user";

    @Inject
    private Environment env;

    @Bean
    public DataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(env.getProperty(DATABASE_URL));
        dataSource.setUser(env.getProperty(DATABASE_USER));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
