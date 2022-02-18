package com.sluzbenik.SluzbenikApp.service;

import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

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

    @Override
    public void sendDzs(String xhtmlDzs, byte[] pdfDzs, String sendTo) throws MessagingException {
        String text = "Poštovani, U prilogu Vam šaljemo digitalni zeleni sertifikat za koji ste podneli zahtev.\n" +
                "Ukoliko je došlo do neke greške, obratite se administratoru.\n" +
                "Dokumenti se nalaze u prilogu. Nemojte odgovarati na ovu poruku, molim Vas, nismo drugari.";

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
        msg.setSubject("DIGITALNI ZELENI SERTIFIKAT");
        msg.setContent("Digitalni zeleni sertifikat", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(text, "text/html");

        MimeBodyPart attachPartXHTML = new MimeBodyPart();
        File tmpFileXhtml;
        try {
            tmpFileXhtml = File.createTempFile("digitalni_zeleni_sertifikat", ".xhtml");
            FileWriter writer = new FileWriter(tmpFileXhtml);
            writer.write(xhtmlDzs);
            writer.close();

            FileDataSource source = new FileDataSource(tmpFileXhtml);
            attachPartXHTML.setDataHandler(new DataHandler(source));
            attachPartXHTML.setFileName("digitalni_zeleni_sertifikat.xhtml");

        } catch (IOException e) {
            e.printStackTrace();
        }

        MimeBodyPart attachPartPDF = new MimeBodyPart();
        File tmpFilePdf;
        try {
            tmpFilePdf = File.createTempFile("digitalni_zeleni_sertifikat", ".pdf");
            FileOutputStream outputStream = new FileOutputStream(tmpFilePdf);
            outputStream.write(pdfDzs);
            outputStream.close();

            FileDataSource source = new FileDataSource(tmpFilePdf);
            attachPartPDF.setDataHandler(new DataHandler(source));
            attachPartPDF.setFileName("digitalni_zeleni_sertifikat.pdf");

        } catch (IOException e) {
            e.printStackTrace();
        }

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachPartXHTML);
        multipart.addBodyPart(attachPartPDF);

        msg.setContent(multipart);

        Transport.send(msg);
    }

    @Override
    public void sendRejection(String subject, String text, String sendTo) throws MessagingException {
        String textToReject = "Postovani,\nVas zahtev za digitalnim zelenim sertifikatom je odbijen.\nRazlog:\n"
                + text + "\n\nNadamo se da se ne ljutite, i da mozemo ostati drugari.\nPozdrav";
        this.sendMail("Zahtev za DZS odbijen", textToReject, sendTo);
    }
}
