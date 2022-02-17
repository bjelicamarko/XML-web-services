package com.imunizacija.ImunizacijaApp.controllers;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.SearchResults;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.imunizacija.ImunizacijaApp.service.PotvrdaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/potvrda")
public class PotvrdaController {

    @Autowired
    private PotvrdaService potvrdaService;

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<PotvrdaOVakcinaciji> findOne(@PathVariable String id){
        PotvrdaOVakcinaciji potvrda = potvrdaService.findOneById(id);
        if(potvrda == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(potvrda, HttpStatus.OK);
    }

    @GetMapping(value = "/generatePDF/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePDF(@PathVariable String id) {
        try {
            byte[] pdfBytes = potvrdaService.generatePotvrdaPDF(id);
            return new ResponseEntity<>(pdfBytes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/generateHTML/{id}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<String> generateHTML(@PathVariable String id) {
        try {
            return new ResponseEntity<>(potvrdaService.generatePotvrdaHTML(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error HTML transforming.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<SearchResults> search(@RequestParam String userId, @RequestParam String searchText) {
        try {
            SearchResults results = potvrdaService.searchDocuments(userId, searchText);
            if(results == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
