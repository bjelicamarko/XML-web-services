package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.app_users.RegistrationDTO;
import com.imunizacija.ImunizacijaApp.model.app_users.UserException;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.korisnik.Korisnik;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface KorisnikService extends UserDetailsService {
    
    Korisnik registerUser(RegistrationDTO registrationDTO) throws UserException;

    Korisnik findOneById(String id);
}
