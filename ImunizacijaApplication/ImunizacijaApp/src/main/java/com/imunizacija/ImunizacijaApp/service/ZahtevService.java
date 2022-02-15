package com.imunizacija.ImunizacijaApp.service;


import com.imunizacija.ImunizacijaApp.model.vakc_sistem.zahtev_dzs.Zahtev;

import javax.mail.MessagingException;
import javax.xml.transform.TransformerException;

public interface ZahtevService {

    void createNewRequest(String zahtevDzs) throws MessagingException;

    Zahtev findOneById(String id);

    byte[] generateInteresovanjePDF(String id) throws Exception;

    String generateInteresovanjeHTML(String id) throws TransformerException;
}
