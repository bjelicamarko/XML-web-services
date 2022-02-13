package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.dto.GradVakcinaKolicinaDTO;
import com.sluzbenik.SluzbenikApp.dto.VakcinaDTO;

import java.util.List;

public interface SistemskiMagacinService {

    void updateVaccine(GradVakcinaKolicinaDTO gradVakcinaKolicinaDTO);

    List<VakcinaDTO> getVaccineStatusOfCity(String city);
}
