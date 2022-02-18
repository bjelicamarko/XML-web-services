package com.imunizacija.ImunizacijaApp.service;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

@Service
public class MailServiceImpl implements MailService{

    @Override
    public void sendMail(String subject, String text, String sendTo) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {

                return new javax.mail.PasswordAuthentication("kts.sistem@gmail.com", "ktssistem123");
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("kts.sistem@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo));
        msg.setSubject(subject);
        msg.setContent("Poslednji Trzaj", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(text, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();

        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        msg.setText(text);

        Transport.send(msg);
    }
}
