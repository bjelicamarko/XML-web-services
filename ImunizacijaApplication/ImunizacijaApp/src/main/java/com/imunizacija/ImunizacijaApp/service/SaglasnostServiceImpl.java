package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju.Saglasnost;
import com.imunizacija.ImunizacijaApp.repository.xmlFileReaderWriter.GenericXMLReaderWriter;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@Service
public class SaglasnostServiceImpl implements SaglasnostService{

    @Autowired
    private GenericXMLRepository<Saglasnost> repository;

    @Autowired
    private GenericXMLReaderWriter<Saglasnost> repositoryReaderWriter;

    @PostConstruct // after init
    private void postConstruct(){
        this.repository.setRepositoryParams(PACKAGE_PATH_SAGLASNOST, COLLECTION_PATH_SAGLASNOST, new IdGeneratorPosInt());
        this.repositoryReaderWriter.setRepositoryParams(PACKAGE_PATH_SAGLASNOST, XML_SCHEMA_PATH_SAGLASNOST);
    }

    @Override
    public void createNewConsent(String saglasnost) {
        Saglasnost s = this.repositoryReaderWriter.checkSchema(saglasnost);
        this.repository.storeXML(s, true);
    }
}
