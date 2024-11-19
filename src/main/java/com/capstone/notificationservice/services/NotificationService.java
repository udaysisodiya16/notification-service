package com.capstone.notificationservice.services;

import com.capstone.notificationservice.dtos.EmailDetailDto;
import com.capstone.notificationservice.dtos.NotificationType;
import com.capstone.notificationservice.dtos.OrderNotificationDto;
import com.capstone.notificationservice.dtos.UserNotificationDto;
import com.capstone.notificationservice.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private EmailUtil emailUtil;

    @Override
    public void sendOrderNotification(OrderNotificationDto orderNotificationDto) throws MessagingException {
        processNotification(orderNotificationDto.getType(), orderNotificationDto.getEmailDetail());
    }

    @Override
    public void sendUserNotification(UserNotificationDto userNotificationDto) throws MessagingException {
        processNotification(userNotificationDto.getType(), userNotificationDto.getEmailDetail());
    }

    private void processNotification(NotificationType type, EmailDetailDto emailDetailDto) throws MessagingException {
        switch (type) {
            case EMAIL:
                emailUtil.sendEmail(emailDetailDto.getFrom(), emailDetailDto.getTo(), emailDetailDto.getSubject(), emailDetailDto.getBody());
                break;
            default:
                throw new UnsupportedOperationException("Notification type not supported");
        }
    }
}
