package com.sluzbenik.SluzbenikApp.controllers;


import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.AdvancedSearchResults;
import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.SearchResults;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.korisnik.Korisnik;
import com.sluzbenik.SluzbenikApp.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("api/metapodaci")
public class MetapodaciController {

    @Autowired
    MetadataService metadataService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/pretraga/{query}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<SearchResults> advancedSearch(@PathVariable String query) {
        try{
            String[] tokens = query.split("~");
            if (tokens.length != 2){
                throw new RuntimeException("Nevalidan upit! Nema tacno 2 tokena nakon deljenja po ~!");
            }

            SearchResults res = null;
            if (tokens[0].equals("dzs")){
                res = metadataService.getDocIdsFromQuery(tokens[1]);
            }else{
                ResponseEntity<SearchResults> entity = restTemplate.getForEntity("http://localhost:9001/api/metapodaci/pretraga/"+ query,
                        SearchResults.class);
                if (entity.getStatusCode().is2xxSuccessful()){
                    res = entity.getBody();
                }else{
                    throw new RuntimeException("Error while getting from other service");
                }
            }
            return new ResponseEntity<>(res, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
