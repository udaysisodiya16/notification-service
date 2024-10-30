package com.capstone.notificationservice.consumers;

import com.capstone.notificationservice.dtos.MessageDto;
import com.capstone.notificationservice.utils.EmailUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Component
public class SendEmailConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "user_signup_email", groupId = "emailService")
    public void sendEmail(String message) {
        try {
            MessageDto messageDto = objectMapper.readValue(message, MessageDto.class);

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(messageDto.getFrom(), "test");
                }
            };
            Session session = Session.getInstance(props, auth);

            EmailUtil.sendEmail(session, messageDto.getTo(), messageDto.getSubject(), messageDto.getBody());

        } catch (JsonProcessingException | MessagingException | UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
