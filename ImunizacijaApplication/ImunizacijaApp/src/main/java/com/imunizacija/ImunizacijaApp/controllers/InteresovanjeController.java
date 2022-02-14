package com.imunizacija.ImunizacijaApp.controllers;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;
import com.imunizacija.ImunizacijaApp.service.InteresovanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;


@RestController
@RequestMapping("api/interesovanje")
public class InteresovanjeController {

    @Autowired
    private InteresovanjeService interesovanjeService;

    @PreAuthorize("hasRole('CITIZEN')")
    @GetMapping(value = "/{id}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<Interesovanje> findOne(@PathVariable String id) throws NoSuchFieldException {
        Interesovanje interesovanje = interesovanjeService.findOneById(id);
        if(interesovanje == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(interesovanje, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CITIZEN')")
    @PostMapping(value = "/kreirajNovoInteresovanje", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> createNewInterest(@RequestBody String interesovanje) throws MessagingException {
        interesovanjeService.createNewInterest(interesovanje);
        return new ResponseEntity<>("Interesovanje uspesno podneto!", HttpStatus.CREATED);
    }

    
    @GetMapping(value = "/generatePDF/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePDF(@PathVariable String id) {
        try {
            byte[] pdfBytes = interesovanjeService.generateInteresovanjePDF(id);
            return new ResponseEntity<>(pdfBytes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/generateHTML/{id}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<String> generateHTML(@PathVariable String id) {
        try {
            return new ResponseEntity<>(interesovanjeService.generateInteresovanjeHTML(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error HTML transforming.", HttpStatus.NOT_FOUND);
        }
    }

}

