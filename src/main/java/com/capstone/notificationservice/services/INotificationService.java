package com.capstone.notificationservice.services;

import com.capstone.notificationservice.dtos.OrderNotificationDto;
import com.capstone.notificationservice.dtos.UserNotificationDto;

import javax.mail.MessagingException;

public interface INotificationService {

    void sendOrderNotification(OrderNotificationDto orderNotificationDto) throws MessagingException;

    void sendUserNotification(UserNotificationDto userNotificationDto) throws MessagingException;
}
