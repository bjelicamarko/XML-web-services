package com.imunizacija.ImunizacijaApp.controllers;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju.Saglasnost;
import com.imunizacija.ImunizacijaApp.service.SaglasnostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("api/saglasnost")
public class SaglasnostController {

    @Autowired
    private SaglasnostService saglasnostService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/kreirajNovuSaglasnost", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> createNewConsent(@RequestBody Saglasnost saglasnost) {
        saglasnostService.createNewConsent(saglasnost);
        return new ResponseEntity<>("Interesovanje uspesno podneta!", HttpStatus.CREATED);
    }
}
