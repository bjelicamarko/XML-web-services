package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;

public interface DZSService {

    DigitalniZeleniSertifikat findOneById(String id);

    byte[] generateDZSPDF(String id) throws Exception;

    String generateDZSHTML(String id) throws Exception;

    void createDZS(String zahtevID, String idSluzbenika, String potvrdaXML) throws DzsException, DatatypeConfigurationException;

}
