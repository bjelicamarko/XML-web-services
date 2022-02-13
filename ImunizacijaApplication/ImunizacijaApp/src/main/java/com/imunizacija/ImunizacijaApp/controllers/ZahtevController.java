package com.imunizacija.ImunizacijaApp.controllers;

import com.imunizacija.ImunizacijaApp.service.ZahtevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("api/zahtev")
public class ZahtevController {

    @Autowired
    private ZahtevService zahtevService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/kreirajNovZahtev", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> createNewInterest(@RequestBody String zahtev) throws MessagingException {
        zahtevService.createNewRequest(zahtev);
        return new ResponseEntity<>("Zahtev za izdavanje digitalnog zelenog sertifikata uspesno podnet!", HttpStatus.CREATED);
    }
}
