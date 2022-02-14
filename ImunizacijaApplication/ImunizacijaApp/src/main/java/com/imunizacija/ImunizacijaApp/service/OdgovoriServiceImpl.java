package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.OdgovorTerminDTO;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.OdgovoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OdgovoriServiceImpl implements OdgovoriService{

    @Autowired
    private OdgovoriRepository odgovoriRepository;

    @Override
    public void dodajOdgovor(OdgovorTerminDTO odgovor) {
        this.odgovoriRepository.dodajOdgovor(odgovor);
    }
}
