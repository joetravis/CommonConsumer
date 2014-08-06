package com.newco.hackathon.configuration;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Properties;

@Configuration
@PropertySources({
        @PropertySource("classpath:META-INF/jdbc.properties"),
        @PropertySource("classpath:META-INF/hibernate.properties")
})
public class DataSourceConfig {

    static final String DATABASE_URL = "jdbc.url";
    static final String DATABASE_USER = "jdbc.user";
    static final String HIBERNATE_DIALECT = "org.hibernate.dialect.H2Dialect";
    static final String ENTITIES_PACKAGE = "entitymanager.packages.to.scan";

    @Inject
    private Environment env;

    @Bean
    public DataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(env.getProperty(DATABASE_URL));
        dataSource.setUser(env.getProperty(DATABASE_USER));
        return dataSource;
    }

    /**
     * @return Entity Manager Factory
     */
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(ENTITIES_PACKAGE);

        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", HIBERNATE_DIALECT);

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean(name = "jpaVendorAdapter")
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabasePlatform(HIBERNATE_DIALECT);
        return jpaVendorAdapter;
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

}
