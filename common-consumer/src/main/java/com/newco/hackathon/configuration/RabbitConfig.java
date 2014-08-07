package com.newco.hackathon.configuration;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * Created by travisj on 8/7/14.
 */
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
        return new RabbitAdmin(getQueueConnectionFactory());
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

    @Bean
     public Queue commonConsumerCreate() throws IOException {
        Connection connection = getQueueConnectionFactory().createConnection();
        Channel channel = connection.createChannel(true);
        channel.exchangeDeclare(createName, "fanout");
        Queue queue = new Queue(createName);
        getAmqpAdmin().declareQueue(queue);
        /*binding the created queue to exchange*/
        channel.queueBind(queue.getName(), createName, "");
        return queue;
    }

    @Bean
    public Queue commonConsumerDelete() throws IOException {
        Connection connection = getQueueConnectionFactory().createConnection();
        Channel channel = connection.createChannel(true);
        channel.exchangeDeclare(deleteName, "fanout");
        Queue queue = new Queue(deleteName);
        getAmqpAdmin().declareQueue(queue);
        /*binding the created queue to exchange*/
        channel.queueBind(queue.getName(), deleteName, "");
        return queue;
    }

    @Bean
    public Queue commonConsumerUpdate() throws IOException {
        Connection connection = getQueueConnectionFactory().createConnection();
        Channel channel = connection.createChannel(true);
        channel.exchangeDeclare(updateName, "fanout");
        Queue queue = new Queue(updateName);
        getAmqpAdmin().declareQueue(queue);
        /*binding the created queue to exchange*/
        channel.queueBind(queue.getName(), updateName, "");
        return queue;
    }
}
