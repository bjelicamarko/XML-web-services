package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.exception.DzsException;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.sluzbenik.SluzbenikApp.repository.xmlFileReaderWriter.GenericXMLReaderWriter;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.GenericXMLRepository;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.id_generator.IdGeneratorDZS;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.sluzbenik.SluzbenikApp.repository.xmlFileReaderWriter.GenericXMLReaderWriter;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.GenericXMLRepository;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import com.sluzbenik.SluzbenikApp.transformers.XML2HTMLTransformer;
import com.sluzbenik.SluzbenikApp.transformers.XSLFOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.io.StringWriter;

import static com.sluzbenik.SluzbenikApp.repository.Constants.*;
import static com.sluzbenik.SluzbenikApp.transformers.Constants.*;

@Service
public class DZSServiceImpl implements DZSService {

    @Autowired
    private GenericXMLRepository<DigitalniZeleniSertifikat> repository;

    @Autowired
    private GenericXMLReaderWriter<DigitalniZeleniSertifikat> repositoryReaderWriter;

    @Autowired
    GenericXMLReaderWriter<PotvrdaOVakcinaciji> potvrdaGenericReaderWriter;

    @Autowired
    private XSLFOTransformer transformerXML2PDF;

    @Autowired
    private XML2HTMLTransformer transformerXML2HTML;

    public static final String URL_RESOURCE_ROOT = "dzs/";

    @PostConstruct
    private void postConstruct(){
        this.repository.setRepositoryParams(PACKAGE_PATH_DZS, COLLECTION_PATH_DZS, new IdGeneratorPosInt());
        this.repositoryReaderWriter.setRepositoryParams(PACKAGE_PATH_DZS, XML_SCHEMA_PATH_DZS);
        this.potvrdaGenericReaderWriter.setRepositoryParams(PACKAGE_PATH_POTVRDA, XML_SCHEMA_PATH_POTVRDA);
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
    public void createDZS(String zahtevID, String idSluzbenika, String potvrdaXML) throws DzsException, DatatypeConfigurationException {
        PotvrdaOVakcinaciji potvrdaOVakcinaciji = potvrdaGenericReaderWriter.checkSchema(potvrdaXML);
        if (potvrdaOVakcinaciji == null)
            throw new DzsException("Nevalidan dokument potvrde dobavljen sa servisa!");

        DigitalniZeleniSertifikat dzs = new DigitalniZeleniSertifikat();
        dzs.setPodaciOPrimaocu(potvrdaOVakcinaciji.getPodaciOPrimaocu());
        dzs.setIdSluzbenika(idSluzbenika);

        DigitalniZeleniSertifikat.PodaciOSertifikatu podaciOSertifikatu = new DigitalniZeleniSertifikat.PodaciOSertifikatu();

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        XMLGregorianCalendar now =
                datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        podaciOSertifikatu.setDatumIzdavanjaSertifikata(now);
        podaciOSertifikatu.setQrKod("qrkodic"); //TODO VIDETI STA OVDE TREBA U QR KOD

        //TODO SLANJE MAIL-a

        dzs.setPodaciOSertifikatu(podaciOSertifikatu);
        dzs.getDoze().addAll(potvrdaOVakcinaciji.getPodaciOVakcini().getDoze());

        xmlRepositoryDzs.storeXML(dzs, true);
    }
}
