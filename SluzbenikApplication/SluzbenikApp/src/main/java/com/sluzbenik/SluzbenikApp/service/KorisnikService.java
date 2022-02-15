package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.dto.rdf_dto.DocumentsOfUserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface KorisnikService extends UserDetailsService {

    DocumentsOfUserDTO getDocumentsOfUser(String userID, DocumentsOfUserDTO existingDoc);
}
