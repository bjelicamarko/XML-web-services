package com.imunizacija.ImunizacijaApp.model.dto.comunication_dto;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Search_results")
public class SearchResults {

    @XmlElement(name = "Document_id", required = true)
    private List<String> searchResults;

    public SearchResults() {
        this.searchResults = new ArrayList<>();
    }

    public SearchResults(List<String> searchResults) {
        this.searchResults = searchResults;
    }

    public List<String> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<String> searchResults) {
        this.searchResults = searchResults;
    }
}
