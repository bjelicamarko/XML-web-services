package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.SearchResults;

import java.util.List;

public interface MetadataService {
    SearchResults getDocIdsFromQuery(String query);
}
