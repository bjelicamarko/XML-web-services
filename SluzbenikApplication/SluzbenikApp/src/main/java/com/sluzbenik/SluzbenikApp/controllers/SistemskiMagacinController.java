package com.sluzbenik.SluzbenikApp.controllers;

import com.sluzbenik.SluzbenikApp.dto.GradVakcinaKolicinaDTO;
import com.sluzbenik.SluzbenikApp.dto.VakcinaDTO;
import com.sluzbenik.SluzbenikApp.dto.VakcineDTO;
import com.sluzbenik.SluzbenikApp.service.SistemskiMagacinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sistemski-magacin")
public class SistemskiMagacinController {

    @Autowired
    private SistemskiMagacinService sistemskiMagacinService;

    @PutMapping(value = "/azurirajVakcinu", consumes = MediaType.APPLICATION_XML_VALUE)
    @PreAuthorize("hasRole('MEDICAL_OFFICIAL')")
    public ResponseEntity<String> updateVaccine(@RequestBody GradVakcinaKolicinaDTO gradVakcinaKolicinaDTO) {
        sistemskiMagacinService.updateVaccine(gradVakcinaKolicinaDTO);
        return new ResponseEntity<>("Vakcina uspesno azurirana!", HttpStatus.OK);
    }


    @GetMapping(value = "/dobaviStanjeVakcinaGrada/{grad}", produces = MediaType.APPLICATION_XML_VALUE)
    @PreAuthorize("hasRole('MEDICAL_OFFICIAL')")
    public ResponseEntity<VakcineDTO> getVaccineStatusOfCity(@PathVariable String grad) {
        List<VakcinaDTO> vakcine = sistemskiMagacinService.getVaccineStatusOfCity(grad);
        VakcineDTO v = new VakcineDTO();
        v.setVakcina(vakcine);
        return new ResponseEntity<>(v, HttpStatus.OK);
    }
}
