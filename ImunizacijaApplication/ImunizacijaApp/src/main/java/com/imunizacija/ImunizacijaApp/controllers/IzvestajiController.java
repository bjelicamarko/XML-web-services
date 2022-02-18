package com.imunizacija.ImunizacijaApp.controllers;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.IzvestajDTO;
import com.imunizacija.ImunizacijaApp.service.IzvestajiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/izvestaji")
public class IzvestajiController {

    @Autowired
    private IzvestajiService izvestajiService;

    @Autowired
    private RestTemplate restTemplate;

    //server.port = 9001
    @GetMapping(value = "/dobaviIzvestaje/{dateFrom}&{dateTo}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<IzvestajDTO> dobaviIzvestaje(
            @PathVariable String dateFrom, @PathVariable String dateTo) {

        return new ResponseEntity<>(izvestajiService.createReport(dateFrom, dateTo),
                HttpStatus.OK);
    }
}
