package com.sluzbenik.SluzbenikApp.controllers;

import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.SearchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import static com.sluzbenik.SluzbenikApp.utils.BridgeControllerUtil.makeUrlGenerateDocument;
import static com.sluzbenik.SluzbenikApp.utils.BridgeControllerUtil.makeUrlSearch;

@RestController
@RequestMapping("api/potvrda")
public class PotvrdaBridgeController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<SearchResults> searchByEmail(@RequestParam String userId, @RequestParam String searchText) {
        String url = makeUrlSearch(userId, searchText, "potvrda");

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
        String url = makeUrlGenerateDocument("PDF", id, "potvrda");
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

    @GetMapping(value = "/generateHTML/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> generateHTML(@PathVariable String id) {
        String url = makeUrlGenerateDocument("HTML", id, "potvrda");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        HttpEntity<SearchResults> request = new HttpEntity<>(headers);

        ResponseEntity<String> document = restTemplate.exchange(url,
                HttpMethod.GET, request, String.class);

        if(document.getStatusCode() != HttpStatus.OK)
            return new ResponseEntity<>(document.getBody(), document.getStatusCode());

        return new ResponseEntity<>(document.getBody(), HttpStatus.OK);
    }

}
