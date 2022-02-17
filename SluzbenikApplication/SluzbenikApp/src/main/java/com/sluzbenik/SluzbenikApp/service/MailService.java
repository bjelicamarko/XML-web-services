package com.sluzbenik.SluzbenikApp.service;

import javax.mail.MessagingException;

public interface MailService {
    void sendMail(String subject, String text, String sendTo) throws MessagingException;

    void sendDzs(String xhtmlDzs, byte[] pdfDzs, String sendTo) throws MessagingException;
}
