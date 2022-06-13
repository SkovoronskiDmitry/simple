package com.mastery.java.task.simple.service.jms;

import com.mastery.java.task.simple.dto.Employee;
import com.mastery.java.task.simple.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class JmsService implements MessageListener {

    @Value("${active-mq.topic}")
    private String topic;

    private final EmployeeService employeeService;

    private final JmsTemplate jmsTemplate;


    public void sendMessage(Employee employee) {
        try {
            log.info("Send message with topic: " + topic);
            jmsTemplate.convertAndSend(topic, employee);
        } catch (Exception ex) {
            log.error("Exception during send Message: ", ex);
        }
    }

    @Override
//    @JmsListener(destination = "${active-mq.destination}")
    public void onMessage(Message message) {
        try{
            ObjectMessage objectMessage = (ObjectMessage)message;
            Employee employee = (Employee)objectMessage.getObject();
            employeeService.createEmployee(employee);
            log.info("Received Message: "+ employee.toString());
        } catch(Exception ex) {
            log.error("Received Exception : "+ ex);
        }

    }
}
