package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;

import javax.mail.MessagingException;

public interface InteresovanjeService {
    Interesovanje findOneById(String id);

    void createNewInterest(Interesovanje interesovanje) throws MessagingException;
}
