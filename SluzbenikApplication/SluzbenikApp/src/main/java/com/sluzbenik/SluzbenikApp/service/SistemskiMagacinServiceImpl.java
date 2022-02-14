package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.GradVakcineDTO;
import com.sluzbenik.SluzbenikApp.model.dto.termini_dto.GradDTO;
import com.sluzbenik.SluzbenikApp.model.dto.termini_dto.GradVakcinaKolicinaDTO;
import com.sluzbenik.SluzbenikApp.model.dto.termini_dto.VakcinaDTO;
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

    @Override
    public GradDTO getSelectedCity(GradVakcineDTO gradVakcineDTO) {
        return this.sistemskiMagacinRepository.getSelectedCity(gradVakcineDTO);
    }

    @Override
    public void getTermin(GradVakcineDTO gradVakcineDTO) {
        GradDTO grad = this.getSelectedCity(gradVakcineDTO);
    }
}
