package com.imunizacija.ImunizacijaApp.controllers;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.OdgovorTerminDTO;
import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.VakcinaKolicinaDTO;
import com.imunizacija.ImunizacijaApp.service.IzvestajiService;
import com.imunizacija.ImunizacijaApp.service.OdgovoriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/odgovori")
public class OdgovoriController {

    @Autowired
    private OdgovoriService odgovoriService;

    @Autowired
    private IzvestajiService izvestajiService;

    @Autowired
    private RestTemplate restTemplate;

    //server.port = 9001
    @GetMapping(value = "/dobaviTermin")
    public ResponseEntity<String> getTermin() {
        System.out.println("LAAAAA " + this.odgovoriService.vratiOdgovor("marko4@markovic.gmail"));

        return new ResponseEntity<>("bravo", HttpStatus.OK);
    }
}
