package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.OdgovorTerminDTO;
import com.sluzbenik.SluzbenikApp.model.dto.termini_dto.GradDTO;
import com.sluzbenik.SluzbenikApp.model.dto.termini_dto.GradVakcinaKolicinaDTO;
import com.sluzbenik.SluzbenikApp.model.dto.termini_dto.VakcinaDTO;

import java.util.List;

public interface SistemskiMagacinService {

    void updateVaccine(GradVakcinaKolicinaDTO gradVakcinaKolicinaDTO);

    void addRemoveVaccine(String grad, String nazivProizvodjaca, int vrijednost);

    List<VakcinaDTO> getVaccineStatusOfCity(String city);

    GradDTO getSelectedCity(OdgovorTerminDTO odgovorTerminDTO);

    OdgovorTerminDTO getTermin(OdgovorTerminDTO odgovorTerminDTO);
}
