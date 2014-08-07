package com.newco.hackathon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newco.hackathon.model.Consumer;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by travisj on 8/7/14.
 */
@Service
public class PublishService {
    /**
     * Constant that represents the property holding the consumer id.
     */
    public static final String PROPERTY_ID = "id";

    /**
     * Constant that represents the content type for messages.
     */
    public static final String CONTENT_TYPE = "text/plain";

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Inject
    private Queue commonConsumerCreate;

    @Inject
    private Queue commonConsumerDelete;

    @Inject
    private Queue commonConsumerUpdate;

    public void created(final Consumer consumer) {
        send(commonConsumerCreate, consumer);
    }

    public void deleted(final Consumer consumer) {
        send(commonConsumerDelete, consumer);
    }

    public void updated(final Consumer consumer) {
        send(commonConsumerUpdate, consumer);
    }

    private void send(Queue queue, Consumer consumer) {
        try {
            amqpTemplate.convertAndSend(queue.getName(), formatConsumer(consumer));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private Message formatConsumer(final Consumer consumer) throws JsonProcessingException {

        String consumerJson = mapper.writeValueAsString(consumer);

        return new Message(consumerJson.getBytes(), getMessageProperties());
    }

    private MessageProperties getMessageProperties() {
        return MessagePropertiesBuilder.newInstance()
                .setContentType(CONTENT_TYPE)
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();
    }
}
