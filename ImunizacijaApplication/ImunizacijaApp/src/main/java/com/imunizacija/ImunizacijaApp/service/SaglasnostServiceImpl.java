package com.imunizacija.ImunizacijaApp.service;



import javax.annotation.PostConstruct;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju.Saglasnost;
import com.imunizacija.ImunizacijaApp.repository.xmlFileReaderWriter.GenericXMLReaderWriter;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import org.exist.collections.triggers.TriggerException;
import org.exist.security.PermissionDeniedException;
import org.exist.util.LockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xmldb.api.base.*;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XUpdateQueryService;

import java.io.IOException;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;
import static org.exist.security.utils.Utils.getOrCreateCollection;

@Service
public class SaglasnostServiceImpl implements SaglasnostService{

    @Autowired
    private GenericXMLRepository<Saglasnost> repository;

    @Autowired
    private GenericXMLReaderWriter<Saglasnost> repositoryReaderWriter;

    @Override
    public Saglasnost findOneById(String id) { return repository.retrieveXML(id); }

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

    @Override
    public void updateConsent(String saglasnost) {
        Saglasnost s = this.repositoryReaderWriter.checkSchema(saglasnost);
        this.repository.storeXML(s, false);
    }
}
