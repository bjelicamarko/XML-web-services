package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.repository.rdfRepository.AdvancedSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetadataServiceImpl implements MetadataService{

    @Autowired
    AdvancedSearchRepository advancedSearchRepository;

    @Override
    public List<String> getDocIdsFromQuery(String query) {
        String[] tokens = query.split("~");
        if (tokens.length != 2){
            throw new RuntimeException("Nevalidan upit! Nema tacno 2 tokena nakon deljenja po ~!");
        }
        if (tokens[0].equalsIgnoreCase("interesovanje")){
            return advancedSearchRepository.advancedSearch(tokens[1], AdvancedSearchRepository.DocType.INTERESOVANJE);
        }else if (tokens[0].equalsIgnoreCase("saglasnost")){
            return advancedSearchRepository.advancedSearch(tokens[1], AdvancedSearchRepository.DocType.SAGLASNOST);
        }else if (tokens[0].equalsIgnoreCase("zahtev")){
            return advancedSearchRepository.advancedSearch(tokens[1], AdvancedSearchRepository.DocType.ZAHTEV);
        }else if (tokens[0].equalsIgnoreCase("potvrda")){
            return advancedSearchRepository.advancedSearch(tokens[1], AdvancedSearchRepository.DocType.POTVRDA);
        }else{
            throw new RuntimeException("Nevalidan tip dokumenta!");
        }
    }
}
