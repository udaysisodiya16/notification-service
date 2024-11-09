package com.capstone.notificationservice.consumers;

import com.capstone.notificationservice.dtos.NotificationDto;
import com.capstone.notificationservice.utils.EmailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = " ${kafka.topic.user.signup.notification}", groupId = "NotificationService")
    public void signupNotification(String message) {
        try {
            NotificationDto notificationDto = objectMapper.readValue(message, NotificationDto.class);
            emailUtil.sendEmail(notificationDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "${kafka.topic.password.reset.notification}", groupId = "NotificationService")
    public void passwordResetNotification(String message) {
        try {
            NotificationDto notificationDto = objectMapper.readValue(message, NotificationDto.class);
            emailUtil.sendEmail(notificationDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
