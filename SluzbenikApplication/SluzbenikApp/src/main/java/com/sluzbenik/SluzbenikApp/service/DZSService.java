package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.SearchResults;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.exception.DzsException;

import javax.xml.datatype.DatatypeConfigurationException;
import org.xmldb.api.base.XMLDBException;

import java.util.List;

public interface DZSService {

    DigitalniZeleniSertifikat findOneById(String id);

    byte[] generateDZSPDF(String id) throws Exception;

    String generateDZSHTML(String id) throws Exception;

    void createDZS(String zahtevID, String idSluzbenika, String potvrdaXML, String userEmail) throws DzsException, DatatypeConfigurationException;

    SearchResults searchDocuments(String userId, String searchText) throws XMLDBException;

    List<String> getDzsOfUser(String id);
}
