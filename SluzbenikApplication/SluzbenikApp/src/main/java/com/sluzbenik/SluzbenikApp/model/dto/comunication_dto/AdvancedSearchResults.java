package com.sluzbenik.SluzbenikApp.model.dto.comunication_dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MetaSearchResults")
public class AdvancedSearchResults {

    @XmlElement(name = "Document_id", required = true)
    private List<String> searchResults;

    public AdvancedSearchResults() {
        this.searchResults = new ArrayList<>();
    }

    public AdvancedSearchResults(List<String> searchResults) {
        this.searchResults = searchResults;
    }

    public List<String> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<String> searchResults) {
        this.searchResults = searchResults;
    }
}
