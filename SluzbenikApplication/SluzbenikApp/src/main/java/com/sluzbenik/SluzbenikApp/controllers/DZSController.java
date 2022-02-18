package com.sluzbenik.SluzbenikApp.controllers;

import com.sluzbenik.SluzbenikApp.model.dto.dzs_dto.CreateDzsDTO;
import com.sluzbenik.SluzbenikApp.model.dto.dzs_dto.DzsList;
import com.sluzbenik.SluzbenikApp.model.dto.dzs_dto.RejectRequestDTO;
import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.SearchResults;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.exception.DzsException;
import com.sluzbenik.SluzbenikApp.service.DZSService;
import com.sluzbenik.SluzbenikApp.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.xml.datatype.DatatypeConfigurationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/dzs")
public class DZSController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DZSService dzsService;

    @Autowired
    private MailService mailService;

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<DigitalniZeleniSertifikat> findOne(@PathVariable String id) {
        DigitalniZeleniSertifikat dzs = dzsService.findOneById(id);
        if(dzs == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dzs, HttpStatus.OK);
    }

    @GetMapping(value = "/odKorisnika/{id}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<DzsList> getDzsListOfList(@PathVariable String id) {
        try{
            DzsList dzsList = new DzsList(dzsService.getDzsOfUser(id));
            return new ResponseEntity<>(dzsList, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/generatePDF/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePDF(@PathVariable String id) {
        try {
            byte[] pdfBytes = dzsService.generateDZSPDF(id);
            return new ResponseEntity<>(pdfBytes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/generateHTML/{id}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<String> generateHTML(@PathVariable String id) {
        try {
            return new ResponseEntity<>(dzsService.generateDZSHTML(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error HTML transforming.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> createDZS(@RequestBody CreateDzsDTO zahtevIdDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        //saljemo da promenimo stanje zahteva
        ResponseEntity<String> entity2 = restTemplate.exchange("http://localhost:9001/api/zahtev/prihvati/"+zahtevIdDTO.getZahtevId(),
                HttpMethod.PUT,httpEntity, String.class);

        if (!entity2.getStatusCode().is2xxSuccessful()){
            return new ResponseEntity<>("Nevalidan zahtev, digitalni zeleni sertifikat nije kreiran!", HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:9001/api/potvrda/korisnik/"+zahtevIdDTO.getUserId(),
                String.class);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            dzsService.createDZS(zahtevIdDTO.getZahtevId(), userDetails.getUsername(), entity.getBody(), zahtevIdDTO.getUserEmail());
            return new ResponseEntity<>("Uspesno kreiran digitalni zeleni sertifikat!", HttpStatus.CREATED);
        } catch (DzsException | DatatypeConfigurationException | RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/odbijanje-zahteva", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> rejectRequestForDZS(@RequestBody RejectRequestDTO rejectRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        //saljemo da promenimo stanje zahteva
        ResponseEntity<String> entity = restTemplate.exchange("http://localhost:9001/api/zahtev/odbij/"+rejectRequestDTO.getZahtevId(),
                HttpMethod.DELETE,httpEntity, String.class);

        if (!entity.getStatusCode().is2xxSuccessful()){
            return new ResponseEntity<>("Nevalidan zahtev!", HttpStatus.BAD_REQUEST);
        }

        try {
            mailService.sendRejection("Zahtev za DZS odbijen", rejectRequestDTO.getReason(), rejectRequestDTO.getUserEmail());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Zahtev odbijen!", HttpStatus.OK);
    }
    
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<SearchResults> search(@RequestParam String userId, @RequestParam String searchText) {
        try {
            SearchResults results = dzsService.searchDocuments(userId, searchText);
            if(results == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
