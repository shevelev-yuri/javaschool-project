package com.tsystems.ecm.messaging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsPublisher {

    private final static Logger log = LogManager.getLogger(JmsPublisher.class);

    private JmsTemplate jmsTemplate;

    private final static String TOPIC_NAME = "ecm.changes";

    @Autowired
    public JmsPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String message) {
        log.debug("Sending: {}", message);

        jmsTemplate.send(TOPIC_NAME, session -> session.createTextMessage(message));
    }

    public void sendObject(Object message) {
        log.debug("Sending: {}", message.toString());

        jmsTemplate.convertAndSend(TOPIC_NAME, message);
    }
}