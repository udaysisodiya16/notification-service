package com.capstone.notificationservice.consumers;

import com.capstone.notificationservice.dtos.OrderNotificationDto;
import com.capstone.notificationservice.utils.EmailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaOrderConsumer {

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = " ${kafka.topic.order.notification}", groupId = "NotificationService")
    public void orderNotification(String message) {
        try {
            OrderNotificationDto orderNotificationDto = objectMapper.readValue(message, OrderNotificationDto.class);
            emailUtil.sendEmail(orderNotificationDto.getFrom(), orderNotificationDto.getTo(), orderNotificationDto.getSubject(), orderNotificationDto.getBody());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
