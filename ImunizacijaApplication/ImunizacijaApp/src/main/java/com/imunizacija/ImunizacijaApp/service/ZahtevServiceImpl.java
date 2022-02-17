package com.imunizacija.ImunizacijaApp.service;

import com.google.zxing.WriterException;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.zahtev_dzs.Zahtev;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.ZahtevExtractMetadata;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.ZahtevRdfRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlFileReaderWriter.GenericXMLReaderWriter;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.ZahteviRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import com.imunizacija.ImunizacijaApp.transformers.XML2HTMLTransformer;
import com.imunizacija.ImunizacijaApp.transformers.XSLFOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.xml.transform.TransformerException;

import java.io.IOException;
import java.io.StringWriter;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;
import static com.imunizacija.ImunizacijaApp.transformers.Constants.*;

@Service
public class ZahtevServiceImpl implements ZahtevService {

    @Autowired
    private GenericXMLRepository<Zahtev> repository;

    @Autowired
    private ZahteviRepository zahteviXMLRepository;

    @Autowired
    private GenericXMLReaderWriter<Zahtev> repositoryReaderWriter;

    @Autowired
    private ZahtevExtractMetadata zahtevExtractMetadata;

    @Autowired
    private ZahtevRdfRepository zahtevRdfRepository;

    @Autowired
    private XSLFOTransformer transformerXML2PDF;

    @Autowired
    private XML2HTMLTransformer transformerXML2HTML;

    @PostConstruct
    private void postConstruct(){
        this.repository.setRepositoryParams(PACKAGE_PATH_ZAHTEV_DZS, COLLECTION_PATH_ZAHTEV_DZS, new IdGeneratorPosInt(), ZAHTEV_NAMESPACE_PATH);
        this.repositoryReaderWriter.setRepositoryParams(PACKAGE_PATH_ZAHTEV_DZS, XML_SCHEMA_PATH_ZAHTEV);
    }

    @Override
    public Zahtev findOneById(String id) { return repository.retrieveXML(id); }

    @Override
    public void createNewRequest(String zahtevDzs) throws MessagingException {
        Zahtev z = repositoryReaderWriter.checkSchema(zahtevDzs);
        this.repository.storeXML(z, true);
        this.zahtevExtractMetadata.extractData(z);
    }

    @Override
    public byte[] generateInteresovanjePDF(String id) throws Exception {
        return transformerXML2PDF.generatePDF(repository.retrieveXMLAsDOMNode(id), ZAHTEV_XSL_FO_PATH, null);
    }

    @Override
    public String generateInteresovanjeHTML(String id) throws TransformerException, IOException, WriterException {
        String htmlString = transformerXML2HTML.generateHTML(repository.retrieveXMLAsDOMNode(id), ZAHTEV_XSL_PATH, null);
        return htmlString;
    }

    @Override
    public void acceptRequest(String id) {
        String currentStatus = zahtevRdfRepository.getStatusZahtev(id);
        if (currentStatus == null){
            throw new RuntimeException("Ne postoji zahtev sa datim ID!");
        }else if (!currentStatus.equals("pending")){
            throw new RuntimeException("Zahtev sa datim ID nije u statusu poslato!");
        }
        zahtevRdfRepository.setStatusZahtev(id, "accepted", currentStatus);
    }

    @Override
    public void rejectRequest(String id) {
        String currentStatus = zahtevRdfRepository.getStatusZahtev(id);
        if (currentStatus == null){
            throw new RuntimeException("Ne postoji zahtev sa datim ID!");
        }else if (!currentStatus.equals("pending")){
            throw new RuntimeException("Zahtev sa datim ID nije u statusu poslato!");
        }
        zahteviXMLRepository.deleteZahtev(id);
        zahtevRdfRepository.deleteZahtev(id);
    }
}
