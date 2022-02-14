package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.OdgovorTerminDTO;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;
import com.imunizacija.ImunizacijaApp.repository.xmlFileReaderWriter.GenericXMLReaderWriter;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@Service
public class InteresovanjeServiceImpl implements InteresovanjeService {

    @Autowired
    private GenericXMLRepository<Interesovanje> repository;

    @Autowired
    private GenericXMLReaderWriter<Interesovanje> repositoryReaderWriter;

    @Autowired
    private MailService mailService;

    @Autowired
    private OdgovoriService odgovoriService;

    @PostConstruct // after init
    private void postConstruct(){
        this.repository.setRepositoryParams(PACKAGE_PATH_INTERESOVANJE, COLLECTION_PATH_INTERESOVANJE, new IdGeneratorPosInt());
        this.repositoryReaderWriter.setRepositoryParams(PACKAGE_PATH_INTERESOVANJE, XML_SCHEMA_PATH_INTERESOVANJE);
    }

    @Override
    public Interesovanje findOneById(String id) {
        return repository.retrieveXML(id);
    }

    @Override
    public void createNewInterest(String interesovanje) throws MessagingException {
        Interesovanje i = repositoryReaderWriter.checkSchema(interesovanje);
        this.mailService.sendMail("Novo interesovanje", this.generateTextFromInterest(i),
                i.getKontakt().getEmailAdresa());
        this.repository.storeXML(i, true);

        OdgovorTerminDTO odgovorTerminDTO = new OdgovorTerminDTO();
        odgovorTerminDTO.setEmail(i.getKontakt().getEmailAdresa());
        odgovorTerminDTO.setGrad(i.getOpstinaVakcinisanja());
        for (Interesovanje.Vakcina v : i.getVakcine())
            odgovorTerminDTO.getVakcine().add(v.getTip());
        this.odgovoriService.dodajOdgovor(odgovorTerminDTO);
    }

    private String generateTextFromInterest(Interesovanje interesovanje) {
        StringBuilder sb = new StringBuilder();
        if (interesovanje.getDrzavljanstvo().getTip().equals("DOMACE")) {
            sb.append(String.format("JMBG: %s\n", interesovanje.getDrzavljanstvo().getJMBG()));
        } else if (interesovanje.getDrzavljanstvo().getTip().equals("STRANO_SA_BORAVKOM")) {
            sb.append(String.format("Evidencioni broj stranca: %s\n", interesovanje.getDrzavljanstvo().getEvidencioniBrojStranca()));
        } else {  // STRANO_BEZ_BORAVKA
            sb.append(String.format("Broj pasosa: %s\n", interesovanje.getDrzavljanstvo().getBrPasosa()));
        }

        StringBuilder sb2 = new StringBuilder();
        for (Interesovanje.Vakcina v : interesovanje.getVakcine())
            sb2.append(String.format("Vakcina: %s\n", v.getTip()));

        return String.format("Drzavljanstvo: %s\n" + "Ime: %s\n" +
                "Prezime: %s\n" +
                "Email: %s\n" +
                "Broj telefona: %s\n" +
                "Broj fiksnog telefona: %s\n" +
                "Opstina vakcinisanja: %s\n" +
                "Datum predaje interesovanja: %s\n" +
                "Izabrane zelene vakcine: \n %s", sb.toString(), interesovanje.getIme(), interesovanje.getPrezime(),
                interesovanje.getKontakt().getEmailAdresa(), interesovanje.getKontakt().getBrojTelefona(),
                interesovanje.getKontakt().getBrojFiksnosgTelefona(),
                interesovanje.getOpstinaVakcinisanja(), interesovanje.getDatum().toString(), sb2.toString());
    }
}
