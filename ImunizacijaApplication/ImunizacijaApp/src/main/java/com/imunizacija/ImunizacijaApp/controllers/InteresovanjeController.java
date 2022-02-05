package com.imunizacija.ImunizacijaApp.controllers;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;
import com.imunizacija.ImunizacijaApp.service.InteresovanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/interesovanje")
public class InteresovanjeController {

    @Autowired
    private InteresovanjeService interesovanjeService;

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<Interesovanje> findOne(@PathVariable String id) throws NoSuchFieldException {
        Interesovanje interesovanje = interesovanjeService.findOneById(id);
        if(interesovanje == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(interesovanje, HttpStatus.OK);
    }
}

