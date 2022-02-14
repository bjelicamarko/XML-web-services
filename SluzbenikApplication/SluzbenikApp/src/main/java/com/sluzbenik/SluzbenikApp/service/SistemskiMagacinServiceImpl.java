package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.OdgovorTerminDTO;
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
    public List<VakcinaDTO> getVaccineStatusOfCity(String city) {
        return this.sistemskiMagacinRepository.getVaccineStatusOfCity(city);
    }

    @Override
    public GradDTO getSelectedCity(OdgovorTerminDTO ogovorTerminDTO) {
        return this.sistemskiMagacinRepository.getSelectedCity(ogovorTerminDTO);
    }

    @Override
    public OdgovorTerminDTO getTermin(OdgovorTerminDTO ogovorTerminDTO) {

        GradDTO city = this.getSelectedCity(ogovorTerminDTO);
        ogovorTerminDTO.setGrad(city.getIme()); // postavljen izabran grad

        // izabrana vakcina
        String chosenVaccine = this.checkVaccineStatus(ogovorTerminDTO.getVakcine(), city.getVakcine());
        if (chosenVaccine.equals("Empty")) {
            ogovorTerminDTO.setRazlog("Nema dovoljno trazene vakcine!");
            return ogovorTerminDTO;
        }
        ogovorTerminDTO.setVakcinaDodeljena(chosenVaccine); // postavljena izabrana vakcina

        String termin = "Empty";
        for (UstanovaDTO u : city.getUstanove()) {
            termin = checkDate(u.getTermin(), LocalDateTime.now(), ogovorTerminDTO);
            if (!termin.equals("Empty")) {// nasli termin u prvoj ustanovi slobodnoj
                ogovorTerminDTO.setRazlog("Uspesno pronadjen termin!");
                ogovorTerminDTO.setTermin(termin); // postavljen termin
                ogovorTerminDTO.setUstanova(u.getNaziv()); // postavljen razlog i ustanova
                break;
            }
        }
        if (termin.equals("Empty")) {
            ogovorTerminDTO.setRazlog("Nema slobodnih termina!");
            return ogovorTerminDTO;
        }

        // ovdje da ide azuriranje baze
        this.sistemskiMagacinRepository.updateTermin(ogovorTerminDTO);
        return ogovorTerminDTO;
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
