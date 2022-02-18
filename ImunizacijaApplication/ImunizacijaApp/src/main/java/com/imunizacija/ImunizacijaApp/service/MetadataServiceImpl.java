package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.SearchResult;
import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.SearchResults;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MetadataServiceImpl implements MetadataService{

    @Autowired
    AdvancedSearchRepository advancedSearchRepository;

    @Autowired
    InteresovanjeRdfRepository interesovanjeRdfRepository;

    @Autowired
    SaglasnostRdfRepository saglasnostRdfRepository;

    @Autowired
    ZahtevRdfRepository zahtevRdfRepository;

    @Autowired
    PotvrdaRdfRepository potvrdaRdfRepository;

    @Override
    public SearchResults getDocIdsFromQuery(String query) {
        String[] tokens = query.split("~");
        if (tokens.length != 2){
            throw new RuntimeException("Nevalidan upit! Nema tacno 2 tokena nakon deljenja po ~!");
        }
        List<String> res = new ArrayList<>();
        if (tokens[0].equalsIgnoreCase("interesovanje")){
            res = advancedSearchRepository.advancedSearch(tokens[1], AdvancedSearchRepository.DocType.INTERESOVANJE);
        }else if (tokens[0].equalsIgnoreCase("saglasnost")){
            res = advancedSearchRepository.advancedSearch(tokens[1], AdvancedSearchRepository.DocType.SAGLASNOST);
        }else if (tokens[0].equalsIgnoreCase("zahtev")){
            res = advancedSearchRepository.advancedSearch(tokens[1], AdvancedSearchRepository.DocType.ZAHTEV);
        }else if (tokens[0].equalsIgnoreCase("potvrda")){
            res = advancedSearchRepository.advancedSearch(tokens[1], AdvancedSearchRepository.DocType.POTVRDA);
        }else{
            throw new RuntimeException("Nevalidan tip dokumenta!");
        }

        SearchResults searchResults = new SearchResults();
        for (String result : res){
            searchResults.getSearchResults().add(new SearchResult(result));
        }

        if (tokens[0].equalsIgnoreCase("saglasnost")){
            saglasnostRdfRepository.getSaglasnostWithReferences(searchResults);
        }else if (tokens[0].equalsIgnoreCase("potvrda")){
            potvrdaRdfRepository.getPotvrdaWithReferences(searchResults);
        }

        return searchResults;
    }
}
