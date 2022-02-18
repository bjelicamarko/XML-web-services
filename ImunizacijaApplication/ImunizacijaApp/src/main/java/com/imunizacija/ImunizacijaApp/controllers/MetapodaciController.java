package com.imunizacija.ImunizacijaApp.controllers;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.AdvancedSearchResults;
import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.SearchResult;
import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.SearchResults;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.korisnik.Korisnik;
import com.imunizacija.ImunizacijaApp.service.MetadataService;
import com.imunizacija.ImunizacijaApp.service.MetadataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/metapodaci")
public class MetapodaciController {

    @Autowired
    MetadataService metadataService;

    @GetMapping(value = "/pretraga/{query}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<SearchResults> advancedSearch(@PathVariable String query) {
        try{
            SearchResults res = metadataService.getDocIdsFromQuery(query);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
