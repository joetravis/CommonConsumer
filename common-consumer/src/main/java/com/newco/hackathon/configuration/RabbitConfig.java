package com.newco.hackathon.configuration;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

@Configuration
@PropertySource("classpath:queue.properties")
public class RabbitConfig {
    /**
     * Queue server.
     */
    @Value("${queue.server.host}")
    private String queueHost;

    /**
     * Port to access queue server.
     */
    @Value("${queue.server.port}")
    private Integer queuePort;

    /**
     * Username for connection.
     */
    @Value("${queue.server.username}")
    private String queueUser;

    /**
     * Password for connection.
     */
    @Value("${queue.server.password}")
    private String queuePassword;

    /**
     * Create exchange/queue name.
     */
    @Value("${common.consumer.create}")
    private String createName;

    /**
     * Update exchange/queue name.
     */
    @Value("${common.consumer.update}")
    private String updateName;

    /**
     * Delete exchange/queue name.
     */
    @Value("${common.consumer.delete}")
    private String deleteName;

    /**
     * Bean for providing AmqpAdmin.
     * @return an AmqpAdmin.
     * @throws java.io.IOException the Exception.
     */
    @Bean(name = "amqpAdmin")
    public AmqpAdmin getAmqpAdmin() throws IOException {
        AmqpAdmin admin = new RabbitAdmin(getQueueConnectionFactory());
        admin.declareExchange(new FanoutExchange(createName, false, true));
        admin.declareExchange(new FanoutExchange(updateName, false, true));
        admin.declareExchange(new FanoutExchange(deleteName, false, true));
        return admin;
    }

    /**
     * Bean for providing messaging ConnectionFactory.
     * @return a ConnectionFactory.
     * @throws IOException the Exception.
     */
    @Bean(name = "queueConnectionFactory")
    public ConnectionFactory getQueueConnectionFactory() throws IOException {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(queueHost);
        connectionFactory.setPort(queuePort);
        connectionFactory.setUsername(queueUser);
        connectionFactory.setPassword(queuePassword);
        return connectionFactory;
    }
}
