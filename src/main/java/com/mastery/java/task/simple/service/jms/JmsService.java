package com.mastery.java.task.simple.service.jms;

import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.service.employee.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Service
public class JmsService implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsService.class);

    @Value("${active-mq.topic}")
    private String topic;

    private final EmployeeService employeeService;

    private final JmsTemplate jmsTemplate;

    public JmsService(EmployeeService employeeService, JmsTemplate jmsTemplate) {
        this.employeeService = employeeService;
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(Employee employee) {
        try {
            LOGGER.info("Send message with topic: " + topic);
            jmsTemplate.convertAndSend(topic, employee);
        } catch (Exception ex) {
            LOGGER.error("Exception during send Message: ", ex);
        }
    }

    @Override
    @JmsListener(destination = "${active-mq.destination}")
    public void onMessage(Message message) {
        try{
            ObjectMessage objectMessage = (ObjectMessage)message;
            Employee employee = (Employee)objectMessage.getObject();
            employeeService.createEmployee(employee);
            LOGGER.info("Received Message: "+ employee.toString());
        } catch(Exception ex) {
            LOGGER.error("Received Exception : "+ ex);
        }

    }
}
