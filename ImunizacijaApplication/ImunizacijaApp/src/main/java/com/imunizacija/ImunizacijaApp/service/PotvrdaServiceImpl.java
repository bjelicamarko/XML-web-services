package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.RdfRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@Service
public class PotvrdaServiceImpl implements PotvrdaService {

    @Autowired
    private GenericXMLRepository<PotvrdaOVakcinaciji> repository;

    @Autowired
    private RdfRepository rdfRepository;

    @PostConstruct // after init
    private void postConstruct(){
        this.repository.setRepositoryParams(PACKAGE_PATH_POTVRDA, COLLECTION_PATH_POTVRDA, new IdGeneratorPosInt());
    }

    @Override
    public PotvrdaOVakcinaciji findOneById(String id) {
        return repository.retrieveXML(id);
    }

    @Override
    public PotvrdaOVakcinaciji findLastOneByUser(String userID) {
        String potvrdaId = rdfRepository.getPosljednjaPotvrdaIzBazeId(userID);
        if(potvrdaId.equals("-1"))
            return null;
        return repository.retrieveXML(potvrdaId+".xml");
    }
}
