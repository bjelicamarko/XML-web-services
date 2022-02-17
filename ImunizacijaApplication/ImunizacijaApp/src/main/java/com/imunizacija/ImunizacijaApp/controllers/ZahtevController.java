package com.imunizacija.ImunizacijaApp.controllers;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.zahtev_dzs.Zahtev;
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

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<Zahtev> findOne(@PathVariable String id){
        Zahtev zahtev = zahtevService.findOneById(id);
        if(zahtev == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(zahtev, HttpStatus.OK);
    }

    @PutMapping(value = "/prihvati/{id}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<String> acceptRequest(@PathVariable String id){
        try {
            zahtevService.acceptRequest(id);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Odobreno izdavanje digitalnog zelenog sertifikata!", HttpStatus.OK);
    }

    @DeleteMapping(value = "/odbij/{id}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<String> rejectRequest(@PathVariable String id){
        try {
            zahtevService.rejectRequest(id);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Zahtev za DZS odbijen!", HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/kreirajNovZahtev", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> createNewInterest(@RequestBody String zahtev) throws MessagingException {
        zahtevService.createNewRequest(zahtev);
        return new ResponseEntity<>("Zahtev za izdavanje digitalnog zelenog sertifikata uspesno podnet!", HttpStatus.CREATED);
    }

    @GetMapping(value = "/generatePDF/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePDF(@PathVariable String id) {
        try {
            byte[] pdfBytes = zahtevService.generateZahtevPDF(id);
            return new ResponseEntity<>(pdfBytes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/generateHTML/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> generateHTML(@PathVariable String id) {
        try {
            return new ResponseEntity<>(zahtevService.generateZahtevHTML(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error HTML transforming.", HttpStatus.NOT_FOUND);
        }
    }
}
