package com.imunizacija.ImunizacijaApp.controllers;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.odgovori.Odgovori;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju.Saglasnost;
import com.imunizacija.ImunizacijaApp.service.OdgovoriService;
import com.imunizacija.ImunizacijaApp.service.SaglasnostService;
import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.SearchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/saglasnost")
public class SaglasnostController {

    @Autowired
    private SaglasnostService saglasnostService;

    @Autowired
    private OdgovoriService odgovoriService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Saglasnost> findOne(@PathVariable String id) {
        Saglasnost saglasnost = saglasnostService.findOneById(id);
        if(saglasnost == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(saglasnost, HttpStatus.OK);
    }

    @GetMapping(value = "/isConsentExist/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> isConsentExist(@PathVariable String id) {
        try {
            Saglasnost saglasnost = saglasnostService.findOneById(String.format("%s.xml", id));
            if(saglasnost.getOVakcinaciji() != null){
                return new ResponseEntity<>(String.format("Saglasnost (ID: %s) je popunjena.", id), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(String.format("Saglasnost (ID: %s) je spremna za azuriranje.", id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Saglasnost (ID: %s) ne postoji.", id), HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/kreirajNovuSaglasnost", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> createNewConsent(@RequestBody String saglasnost) {
        saglasnostService.createNewConsent(saglasnost);
        return new ResponseEntity<>("Saglasnost uspesno podneta!", HttpStatus.CREATED);
    }

    @GetMapping(value = "/generatePDF/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePDF(@PathVariable String id) {
        try {
            byte[] pdfBytes = saglasnostService.generateSaglasnostPDF(id);
            return new ResponseEntity<>(pdfBytes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/generateHTML/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<String> generateHTML(@PathVariable String id) {
        try {
            return new ResponseEntity<>(saglasnostService.generateSaglasnostHTML(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error HTML transforming.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/generateJSON/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> generateJSON(@PathVariable String id) {
        try {
            return new ResponseEntity<>(saglasnostService.generateSaglasnostJSON(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(String.format("Error getting RDF (DocId: %s) in JSON format .", id), HttpStatus.NOT_FOUND);
        }
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> updateConsent(@RequestBody String saglasnost) {
        saglasnostService.updateConsent(saglasnost);
        return new ResponseEntity<>("Saglasnost uspesno dopunjena!", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CITIZEN')")
    @GetMapping(value = "/provjeraPostojanjaTerminaZaSaglasnost/{email}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> provjeraPostojanjaTerminaZaSaglasnost(@PathVariable String email) {
        Odgovori.Odgovor o = this.odgovoriService.vratiOdgovor(email);
        if (o.getTermin().equals("Empty"))
            return new ResponseEntity<>("Nepostoji termin da bi se podneo saglasnost.", HttpStatus.OK);
        else
            return new ResponseEntity<>("Postoji termin.", HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<SearchResults> search(@RequestParam String userId, @RequestParam String searchText) {
        try {
            SearchResults results = saglasnostService.searchDocuments(userId, searchText);
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
