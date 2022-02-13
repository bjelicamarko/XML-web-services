package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.dto.GradVakcinaKolicinaDTO;
import com.sluzbenik.SluzbenikApp.dto.VakcinaDTO;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.SistemskiMagacinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SistemskiMagacinServiceImpl implements SistemskiMagacinService {

    @Autowired
    private SistemskiMagacinRepository sistemskiMagacinRepository;

    @Override
    public void updateVaccine(GradVakcinaKolicinaDTO gradVakcinaKolicinaDTO) {
        this.sistemskiMagacinRepository.updateVaccine(gradVakcinaKolicinaDTO);
    }

    @Override
    public List<VakcinaDTO> getVaccineStatusOfCity(String city) {
        return this.sistemskiMagacinRepository.getVaccineStatusOfCity(city);
    }
}
