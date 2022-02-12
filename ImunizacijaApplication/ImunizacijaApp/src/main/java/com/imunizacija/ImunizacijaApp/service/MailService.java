package com.imunizacija.ImunizacijaApp.service;

import javax.mail.MessagingException;

public interface MailService {

    void sendMail(String subject, String text, String sendTo) throws MessagingException;

}
