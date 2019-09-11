package com.tsystems.ecm.configuration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfig {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String USERNAME = "admin";
    private static final String PSWD = "admin";

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setUserName(USERNAME);
        connectionFactory.setPassword(PSWD);
        connectionFactory.setBrokerURL(BROKER_URL);

        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setPubSubDomain(true);

        return template;
    }
}
