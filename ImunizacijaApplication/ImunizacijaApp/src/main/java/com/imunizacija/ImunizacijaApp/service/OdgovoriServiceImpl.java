package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.OdgovorTerminDTO;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.odgovori.Odgovori;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.OdgovoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OdgovoriServiceImpl implements OdgovoriService{

    @Autowired
    private OdgovoriRepository odgovoriRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void dodajOdgovor(OdgovorTerminDTO odgovor) {
        this.odgovoriRepository.dodajOdgovor(odgovor);
    }

    @Override
    public void azurirajOdgovor(OdgovorTerminDTO odgovor) {
        this.odgovoriRepository.azurirajOdgovor(odgovor);
    }

    @Override
    @Scheduled(cron = "0 23 20 * * ?", zone = "CET")
    public void posaljiOdgovore() {
        List<Odgovori.Odgovor> odgovori = odgovoriRepository.vratiOdgovore();
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
        }
    }

}
