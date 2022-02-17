package com.sluzbenik.SluzbenikApp.controllers;

import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.OdgovorTerminDTO;
import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.SearchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/saglasnost")
public class SaglasnostBridgeController {

    @Autowired
    private RestTemplate restTemplate;

    public static final String HTML = "HTML";
    public static final String PDF = "PDF";

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_XML_VALUE) //TEXT_XML_VALUE | APPLICATION_XML_VALUE
    public ResponseEntity<SearchResults> searchByEmail(@RequestParam String userId, @RequestParam String searchText) {
        String url = makeUrlSearch(userId, searchText);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        HttpEntity<SearchResults> request = new HttpEntity<>(headers);

        ResponseEntity<SearchResults> results = restTemplate.exchange(url,
                HttpMethod.GET, request, SearchResults.class);

        if(results.getStatusCode() != HttpStatus.OK)
            return new ResponseEntity<>(results.getStatusCode());

        return new ResponseEntity<>(results.getBody(), HttpStatus.OK);
    }

    @GetMapping(value = "/generatePDF/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePDF(@PathVariable String id) {
        String url = makeUrlGenerateDocument(PDF, id);
        System.out.println(url);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        HttpEntity<SearchResults> request = new HttpEntity<>(headers);

        ResponseEntity<byte[]> document = restTemplate.exchange(url,
                HttpMethod.GET, request, byte[].class);

        if(document.getStatusCode() != HttpStatus.OK)
            return new ResponseEntity<>(document.getBody(), document.getStatusCode());

        return new ResponseEntity<>(document.getBody(), HttpStatus.OK);
    }

    @GetMapping(value = "/generateHTML/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<String> generateHTML(@PathVariable String id) {
        String url = makeUrlGenerateDocument(HTML, id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        HttpEntity<SearchResults> request = new HttpEntity<>(headers);

        ResponseEntity<String> document = restTemplate.exchange(url,
                HttpMethod.GET, request, String.class);

        if(document.getStatusCode() != HttpStatus.OK)
            return new ResponseEntity<>(document.getBody(), document.getStatusCode());

        return new ResponseEntity<>(document.getBody(), HttpStatus.OK);
    }

    private String makeUrlSearch(String userId, String searchText) {
        String url = String.format("http://localhost:9001/api/saglasnost/search?userId=%s&searchText=%s",
                userId, searchText);
        return url;
    }

    private String makeUrlGenerateDocument(String type, String documentId) {
        String url = String.format("http://localhost:9001/api/saglasnost/generate%s/%s",
                type, documentId);
        return url;
    }

}
