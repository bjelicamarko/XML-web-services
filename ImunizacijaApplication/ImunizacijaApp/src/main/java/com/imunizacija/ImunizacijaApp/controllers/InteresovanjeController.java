package com.imunizacija.ImunizacijaApp.controllers;
import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.GradVakcineDTO;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;
import com.imunizacija.ImunizacijaApp.service.InteresovanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;

@RestController
@RequestMapping("api/interesovanje")
public class InteresovanjeController {

    @Autowired
    private InteresovanjeService interesovanjeService;

    @Autowired
    private RestTemplate restTemplate;

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

    //server.port = 9001

    @GetMapping(value = "/dobaviTermin")
    public ResponseEntity<String> getTermin() {
        GradVakcineDTO vakcineDTO = new GradVakcineDTO();
        vakcineDTO.setGrad("Becej");
        vakcineDTO.getVakcine().add("Pfizer");
        vakcineDTO.getVakcine().add("Sputnik V");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        HttpEntity<GradVakcineDTO> requestUpdate = new HttpEntity<>(vakcineDTO, headers);

        ResponseEntity<String> entity = restTemplate.exchange("http://localhost:9000/api/sistemski-magacin/dobaviTermin",
                HttpMethod.POST, requestUpdate, String.class);

        return new ResponseEntity<>(entity.getBody(), HttpStatus.OK);
    }
}

