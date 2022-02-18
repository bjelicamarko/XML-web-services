package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.MapaDTO;
import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.VakcinaKolicinaDTO;
import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.OdgovorTerminDTO;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.odgovori.Odgovori;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.OdgovoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class OdgovoriServiceImpl implements OdgovoriService{

    @Autowired
    private OdgovoriRepository odgovoriRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Lazy
    private PotvrdaService potvrdaService;

    @Override
    public void dodajOdgovor(OdgovorTerminDTO odgovor) {
        this.odgovoriRepository.dodajOdgovor(odgovor);
    }

    @Override
    public void azurirajOdgovor(OdgovorTerminDTO odgovor) {
        this.odgovoriRepository.azurirajOdgovor(odgovor);
    }

    @Override
    public void izbrisiOdgovor(String email) {
        this.odgovoriRepository.izbrisiOdgovor(email);
    }

    @Override
    public Odgovori.Odgovor vratiOdgovor(String email) {
        return this.odgovoriRepository.vratiOdgovor(email);
    }

    @Override
   // @Scheduled(cron = "0 25 16 * * ?", zone = "CET")
    public VakcinaKolicinaDTO vratiDozeUMagacin() {
        VakcinaKolicinaDTO vakcinaKolicinaDTO = new VakcinaKolicinaDTO();
        List<Odgovori.Odgovor> odgovori = odgovoriRepository.vratiOdgovore(LocalDate.now().toString());
        for (Odgovori.Odgovor o : odgovori) {
            if (!vakcinaKolicinaDTO.getMapa().containsKey(o.getGrad())) {
                vakcinaKolicinaDTO.getMapa().put(o.getGrad(), new MapaDTO());
                vakcinaKolicinaDTO.getMapa().get(o.getGrad()).getMapa().put(o.getDodeljenaVakcina(), 1);
            } else {
                int value = vakcinaKolicinaDTO.getMapa().get(o.getGrad()).getMapa().get(o.getDodeljenaVakcina());
                vakcinaKolicinaDTO.getMapa().get(o.getGrad()).getMapa().put(o.getDodeljenaVakcina(), value+1);
            }
            OdgovorTerminDTO odgovorTerminDTO = new OdgovorTerminDTO();
            odgovorTerminDTO.setGrad(o.getGrad());
            for (String s : o.getVakcine())
                odgovorTerminDTO.getVakcine().add(s);
            odgovorTerminDTO.setEmail(o.getEmail());
            this.azurirajOdgovor(odgovorTerminDTO);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        HttpEntity<VakcinaKolicinaDTO> requestUpdate = new HttpEntity<>(vakcinaKolicinaDTO, headers);
        ResponseEntity<String> entity = restTemplate
                .exchange("http://localhost:9000/api/sistemski-magacin/dobaviDozeZaostavljene",
               HttpMethod.POST, requestUpdate, String.class);

        System.out.println(entity.getBody());
        return vakcinaKolicinaDTO;
    }

    @Override
  //  @Scheduled(cron = "0 15 22 * * ?", zone = "CET")
    public void posaljiOdgovore() throws MessagingException {
        List<Odgovori.Odgovor> odgovori = odgovoriRepository.vratiOdgovore("Empty");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        for (Odgovori.Odgovor o : odgovori) {
            OdgovorTerminDTO odgovorTerminDTO = new OdgovorTerminDTO();
            odgovorTerminDTO.setGrad(o.getGrad());
            for (String s : o.getVakcine())
                odgovorTerminDTO.getVakcine().add(s);
            odgovorTerminDTO.setEmail(o.getEmail());

            HttpEntity<OdgovorTerminDTO> requestUpdate = new HttpEntity<>(odgovorTerminDTO, headers);
            ResponseEntity<OdgovorTerminDTO> entity = restTemplate.exchange("http://localhost:9000/api/sistemski-magacin/dobaviTermin",
                    HttpMethod.POST, requestUpdate, OdgovorTerminDTO.class);

            this.azurirajOdgovor(entity.getBody());

            this.mailService.sendMail("Termin",
                    this.generisiTekstOdgovora(Objects.requireNonNull(entity.getBody())),
                    entity.getBody().getEmail());
        }
    }

    public String generisiTekstOdgovora(OdgovorTerminDTO odgovorTerminDTO) {
        return String.format("Dodeljen termin: %s\n" +
                "Ustanova: %s\n" +
                "Vakcina dodeljena: %s\n" +
                "Redni broj u terminu: %s\n", odgovorTerminDTO.getTermin(),
                odgovorTerminDTO.getUstanova(), odgovorTerminDTO.getVakcinaDodeljena(), odgovorTerminDTO.getVrednost());
    }

}
