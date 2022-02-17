package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.SearchResults;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import org.xmldb.api.base.XMLDBException;

public interface DZSService {

    DigitalniZeleniSertifikat findOneById(String id);

    byte[] generateDZSPDF(String id) throws Exception;

    String generateDZSHTML(String id) throws Exception;

    SearchResults searchDocuments(String userId, String searchText) throws XMLDBException;
}
