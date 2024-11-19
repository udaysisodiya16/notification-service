package com.capstone.notificationservice.consumers;

import com.capstone.notificationservice.dtos.UserNotificationDto;
import com.capstone.notificationservice.services.INotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaUserConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaUserConsumer.class);

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.topic.user.signup.notification}", groupId = "NotificationService")
    public void signupNotification(String message) {
        try {
            UserNotificationDto userNotificationDto = objectMapper.readValue(message, UserNotificationDto.class);
            notificationService.sendUserNotification(userNotificationDto);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "${kafka.topic.password.reset.notification}", groupId = "NotificationService")
    public void passwordResetNotification(String message) {
        try {
            UserNotificationDto userNotificationDto = objectMapper.readValue(message, UserNotificationDto.class);
            notificationService.sendUserNotification(userNotificationDto);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
