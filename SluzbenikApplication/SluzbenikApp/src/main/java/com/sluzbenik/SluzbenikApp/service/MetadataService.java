package com.sluzbenik.SluzbenikApp.service;

import java.util.List;

public interface MetadataService {
    List<String> getDocIdsFromQuery(String query);
}
