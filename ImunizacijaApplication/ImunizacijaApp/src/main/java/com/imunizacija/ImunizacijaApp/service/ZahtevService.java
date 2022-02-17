package com.imunizacija.ImunizacijaApp.service;


import com.google.zxing.WriterException;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.zahtev_dzs.Zahtev;

import javax.mail.MessagingException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public interface ZahtevService {

    void createNewRequest(String zahtevDzs) throws MessagingException;

    Zahtev findOneById(String id);

    byte[] generateZahtevPDF(String id) throws Exception;

    String generateZahtevHTML(String id) throws TransformerException, IOException, WriterException;
}
