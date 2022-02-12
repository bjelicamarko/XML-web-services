package com.imunizacija.ImunizacijaApp.controllers;

import com.imunizacija.ImunizacijaApp.service.SaglasnostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/saglasnost")
public class SaglasnostController {

    @Autowired
    private SaglasnostService saglasnostService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/kreirajNovuSaglasnost", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> createNewConsent(@RequestBody String saglasnost) {
        saglasnostService.createNewConsent(saglasnost);
        return new ResponseEntity<>("Saglasnost uspesno podneta!", HttpStatus.CREATED);
    }
}
