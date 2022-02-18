package com.imunizacija.ImunizacijaApp.service;

import com.google.zxing.WriterException;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.zahtev_dzs.Zahtev;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.RdfRepository;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.ZahtevExtractMetadata;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.ZahtevRdfRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlFileReaderWriter.GenericXMLReaderWriter;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.ZahteviRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import com.imunizacija.ImunizacijaApp.transformers.Util;
import com.imunizacija.ImunizacijaApp.transformers.XML2HTMLTransformer;
import com.imunizacija.ImunizacijaApp.transformers.XSLFOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.xml.transform.TransformerException;

import java.io.IOException;

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
    private RdfRepository rdfRepository;

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
    public void createNewRequest(String zahtevDzs) {
        Zahtev z = repositoryReaderWriter.checkSchema(zahtevDzs);
        if(z == null)
            throw new RuntimeException("Poslali ste nevalidan dokument.");

        String userId = returnPersonIdentifier(z);
        if(canCreateRequest(userId)) {
            this.repository.storeXML(z, true);
            this.zahtevExtractMetadata.extractData(z);
        } // else throws RuntimeException
    }

    @Override
    public byte[] generateZahtevPDF(String id) throws Exception {
        return transformerXML2PDF.generatePDF(repository.retrieveXMLAsDOMNode(id), ZAHTEV_XSL_FO_PATH, null);
    }

    @Override
    public String generateZahtevHTML(String id) throws TransformerException, IOException, WriterException {
        String htmlString = transformerXML2HTML.generateHTML(repository.retrieveXMLAsDOMNode(id), ZAHTEV_XSL_PATH, null);
        return Util.replaceCharacters(htmlString);
    }

    @Override
    public boolean canCreateRequest(String userId) throws RuntimeException {
        if(!rdfRepository.userHasCertificate(userId))
            throw new RuntimeException("Nije moguce kreirati zahtev jer nemate potvrdu o vakcinaciji.");
        if(rdfRepository.userHasPendingRequest(userId))
            throw new RuntimeException("Već postoji evidentiran zahtev za unete kredencijale.");

        return true;
    }

    @Override
    public String generateZahtevJSON(String id) throws IOException {
        return this.rdfRepository.generateJSON(ZAHTEV_NAMESPACE_PATH, id, ZAHTEV_NAMED_GRAPH_URI);
    }

    @Override
    public String generateZahtevRDFTriplets(String id) {
        return this.rdfRepository.generateRDFTriplets(ZAHTEV_NAMESPACE_PATH, id, ZAHTEV_NAMED_GRAPH_URI);
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

    private String returnPersonIdentifier(Zahtev zahtev) {
        /** Handle exception if all null **/

        String jmbg = zahtev.getPodnosilac().getDrzavljanstvo().getJMBG();
        if(jmbg != null) return jmbg;

        String brojPasosa = zahtev.getPodnosilac().getDrzavljanstvo().getBrPasosa();
        if(brojPasosa != null) return brojPasosa;

        return zahtev.getPodnosilac().getDrzavljanstvo().getEvidencioniBrojStranca();
    }
}
