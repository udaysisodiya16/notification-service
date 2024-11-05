package com.capstone.notificationservice.utils;

import com.capstone.notificationservice.dtos.EmailDto;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Component
public class EmailUtil {

    /**
     * Utility method to send simple HTML email
     */

    public void sendEmail(EmailDto emailDTO) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("udaysisodiya.scaler@gmail.com", "Uday9521#");
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(emailDTO.getFrom(), false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDTO.getTo()));
        msg.setSubject(emailDTO.getSubject());
        msg.setContent(emailDTO.getBody(), "text/html");
        msg.setSentDate(new Date());
        Transport.send(msg);
    }
}
