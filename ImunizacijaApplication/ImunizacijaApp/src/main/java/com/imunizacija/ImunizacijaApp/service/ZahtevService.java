package com.imunizacija.ImunizacijaApp.service;


import javax.mail.MessagingException;

public interface ZahtevService {

    void createNewRequest(String zahtevDzs) throws MessagingException;

}
