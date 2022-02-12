package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju.Saglasnost;

import javax.mail.MessagingException;

public interface SaglasnostService {

    void createNewConsent(Saglasnost Saglasnost);
}
