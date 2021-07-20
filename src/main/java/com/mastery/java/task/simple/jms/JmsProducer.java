package com.mastery.java.task.simple.jms;

import com.mastery.java.task.simple.dto.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsProducer {

private static final Logger LOGGER = LoggerFactory.getLogger(JmsProducer.class);

    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${active-mq.topic}")
    private String topic;

    public void sendMessage(Employee employee){
        try{
            LOGGER.info("Send message with topic: "+ topic);
            jmsTemplate.convertAndSend(topic, employee);
        } catch(Exception ex){
            LOGGER.error("Exception during send Message: ", ex);
        }
    }
}
