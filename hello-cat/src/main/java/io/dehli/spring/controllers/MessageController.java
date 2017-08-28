package io.dehli.spring.controllers;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/send_message")
    public ResponseEntity<String> sendMessage() {
        rabbitTemplate.convertAndSend("messages", "Test message...");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @RequestMapping("/get_message")
    public ResponseEntity<String> getMessage() {
        String response = (String)rabbitTemplate.receiveAndConvert("messages");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
