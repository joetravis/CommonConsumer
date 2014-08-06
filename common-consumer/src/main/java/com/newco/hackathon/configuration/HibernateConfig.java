package com.newco.hackathon.configuration;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

public class HibernateConfig {

    @Inject
    private Environment env;

    @Inject
    private DataSource dataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory
                .setPackagesToScan(new String[] { "com.newco.hackathon.model" });
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactory.setJpaProperties(hibernateProperties());
        return entityManagerFactory;
    }

    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties() {
            private static final long serialVersionUID = 1L;

            {
                String falseString = Boolean.toString(false);
                setProperty(AvailableSettings.HBM2DDL_AUTO, env.getProperty(
                        AvailableSettings.HBM2DDL_AUTO, falseString));
                setProperty(AvailableSettings.DIALECT,
                        env.getRequiredProperty(AvailableSettings.DIALECT));
                setProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE,
                        env.getProperty(
                                AvailableSettings.USE_SECOND_LEVEL_CACHE,
                                falseString));
                setProperty(AvailableSettings.SHOW_SQL, env.getProperty(
                        AvailableSettings.SHOW_SQL, falseString));
                setProperty(AvailableSettings.FORMAT_SQL, env.getProperty(
                        AvailableSettings.FORMAT_SQL, falseString));
            }
        };
        return properties;
    }

}
