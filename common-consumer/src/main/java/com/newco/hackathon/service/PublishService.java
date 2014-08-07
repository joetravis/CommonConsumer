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

@Service
public class PublishService {
    public static final String createExchange = "hackathon.create";
    public static final String updateExchange = "hackathon.update";
    public static final String deleteExchange = "hackathon.delete";

    /**
     * Constant that represents the content type for messages.
     */
    public static final String CONTENT_TYPE = "text/plain";

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ObjectMapper mapper;

    public void created(final Consumer consumer) {
        send(createExchange, consumer);
    }

    public void deleted(final Consumer consumer) {
        send(deleteExchange, consumer);
    }

    public void updated(final Consumer consumer) {
        send(updateExchange, consumer);
    }

    private void send(String exchangeName, Consumer consumer) {
        try {
            amqpTemplate.convertAndSend(exchangeName, "#", formatConsumer(consumer));
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
