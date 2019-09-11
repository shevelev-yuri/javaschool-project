package com.tsystems.ecm.messaging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Class, that provides publishing capability for the messages of events updates to the JMS Broker
 */
@Component
public class JmsPublisher {

    /**
     * Log4j logger.
     */
    private static final Logger log = LogManager.getLogger(JmsPublisher.class);

    /**
     * The JmsTemplate reference.
     */
    private JmsTemplate jmsTemplate;

    /**
     * Topic name constant
     */
    private static final String TOPIC_NAME = "ecm.changes";

    /**
     * All args constructor.
     *
     * @param jmsTemplate
     */
    @Autowired
    public JmsPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Sends the message to the JMS Broker. Topic specified by {@code private static final String TOPIC_NAME} constant.
     *
     * @param message the message to be sent
     */
    public void sendMessage(String message) {
        log.debug("Sending: {}", message);

        jmsTemplate.send(TOPIC_NAME, session -> session.createTextMessage(message));
    }

}