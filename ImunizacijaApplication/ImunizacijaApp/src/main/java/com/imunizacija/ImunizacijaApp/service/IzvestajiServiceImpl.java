package com.imunizacija.ImunizacijaApp.service;


import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.IzvestajDTO;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.BazaRdfRepository;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.InteresovanjeRdfRepository;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.ZahtevRdfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IzvestajiServiceImpl implements IzvestajiService{

    @Autowired
    private InteresovanjeRdfRepository interesovanjeRdfRepository;

    @Autowired
    private BazaRdfRepository bazaRdfRepository;

    @Autowired
    private ZahtevRdfRepository zahtevRdfRepository;

    @Override
    public IzvestajDTO createReport(String dateTo, String dateFrom) {
       IzvestajDTO izvestajDTO = new IzvestajDTO();
       izvestajDTO.setBrojInteresovanja(interesovanjeRdfRepository.getInteresovanjaBetweenDates(dateTo, dateFrom));
       izvestajDTO.setBrojZahteva(zahtevRdfRepository.getZahteviBetweenDates(dateTo, dateFrom));
       izvestajDTO.setNoveVakcine(bazaRdfRepository.getNewVaccineReports(dateTo, dateFrom));
       izvestajDTO.setStanje(bazaRdfRepository.getVaccineReports(dateTo, dateFrom));
       return izvestajDTO;
    }
}
