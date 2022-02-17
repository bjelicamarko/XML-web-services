package com.imunizacija.ImunizacijaApp.service;

import com.google.zxing.WriterException;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.odgovori.Odgovori;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju.Saglasnost;
import com.imunizacija.ImunizacijaApp.repository.xmlFileReaderWriter.GenericXMLReaderWriter;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import com.imunizacija.ImunizacijaApp.transformers.XML2HTMLTransformer;
import com.imunizacija.ImunizacijaApp.transformers.XSLFOTransformer;
import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.SearchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;
import static com.imunizacija.ImunizacijaApp.transformers.Constants.*;

@Service
public class SaglasnostServiceImpl implements SaglasnostService{

    @Autowired
    private GenericXMLRepository<Saglasnost> repository;

    @Autowired
    private GenericXMLReaderWriter<Saglasnost> repositoryReaderWriter;

    @Autowired
    private XSLFOTransformer transformerXML2PDF;

    @Autowired
    private XML2HTMLTransformer transformerXML2HTML;

    @Autowired
    private PotvrdaService potvrdaService;

    @Autowired
    private OdgovoriService odgovoriService;

    @PostConstruct // after init
    private void postConstruct(){
        this.repository.setRepositoryParams(PACKAGE_PATH_SAGLASNOST, COLLECTION_PATH_SAGLASNOST, new IdGeneratorPosInt(), SAGLASNOST_NAMESPACE_PATH);
        this.repositoryReaderWriter.setRepositoryParams(PACKAGE_PATH_SAGLASNOST, XML_SCHEMA_PATH_SAGLASNOST);
    }

    @Override
    public Saglasnost findOneById(String id) { return repository.retrieveXML(id); }

    @Override
    public void createNewConsent(String saglasnost) {
        Saglasnost s = this.repositoryReaderWriter.checkSchema(saglasnost);
        Odgovori.Odgovor o = this.odgovoriService.vratiOdgovor(s.getKontakt().getEmailAdresa());
        s.getIzjava().setImunoloskiLek(o.getDodeljenaVakcina());
        this.repository.storeXML(s, true);
    }

    @Override
    public byte[] generateSaglasnostPDF(String id) throws Exception {
        return transformerXML2PDF.generatePDF(repository.retrieveXMLAsDOMNode(id), SAGLASNOST_XSL_FO_PATH, null);
    }

    @Override
    public String generateSaglasnostHTML(String id) throws TransformerException, IOException, WriterException {
        String htmlString = transformerXML2HTML.generateHTML(repository.retrieveXMLAsDOMNode(id), SAGLASNOST_XSL_PATH, null);
        return htmlString;
    }
    
    @Override
    public void updateConsent(String saglasnost) { // primio vakcinu
        Saglasnost s = this.repositoryReaderWriter.checkSchema(saglasnost);
        this.repository.storeXML(s, false);
        try { this.potvrdaService.generatePotvrdaOVakcinaciji(s); }
        catch (DatatypeConfigurationException | MessagingException e) { System.err.println("Generisanje potvrde nije uspjelo."); }
    }

    @Override
    public SearchResults searchDocuments(String userId, String searchText) throws XMLDBException {
        SearchResults searchResults;
        searchResults = repository.searchDocuments(userId, searchText);
        return searchResults;
    }
}

