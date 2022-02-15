package com.imunizacija.ImunizacijaApp.service;


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
    public void createReport(String dateTo, String dateFrom) {
       System.out.println(interesovanjeRdfRepository.getInteresovanjaBetweenDates(dateTo, dateFrom));
       System.out.println(bazaRdfRepository.getVaccineReports("2022-01-07", "2022-01-12"));
       System.out.println(bazaRdfRepository.getNewVaccineReports("2022-01-07", "2022-01-12"));
       System.out.println(zahtevRdfRepository.getZahteviBetweenDates("2022-01-07", "2022-01-12"));
    }
}
