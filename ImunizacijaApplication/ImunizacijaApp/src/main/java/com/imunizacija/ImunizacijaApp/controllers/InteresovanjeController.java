package com.imunizacija.ImunizacijaApp.controllers;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;
import com.imunizacija.ImunizacijaApp.repository.xmlFileReaderWriter.XMLSchemaValidationHandler;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.TerminRepository;
import com.imunizacija.ImunizacijaApp.service.InteresovanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.mail.MessagingException;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.StringReader;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@RestController
@RequestMapping("api/interesovanje")
public class InteresovanjeController {

    @Autowired
    private InteresovanjeService interesovanjeService;

    @Autowired
    private TerminRepository terminRepository;

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<Interesovanje> findOne(@PathVariable String id) throws NoSuchFieldException {
        Interesovanje interesovanje = interesovanjeService.findOneById(id);
        if(interesovanje == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(interesovanje, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/kreirajNovoInteresovanje", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> createNewInterest(@RequestBody String interesovanje) throws MessagingException {
        interesovanjeService.createNewInterest(interesovanje);
        return new ResponseEntity<>("Interesovanje uspesno podneto!", HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/test")
    public void test() {
        terminRepository.loadXmlTermini();
    }
}

