package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.korisnik.Korisnik;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface KorisnikService extends UserDetailsService {
    Korisnik findOneById(String id);
}
