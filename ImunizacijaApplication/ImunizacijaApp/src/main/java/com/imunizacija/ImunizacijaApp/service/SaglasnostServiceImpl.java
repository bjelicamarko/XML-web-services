package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju.Saglasnost;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@Service
public class SaglasnostServiceImpl implements SaglasnostService{

    @Autowired
    private GenericXMLRepository<Saglasnost> repository;

    @PostConstruct // after init
    private void postConstruct(){
        this.repository.setRepositoryParams(PACKAGE_PATH_SAGLASNOST, COLLECTION_PATH_SAGLASNOST, new IdGeneratorPosInt());
    }

    @Override
    public void createNewConsent(Saglasnost saglasnost) {
        this.repository.storeXML(saglasnost, true);
    }
}
