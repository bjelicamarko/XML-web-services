package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.RdfRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import com.imunizacija.ImunizacijaApp.transformers.XML2HTMLTransformer;
import com.imunizacija.ImunizacijaApp.transformers.XSLFOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.io.StringWriter;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;
import static com.imunizacija.ImunizacijaApp.transformers.Constants.*;

@Service
public class PotvrdaServiceImpl implements PotvrdaService {

    @Autowired
    private GenericXMLRepository<PotvrdaOVakcinaciji> repository;

    @Autowired
    private RdfRepository rdfRepository;

    @Autowired
    private XSLFOTransformer transformerXML2PDF;

    @Autowired
    private XML2HTMLTransformer transformerXML2HTML;

    public static final String URL_RESOURCE_ROOT = "potvrda/";

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

    @Override
    public byte[] generateInteresovanjePDF(String id) throws Exception {
        String resourceUrl = URL_ROOT + URL_RESOURCE_ROOT + id;
        return transformerXML2PDF.generatePDF(repository.retrieveXMLAsDOMNode(id), POTVRDA_XSL_FO_PATH, resourceUrl);
    }

    @Override
    public String generateInteresovanjeHTML(String id) throws Exception {
        String resourceUrl = URL_ROOT + URL_RESOURCE_ROOT + id;
        String htmlString = transformerXML2HTML.generateHTML(repository.retrieveXMLAsDOMNode(id), POTVRDA_XSL_PATH, resourceUrl);
        return htmlString;
    }
}
