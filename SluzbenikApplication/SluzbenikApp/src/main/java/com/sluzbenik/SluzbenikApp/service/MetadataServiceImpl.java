package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.repository.rdfRepository.AdvancedSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetadataServiceImpl implements MetadataService {

    @Autowired
    AdvancedSearchRepository advancedSearchRepository;

    @Override
    public List<String> getDocIdsFromQuery(String query) {
        return advancedSearchRepository.advancedSearch(query);
    }
}
