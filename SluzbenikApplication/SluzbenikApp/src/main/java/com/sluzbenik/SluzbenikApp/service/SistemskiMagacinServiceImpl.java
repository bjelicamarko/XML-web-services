package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.dto.GradVakcinaKolicinaDTO;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.SistemskiMagacinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SistemskiMagacinServiceImpl implements SistemskiMagacinService {

    @Autowired
    private SistemskiMagacinRepository sistemskiMagacinRepository;

    @Override
    public void updateVaccine(GradVakcinaKolicinaDTO gradVakcinaKolicinaDTO) {
        this.sistemskiMagacinRepository.updateVaccine(gradVakcinaKolicinaDTO);
    }
}
