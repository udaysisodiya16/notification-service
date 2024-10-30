package com.capstone.notificationservice.consumers;

import com.capstone.notificationservice.dtos.EmailDto;
import com.capstone.notificationservice.utils.EmailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SendEmailConsumer {

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "user_signup_email", groupId = "emailService")
    public void userSignupEmail(String message) {
        try {
            EmailDto emailDto = objectMapper.readValue(message, EmailDto.class);
            emailUtil.sendEmail(emailDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "password_reset_email", groupId = "emailService")
    public void passwordResetEmail(String message) {
        try {
            EmailDto emailDto = objectMapper.readValue(message, EmailDto.class);
            emailUtil.sendEmail(emailDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
