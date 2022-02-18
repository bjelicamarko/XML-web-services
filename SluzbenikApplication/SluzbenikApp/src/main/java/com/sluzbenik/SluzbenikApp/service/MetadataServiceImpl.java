package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.SearchResult;
import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.SearchResults;
import com.sluzbenik.SluzbenikApp.repository.rdfRepository.AdvancedSearchRepository;
import com.sluzbenik.SluzbenikApp.repository.rdfRepository.DzsRdfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetadataServiceImpl implements MetadataService {

    @Autowired
    AdvancedSearchRepository advancedSearchRepository;

    @Autowired
    DzsRdfRepository dzsRdfRepository;

    @Override
    public SearchResults getDocIdsFromQuery(String query) {
        List<String> res = advancedSearchRepository.advancedSearch(query);

        SearchResults searchResults = new SearchResults();
        for (String result : res){
            searchResults.getSearchResults().add(new SearchResult(result));
        }

        dzsRdfRepository.getDzsWithReferences(searchResults);
        return searchResults;
    }
}
