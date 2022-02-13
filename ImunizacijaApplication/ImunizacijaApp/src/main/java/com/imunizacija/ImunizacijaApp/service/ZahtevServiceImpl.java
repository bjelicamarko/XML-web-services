package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.zahtev_dzs.Zahtev;
import com.imunizacija.ImunizacijaApp.repository.xmlFileReaderWriter.GenericXMLReaderWriter;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@Service
public class ZahtevServiceImpl implements ZahtevService {

    @Autowired
    private GenericXMLRepository<Zahtev> repository;

    @Autowired
    private GenericXMLReaderWriter<Zahtev> repositoryReaderWriter;

    @PostConstruct
    private void postConstruct(){
        this.repository.setRepositoryParams(PACKAGE_PATH_ZAHTEV_DZS, COLLECTION_PATH_ZAHTEV_DZS, new IdGeneratorPosInt());
        this.repositoryReaderWriter.setRepositoryParams(PACKAGE_PATH_ZAHTEV_DZS, XML_SCHEMA_PATH_ZAHTEV);
    }

    @Override
    public void createNewRequest(String zahtevDzs) throws MessagingException {
        Zahtev z = repositoryReaderWriter.checkSchema(zahtevDzs);

        this.repository.storeXML(z, true);
    }
}
