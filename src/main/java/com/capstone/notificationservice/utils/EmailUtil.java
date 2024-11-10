package com.capstone.notificationservice.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Component
public class EmailUtil {

    @Value("${system.email.id}")
    private String systemEmailId;

    @Value("${system.email.password}")
    private String systemEmailPassword;

    /**
     * Utility method to send simple HTML email
     */

    public void sendEmail(String from, String to, String subject, String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(systemEmailId, systemEmailPassword);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from, false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setSubject(subject);
        msg.setContent(body, "text/html");
        msg.setSentDate(new Date());
        Transport.send(msg);
    }
}
