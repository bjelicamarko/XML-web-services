package com.sluzbenik.SluzbenikApp.controllers;

import com.sluzbenik.SluzbenikApp.model.dto.dzs_dto.CreateDzsDTO;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.exception.DzsException;
import com.sluzbenik.SluzbenikApp.service.DZSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.datatype.DatatypeConfigurationException;

@RestController
@RequestMapping("api/dzs")
public class DZSController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DZSService dzsService;

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<DigitalniZeleniSertifikat> findOne(@PathVariable String id) {
        DigitalniZeleniSertifikat dzs = dzsService.findOneById(id);
        if(dzs == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dzs, HttpStatus.OK);
    }

    @GetMapping(value = "/generatePDF/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePDF(@PathVariable String id) {
        try {
            byte[] pdfBytes = dzsService.generateDZSPDF(id);
            return new ResponseEntity<>(pdfBytes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/generateHTML/{id}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<String> generateHTML(@PathVariable String id) {
        try {
            return new ResponseEntity<>(dzsService.generateDZSHTML(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error HTML transforming.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> createDZS(@RequestBody CreateDzsDTO zahtevIdDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:9001/api/potvrda/korisnik/"+zahtevIdDTO.getUserId(),
                String.class);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            dzsService.createDZS(zahtevIdDTO.getZahtevId(), userDetails.getUsername(), entity.getBody(), zahtevIdDTO.getUserEmail());
            return new ResponseEntity<>("Uspesno kreiran digitalni zeleni sertifikat!", HttpStatus.OK);
        } catch (DzsException | DatatypeConfigurationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

}
