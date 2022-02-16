package com.sluzbenik.SluzbenikApp.service;


import com.sluzbenik.SluzbenikApp.model.dto.rdf_dto.DocumentsOfUserDTO;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.korisnik.Korisnik;
import com.sluzbenik.SluzbenikApp.repository.rdfRepository.RdfRepository;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KorisnikServiceImpl implements KorisnikService {

    @Autowired
    KorisnikRepository korisnikRepository;

    @Autowired
    RdfRepository rdfRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Korisnik korisnik = korisnikRepository.retrieveXML(username + ".xml");
        if (korisnik == null)
            throw new UsernameNotFoundException("User with id '" + username + "' not found!");
        return korisnik;
    }

    @Override
    public DocumentsOfUserDTO getDocumentsOfUser(String userID, DocumentsOfUserDTO existingDoc) {
        List<String> listOfDzs = new ArrayList<>();
        List<String> listOfAcceptedRequests = new ArrayList<>();
        List<String> listOfPendingRequests = new ArrayList<>();

        List<String[]> dzsList = rdfRepository.getDZSFromUser(userID);

        dzsList.forEach(dzs -> {listOfAcceptedRequests.add(dzs[1]); listOfDzs.add(dzs[0]);});

        for (String req : existingDoc.getZahtevDZSList()){
            boolean isAccepted = false;
            for (String acceptedReq : listOfAcceptedRequests){
                if (req.equals(acceptedReq)){
                    isAccepted = true;
                    break;
                }
            }
            if (!isAccepted)
                listOfPendingRequests.add(req);
        }

        existingDoc.setPrihvaceniZahtevDZSList(listOfAcceptedRequests);
        existingDoc.setZahtevDZSList(listOfPendingRequests);
        existingDoc.setDzsList(listOfDzs);
        return existingDoc;
    }
}
