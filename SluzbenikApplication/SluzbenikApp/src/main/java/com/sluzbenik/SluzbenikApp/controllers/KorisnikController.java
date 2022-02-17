package com.sluzbenik.SluzbenikApp.controllers;

import com.sluzbenik.SluzbenikApp.model.dto.rdf_dto.DocumentsOfUserDTO;
import com.sluzbenik.SluzbenikApp.model.dto.user_dto.KorisniciListDTO;
import com.sluzbenik.SluzbenikApp.model.dto.user_dto.KorisnikBasicInfoDTO;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.korisnik.Korisnik;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.KorisnikRepository;
import com.sluzbenik.SluzbenikApp.security.TokenUtils;
import com.sluzbenik.SluzbenikApp.security.UserTokenState;
import com.sluzbenik.SluzbenikApp.security.auth.JwtAuthenticationRequest;
import com.sluzbenik.SluzbenikApp.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/users")
public class KorisnikController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;



    //todo izbrisati ova dva dole kasnije
    //sluze samo kao endpoint za dodavanje nekih basic korisnika, jos nisam napravio registraciju

    @Autowired
    KorisnikRepository korisnikRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/test-create-sluzbenik")
    public String createUserSluzbenik(){
        //endpoint za kreiranje basic sluzbenika
        Korisnik korisnik = new Korisnik();
        korisnik.setKorisnikID("12345");
        korisnik.setIme("Djura");
        korisnik.setPrezime("Peric");
        korisnik.setTipKorisnika("MEDICAL_OFFICIAL");
        korisnik.setEmail("djura123@gmail.com");
        korisnik.setLozinka(passwordEncoder.encode("djura123"));

        korisnikRepository.insertUser(korisnik);

        return "Gucci citizen";
    }

    @GetMapping(value = "/dokumentacija/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<DocumentsOfUserDTO> getDocumentationOfUser(@PathVariable String id){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<DocumentsOfUserDTO> entity = restTemplate.getForEntity("http://localhost:9001/api/users/dokumentacija/"+id,
                DocumentsOfUserDTO.class);

       DocumentsOfUserDTO documentsOfUserDTO = korisnikService.getDocumentsOfUser(id, entity.getBody());
        return ResponseEntity.ok(documentsOfUserDTO);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<KorisnikBasicInfoDTO> getOne(@PathVariable String id){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<Korisnik> entity = restTemplate.getForEntity("http://localhost:9001/api/users/"+id + ".xml",
                Korisnik.class);

        if (entity.getBody() == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else {
            return ResponseEntity.ok(new KorisnikBasicInfoDTO(entity.getBody()));
        }
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<KorisniciListDTO> getAll(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<KorisniciListDTO> entity = restTemplate.getForEntity("http://localhost:9001/api/users/",
                KorisniciListDTO.class);

        return ResponseEntity.ok(entity.getBody());
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                                    HttpServletResponse response) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));

        // Ubaci korisnika u trenutni security kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        Korisnik user = (Korisnik) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getKorisnikID(), user.getTipKorisnika(), user.getEmail());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

}
