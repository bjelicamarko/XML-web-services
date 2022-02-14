package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import com.imunizacija.ImunizacijaApp.transformers.XML2HTMLTransformer;
import com.imunizacija.ImunizacijaApp.transformers.XSLFOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.io.StringWriter;

import static com.imunizacija.ImunizacijaApp.repository.Constants.COLLECTION_PATH_INTERESOVANJE;
import static com.imunizacija.ImunizacijaApp.repository.Constants.PACKAGE_PATH_INTERESOVANJE;
import static com.imunizacija.ImunizacijaApp.transformers.Constants.*;

@Service
public class InteresovanjeServiceImpl implements InteresovanjeService {

    @Autowired
    private GenericXMLRepository<Interesovanje> repository;

    @Autowired
    private XSLFOTransformer transformerXML2PDF;

    @Autowired
    private XML2HTMLTransformer transformerXML2HTML;

    @PostConstruct // after init
    private void postConstruct(){
        this.repository.setRepositoryParams(PACKAGE_PATH_INTERESOVANJE, COLLECTION_PATH_INTERESOVANJE, new IdGeneratorPosInt());
    }

    @Override
    public Interesovanje findOneById(String id) {
        return repository.retrieveXML(id);
    }

    @Override
    public byte[] generateInteresovanjePDF(String id) throws Exception {
        return transformerXML2PDF.generatePDF(repository.retrieveXMLAsDOMNode(id), INTERESOVANJE_XSL_FO_PATH);
    }

    @Override
    public String generateInteresovanjeHTML(String id) throws Exception {
        StringWriter htmlStringWriter = transformerXML2HTML.generateHTML(repository.retrieveXMLAsDOMNode(id), INTERESOVANJE_XSL_PATH);
        return htmlStringWriter.toString();
    }
}
