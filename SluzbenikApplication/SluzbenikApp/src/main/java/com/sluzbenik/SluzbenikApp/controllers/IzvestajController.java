package com.sluzbenik.SluzbenikApp.controllers;

import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.IzvestajDTO;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.izvestaj.Izvestaj;
import com.sluzbenik.SluzbenikApp.service.IzvestajService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/izvestaji")
public class IzvestajController {

    @Autowired
    private IzvestajService izvestajService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<Izvestaj> findOne(@PathVariable String id) {
        Izvestaj izvestaj = izvestajService.findOneById(id);
        if(izvestaj == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(izvestaj, HttpStatus.OK);
    }

    @GetMapping(value = "/generatePDF/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePDF(@PathVariable String id) {
        try {
            byte[] pdfBytes = izvestajService.generateIzvestajPDF(id);
            return new ResponseEntity<>(pdfBytes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/generateHTML/{id}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<String> generateHTML(@PathVariable String id) {
        try {
            return new ResponseEntity<>(izvestajService.generateIzvestajHTML(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error HTML transforming.", HttpStatus.NOT_FOUND);
        }
    }

    //server.port = 9000
    @GetMapping(value = "/dobaviIzvestaje/{dateFrom}&{dateTo}", produces = MediaType.APPLICATION_XML_VALUE )
    public ResponseEntity<IzvestajDTO> dobaviIzvestaje(@PathVariable String dateFrom, @PathVariable String dateTo) {
        ResponseEntity<IzvestajDTO> entity = restTemplate.getForEntity(
                String.format("http://localhost:9001/api/izvestaji/dobaviIzvestaje/%s&%s", dateFrom, dateTo),
                IzvestajDTO.class);
        System.out.println(this.izvestajService.createReport(entity.getBody(), dateFrom, dateTo));
        return new ResponseEntity<>(entity.getBody(), HttpStatus.OK);
    }
}
