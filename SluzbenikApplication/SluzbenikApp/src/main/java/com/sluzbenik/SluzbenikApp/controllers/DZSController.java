package com.sluzbenik.SluzbenikApp.controllers;

import com.sluzbenik.SluzbenikApp.model.dto.dzs_dto.CreateDzsDTO;
import com.sluzbenik.SluzbenikApp.model.dto.rdf_dto.DocumentsOfUserDTO;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.exception.DzsException;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.sluzbenik.SluzbenikApp.service.DZSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.xml.datatype.DatatypeConfigurationException;

@RestController
@RequestMapping("api/digitalni-zeleni-sertifikat")
public class DZSController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DZSService dzsService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> createDZS(@RequestBody CreateDzsDTO zahtevIdDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:9001/api/potvrda/korisnik/"+zahtevIdDTO.getUserId(),
                String.class);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            dzsService.createDZS(zahtevIdDTO.getZahtevId(), userDetails.getUsername(), entity.getBody());
            return new ResponseEntity<>("Uspesno kreiran digitalni zeleni sertifikat!", HttpStatus.OK);
        } catch (DzsException | DatatypeConfigurationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}
