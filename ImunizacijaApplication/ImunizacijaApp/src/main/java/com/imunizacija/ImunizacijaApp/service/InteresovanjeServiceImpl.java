package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static com.imunizacija.ImunizacijaApp.repository.Constants.COLLECTION_PATH_INTERESOVANJE;
import static com.imunizacija.ImunizacijaApp.repository.Constants.PACKAGE_PATH_INTERESOVANJE;

@Service
public class InteresovanjeServiceImpl implements InteresovanjeService {

    @Autowired
    private GenericXMLRepository<Interesovanje> repository;

    @PostConstruct // after init
    private void postConstruct(){
        this.repository.setRepositoryParams(PACKAGE_PATH_INTERESOVANJE, COLLECTION_PATH_INTERESOVANJE, new IdGeneratorPosInt());
    }

    @Override
    public Interesovanje findOneById(String id) {
        return repository.retrieveXML(id);
    }
}
