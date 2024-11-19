package com.capstone.notificationservice.consumers;

import com.capstone.notificationservice.dtos.OrderNotificationDto;
import com.capstone.notificationservice.services.INotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaOrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaOrderConsumer.class);

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.topic.order.notification}", groupId = "NotificationService")
    public void orderNotification(String message) {
        try {
            OrderNotificationDto orderNotificationDto = objectMapper.readValue(message, OrderNotificationDto.class);
            notificationService.sendOrderNotification(orderNotificationDto);
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage(), exception);
        }
    }
}
