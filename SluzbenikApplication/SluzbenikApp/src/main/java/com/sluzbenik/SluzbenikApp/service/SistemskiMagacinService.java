package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.GradVakcineDTO;
import com.sluzbenik.SluzbenikApp.model.dto.termini_dto.GradDTO;
import com.sluzbenik.SluzbenikApp.model.dto.termini_dto.GradVakcinaKolicinaDTO;
import com.sluzbenik.SluzbenikApp.model.dto.termini_dto.VakcinaDTO;

import java.util.List;

public interface SistemskiMagacinService {

    void updateVaccine(GradVakcinaKolicinaDTO gradVakcinaKolicinaDTO);

    List<VakcinaDTO> getVaccineStatusOfCity(String city);

    GradDTO getSelectedCity(GradVakcineDTO gradVakcineDTO);

    void getTermin(GradVakcineDTO gradVakcineDTO);
}
