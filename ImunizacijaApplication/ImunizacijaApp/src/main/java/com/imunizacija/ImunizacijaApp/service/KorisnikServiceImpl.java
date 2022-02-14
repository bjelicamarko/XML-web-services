package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.app_users.RegistrationDTO;
import com.imunizacija.ImunizacijaApp.model.app_users.UserException;
import com.imunizacija.ImunizacijaApp.model.app_users.UserUtils;
import com.imunizacija.ImunizacijaApp.model.dto.rdf_dto.DocumentsOfUserDTO;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.korisnik.Korisnik;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.RdfRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.KorisnikRepository;
import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class KorisnikServiceImpl implements KorisnikService{

    @Autowired
    KorisnikRepository korisnikRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

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
    public Korisnik registerUser(RegistrationDTO registrationDTO) throws UserException {
        UserUtils.CheckUserInfo(registrationDTO);
        Korisnik korisnik = new Korisnik();
        korisnik.setKorisnikID(registrationDTO.getUserId());
        korisnik.setTipKorisnika("CITIZEN");
        korisnik.setEmail(registrationDTO.getEmail());
        korisnik.setLozinka(passwordEncoder.encode(registrationDTO.getPassword()));
        korisnik.setIme(registrationDTO.getFirstName());
        korisnik.setPrezime(registrationDTO.getLastName());
        boolean insert = korisnikRepository.insertUser(korisnik);
        if (!insert)
            throw new UserException("Greska prilikom unosa u bazu!");
        return korisnik;
    }

    @Override
    public DocumentsOfUserDTO getDocumentsOfUser(String userID) {
        return rdfRepository.getDocumentsOfUser(userID);
    }
}
