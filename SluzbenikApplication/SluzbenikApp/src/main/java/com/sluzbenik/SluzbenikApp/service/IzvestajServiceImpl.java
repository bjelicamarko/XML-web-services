package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.izvestaj.Izvestaj;
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
public class IzvestajServiceImpl implements IzvestajService {

    @Autowired
    private GenericXMLRepository<Izvestaj> repository;

    @Autowired
    private GenericXMLReaderWriter<Izvestaj> repositoryReaderWriter;

    @Autowired
    private XSLFOTransformer transformerXML2PDF;

    @Autowired
    private XML2HTMLTransformer transformerXML2HTML;

    @PostConstruct
    private void postConstruct(){
        this.repository.setRepositoryParams(PACKAGE_PATH_IZVESTAJ, COLLECTION_PATH_IZVESTAJ, new IdGeneratorPosInt());
        this.repositoryReaderWriter.setRepositoryParams(PACKAGE_PATH_IZVESTAJ, XML_SCHEMA_PATH_IZVESTAJ);
    }

    @Override
    public Izvestaj findOneById(String id) {
        return repository.retrieveXML(id);
    }

    @Override
    public byte[] generateIzvestajPDF(String id) throws Exception {
        return transformerXML2PDF.generatePDF(repository.retrieveXMLAsDOMNode(id), IZVESTAJ_XSL_FO_PATH, null);
    }

    @Override
    public String generateIzvestajHTML(String id) throws Exception {
        String htmlString = transformerXML2HTML.generateHTML(repository.retrieveXMLAsDOMNode(id), IZVESTAJ_XSL_PATH, null);
        return htmlString;
    }

}
