package com.sluzbenik.SluzbenikApp.service;


import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.SearchResults;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.sluzbenik.SluzbenikApp.repository.xmlFileReaderWriter.GenericXMLReaderWriter;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.GenericXMLRepository;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import com.sluzbenik.SluzbenikApp.transformers.XML2HTMLTransformer;
import com.sluzbenik.SluzbenikApp.transformers.XSLFOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import javax.annotation.PostConstruct;

import static com.sluzbenik.SluzbenikApp.repository.Constants.*;
import static com.sluzbenik.SluzbenikApp.transformers.Constants.*;

@Service
public class DZSServiceImpl implements DZSService {

    @Autowired
    private GenericXMLRepository<DigitalniZeleniSertifikat> repository;

    @Autowired
    private GenericXMLReaderWriter<DigitalniZeleniSertifikat> repositoryReaderWriter;

    @Autowired
    private XSLFOTransformer transformerXML2PDF;

    @Autowired
    private XML2HTMLTransformer transformerXML2HTML;

    public static final String URL_RESOURCE_ROOT = "dzs/";

    private static final String ID_PATH = "Broj_sertifikata";

    @PostConstruct
    private void postConstruct(){
        this.repository.setRepositoryParams(PACKAGE_PATH_DZS, COLLECTION_PATH_DZS, new IdGeneratorPosInt(), DZS_NAMESPACE_PATH2);
        this.repositoryReaderWriter.setRepositoryParams(PACKAGE_PATH_DZS, XML_SCHEMA_PATH_DZS);
    }

    @Override
    public DigitalniZeleniSertifikat findOneById(String id) {
        return repository.retrieveXML(id);
    }

    @Override
    public byte[] generateDZSPDF(String id) throws Exception {
        String resourceUrl = URL_ROOT + URL_RESOURCE_ROOT + id;
        return transformerXML2PDF.generatePDF(repository.retrieveXMLAsDOMNode(id), DZS_XSL_FO_PATH, resourceUrl);
    }

    @Override
    public String generateDZSHTML(String id) throws Exception {
        String resourceUrl = URL_ROOT + URL_RESOURCE_ROOT + id;
        String htmlString = transformerXML2HTML.generateHTML(repository.retrieveXMLAsDOMNode(id), DZS_XSL_PATH, resourceUrl);
        return htmlString;
    }

    @Override
    public SearchResults searchDocuments(String userId, String searchText) throws XMLDBException {
        SearchResults searchResults;
        searchResults = repository.searchDocuments(userId, searchText, ID_PATH);
        return searchResults;
    }
}
