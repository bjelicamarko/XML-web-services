package com.imunizacija.ImunizacijaApp.service;


import com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju.Saglasnost;

public interface SaglasnostService {

    Saglasnost findOneById(String id);

    void createNewConsent(String Saglasnost);

    void updateConsent(String saglasnost);
}
