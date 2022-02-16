package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.OdgovorTerminDTO;
import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.VakcinaKolicinaDTO;
import com.sluzbenik.SluzbenikApp.model.dto.termini_dto.*;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.SistemskiMagacinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    public void addRemoveVaccine(String grad, String nazivProizvodjaca, int vrijednost) {
        this.sistemskiMagacinRepository.addRemoveVaccine(grad, nazivProizvodjaca, vrijednost);
    }

    @Override
    public List<VakcinaDTO> getVaccineStatusOfCity(String city) {
        return this.sistemskiMagacinRepository.getVaccineStatusOfCity(city);
    }

    @Override
    public GradDTO getSelectedCity(OdgovorTerminDTO ogovorTerminDTO) {
        return this.sistemskiMagacinRepository.getSelectedCity(ogovorTerminDTO);
    }

    @Override
    public OdgovorTerminDTO getTermin(OdgovorTerminDTO odgovorTerminDTO) {

        GradDTO city = this.getSelectedCity(odgovorTerminDTO);

        // izabrana vakcina
        String chosenVaccine = this.checkVaccineStatus(odgovorTerminDTO.getVakcine(), city.getVakcine());
        if (chosenVaccine.equals("Empty")) {
            odgovorTerminDTO.setRazlog("Nema dovoljno trazene vakcine!");
            return odgovorTerminDTO;
        }

        String termin = "Empty";
        for (UstanovaDTO u : city.getUstanove()) {
            termin = checkDate(u.getTermin(), LocalDateTime.now(), odgovorTerminDTO);
            if (!termin.equals("Empty")) {// nasli termin u prvoj ustanovi slobodnoj
                odgovorTerminDTO.setRazlog("Uspesno pronadjen termin!");
                odgovorTerminDTO.setTermin(termin); // postavljen termin
                odgovorTerminDTO.setUstanova(u.getNaziv()); // postavljen razlog i ustanova
                break;
            }
        }
        if (termin.equals("Empty")) {
            odgovorTerminDTO.setRazlog("Nema slobodnih termina!");
            return odgovorTerminDTO;
        }

        odgovorTerminDTO.setVakcinaDodeljena(chosenVaccine); // postavljena izabrana vakcina

        // azuriranje kolicine u bazi
        for (VakcinaDTO v : city.getVakcine())
            if (v.getNazivProizvodjaca().equals(chosenVaccine))
                this.sistemskiMagacinRepository.addRemoveVaccine(city.getIme(),
                        chosenVaccine, v.getValue()-1);
        // ovdje da ide azuriranje baze
        this.sistemskiMagacinRepository.updateTermin(odgovorTerminDTO);
        return odgovorTerminDTO;
    }

    @Override
    public void returnVaccineToStore(VakcinaKolicinaDTO vakcinaKolicinaDTO) {
        this.sistemskiMagacinRepository.returnVaccineToStore(vakcinaKolicinaDTO);
    }

    private String checkVaccineStatus(List<String> desiredVaccines, List<VakcinaDTO> vakcine) {
        for (String vaccine: desiredVaccines){
            for (VakcinaDTO v : vakcine) // zeljena je vakcina i ima dovoljna kolicina
                if (vaccine.equals(v.getNazivProizvodjaca()) && v.getValue() > 0)
                    return vaccine;
        }
        return "Empty";
    }

    // yyyy-MM-dd HH:mm
    private String checkDate(TerminDTO terminDTO, LocalDateTime ldt, OdgovorTerminDTO odgovor) {
        LocalDateTime sledeciDan = ldt.plusDays(1); // ako je podneto dns da gleda datume od sutra
        LocalDate granica = ldt.plusWeeks(8).toLocalDate(); // u nedelji dana od sledec dana pa 7 dana

        String datum;
        System.out.println(terminDTO);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateOfTermin = LocalDateTime.parse(terminDTO.getDatum(), formatter);

        int value = -1;

        if (ldt.toLocalDate().isAfter(dateOfTermin.toLocalDate()) ||
        ldt.toLocalDate().isEqual(dateOfTermin.toLocalDate())) { // znaci imamo stari termin postavljamo novi
            dateOfTermin = sledeciDan.with(LocalTime.of(8, 0));
            value = 1;
        } else {
            if (terminDTO.getValue() == 10) { // ako je pun termin, prelazi se u sledeci sat ili dan sve dok je u toku sedmice
                if (dateOfTermin.getHour()+1 == 22) { // treba  preci u sledeci dan
                    dateOfTermin = dateOfTermin.plusDays(1).with(LocalTime.of(8, 0));
                    if (dateOfTermin.toLocalDate().equals(granica)) // ako je prekoracio sedmicu
                        return "Empty";
                    // treba paziti je li van sedmice dana
                } else {
                    dateOfTermin = dateOfTermin.with(LocalTime.of(dateOfTermin.getHour()+1, 0));
                }
                value = 1;
            } else {
                value = terminDTO.getValue() + 1;
            }
        }
        odgovor.setVrednost(value); // postavljena vrednost
        datum = dateOfTermin.format(formatter);
        return datum;
    }


}
