package com.sluzbenik.SluzbenikApp.service;


import com.sluzbenik.SluzbenikApp.model.vakc_sistem.korisnik.Korisnik;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class KorisnikServiceImpl implements KorisnikService {

    @Autowired
    KorisnikRepository korisnikRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Korisnik korisnik = korisnikRepository.retrieveXML(username + ".xml");
        if (korisnik == null)
            throw new UsernameNotFoundException("User with id '" + username + "' not found!");
        return korisnik;
    }
}
