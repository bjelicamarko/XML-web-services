package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.SearchResults;

import java.util.List;

public interface MetadataService {
    SearchResults getDocIdsFromQuery(String query);
}
