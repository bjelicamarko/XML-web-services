package com.sluzbenik.SluzbenikApp.controllers;

import com.sluzbenik.SluzbenikApp.dto.GradVakcinaKolicinaDTO;
import com.sluzbenik.SluzbenikApp.service.SistemskiMagacinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/sistemski-magacin")
public class SistemskiMagacinController {

    @Autowired
    private SistemskiMagacinService sistemskiMagacinService;

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/azurirajVakcinu", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> updateVaccine(@RequestBody GradVakcinaKolicinaDTO gradVakcinaKolicinaDTO) {
        sistemskiMagacinService.updateVaccine(gradVakcinaKolicinaDTO);
        return new ResponseEntity<>("Vakcina uspesno azurirana!", HttpStatus.OK);
    }
}
