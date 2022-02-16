package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.exception.DzsException;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.sluzbenik.SluzbenikApp.repository.xmlFileReaderWriter.GenericXMLReaderWriter;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.GenericXMLRepository;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.id_generator.IdGeneratorDZS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.util.GregorianCalendar;

import static com.sluzbenik.SluzbenikApp.repository.Constants.*;

@Service
public class DZSServiceImpl implements DZSService{

    @Autowired
    GenericXMLRepository<DigitalniZeleniSertifikat> xmlRepositoryDzs;

    @Autowired
    GenericXMLReaderWriter<PotvrdaOVakcinaciji> potvrdaGenericReaderWriter;

    @PostConstruct // after init
    private void postConstruct(){
        this.xmlRepositoryDzs.setRepositoryParams(PACKAGE_PATH_DZS, COLLECTION_PATH_DZS, new IdGeneratorDZS());
        this.potvrdaGenericReaderWriter.setRepositoryParams(PACKAGE_PATH_POTVRDA, XML_SCHEMA_PATH_POTVRDA);
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
