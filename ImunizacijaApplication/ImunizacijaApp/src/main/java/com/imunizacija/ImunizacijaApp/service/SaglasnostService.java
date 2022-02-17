package com.imunizacija.ImunizacijaApp.service;

import com.google.zxing.WriterException;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju.Saglasnost;
import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.SearchResults;
import org.xmldb.api.base.XMLDBException;

import javax.xml.transform.TransformerException;
import java.io.IOException;


public interface SaglasnostService {

    Saglasnost findOneById(String id);

    void createNewConsent(String Saglasnost);

    byte[] generateSaglasnostPDF(String id) throws Exception;

    String generateSaglasnostHTML(String id) throws TransformerException, IOException, WriterException;
    
    void updateConsent(String saglasnost);

    SearchResults searchDocuments(String userId, String searchText) throws XMLDBException;
}
