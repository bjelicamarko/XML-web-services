package com.imunizacija.ImunizacijaApp.service;


import com.google.zxing.WriterException;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.zahtev_dzs.Zahtev;

import javax.mail.MessagingException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public interface ZahtevService {

    void createNewRequest(String zahtevDzs) throws MessagingException;

    Zahtev findOneById(String id);

    byte[] generateInteresovanjePDF(String id) throws Exception;

    String generateInteresovanjeHTML(String id) throws TransformerException, IOException, WriterException;

    void acceptRequest(String id);

    void rejectRequest(String id);
}
