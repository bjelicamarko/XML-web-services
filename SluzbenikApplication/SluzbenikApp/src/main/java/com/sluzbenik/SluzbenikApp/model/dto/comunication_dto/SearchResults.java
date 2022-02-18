package com.sluzbenik.SluzbenikApp.model.dto.comunication_dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Search_results")
public class SearchResults {

    @XmlElement(name = "Search_result", required = true)
    private List<SearchResult> searchResults;

    public SearchResults() {
        this.searchResults = new ArrayList<>();
    }

    public SearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }
}
