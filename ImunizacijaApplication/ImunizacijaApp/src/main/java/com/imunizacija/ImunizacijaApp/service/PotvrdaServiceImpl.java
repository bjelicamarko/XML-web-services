package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.OdgovorTerminDTO;
import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.SearchResults;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.odgovori.Odgovori;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.PotvrdaExtractMetadata;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.PotvrdaRdfRepository;
import com.imunizacija.ImunizacijaApp.repository.rdfRepository.RdfRepository;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju.Saglasnost;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.util.*;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import com.imunizacija.ImunizacijaApp.transformers.XML2HTMLTransformer;
import com.imunizacija.ImunizacijaApp.transformers.XSLFOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xmldb.api.base.XMLDBException;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;
import static com.imunizacija.ImunizacijaApp.transformers.Constants.*;

@Service
public class PotvrdaServiceImpl implements PotvrdaService {

    @Autowired
    private GenericXMLRepository<PotvrdaOVakcinaciji> repository;

    @Autowired
    private RdfRepository rdfRepository;

    @Autowired
    private PotvrdaExtractMetadata potvrdaExtractMetadata;

    @Autowired
    private PotvrdaRdfRepository potvrdaRdfRepository;

    @Autowired
    private XSLFOTransformer transformerXML2PDF;

    @Autowired
    private XML2HTMLTransformer transformerXML2HTML;

    @Autowired
    private OdgovoriService odgovoriService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MailService mailService;

    public static final String URL_RESOURCE_ROOT = "potvrda/generatePDF/";

    private static final String ID_PATH = "@Sifra_potvrde";

    @PostConstruct // after init
    private void postConstruct(){
        this.repository.setRepositoryParams(PACKAGE_PATH_POTVRDA, COLLECTION_PATH_POTVRDA, new IdGeneratorPosInt(), POTVRDA_NAMESPACE_PATH);
    }

    @Override
    public PotvrdaOVakcinaciji findOneById(String id) {
        return repository.retrieveXML(id);
    }

    @Override
    public PotvrdaOVakcinaciji findLastOneByUser(String userID) {
        String potvrdaId = rdfRepository.getPosljednjaPotvrdaIzBazeId(userID);
        if(potvrdaId.equals("-1"))
            return null;
        return repository.retrieveXML(potvrdaId+".xml");
    }

    public byte[] generatePotvrdaPDF(String id) throws Exception {
        String resourceUrl = URL_ROOT + URL_RESOURCE_ROOT + id;
        return transformerXML2PDF.generatePDF(repository.retrieveXMLAsDOMNode(id), POTVRDA_XSL_FO_PATH, resourceUrl);
    }

    @Override
    public String generatePotvrdaHTML(String id) throws Exception {
        String resourceUrl = URL_ROOT + URL_RESOURCE_ROOT + id;
        String htmlString = transformerXML2HTML.generateHTML(repository.retrieveXMLAsDOMNode(id), POTVRDA_XSL_PATH, resourceUrl);
        return htmlString;
    }

    @Override
    public SearchResults searchDocuments(String userId, String searchText) throws XMLDBException {
        SearchResults searchResults;
        searchResults = repository.searchDocuments(userId, searchText, ID_PATH);
        this.potvrdaRdfRepository.getPotvrdaWithReferences(searchResults);
        return searchResults;
    }

    @Override
    public void generatePotvrdaOVakcinaciji(Saglasnost s) throws Exception {
        PotvrdaOVakcinaciji potvrdaOVakcinaciji = new PotvrdaOVakcinaciji();
        // generate PodaciOPrimaocu
        potvrdaOVakcinaciji.setPodaciOPrimaocu(this.generatePodaciOPrimaocu(s.getLicniPodaci(), s.getDrzavljanstvo()));
        // generate PodaciOVakcini
        potvrdaOVakcinaciji.setPodaciOVakcini(this.generatePodaciOVakcini(s.getOVakcinaciji(), s.getDrzavljanstvo().getJMBG(), s.getKontakt().getEmailAdresa()));
        // generate PodaciOPotvrdi
        PotvrdaOVakcinaciji.PodaciOPotvrdi podaciOPotvrdi = new PotvrdaOVakcinaciji.PodaciOPotvrdi();
        XMLGregorianCalendar today = DatatypeFactory.newInstance().newXMLGregorianCalendar(LocalDate.now().toString());
        podaciOPotvrdi.setDatumIzdavanja(today);
        podaciOPotvrdi.setQRCode("QrCode");
        potvrdaOVakcinaciji.setPodaciOPotvrdi(podaciOPotvrdi);

        // save new PotvrdaOVakcinaciji
        String newPotvrdaId = this.repository.storeXML(potvrdaOVakcinaciji, true);
        this.potvrdaExtractMetadata.extract(potvrdaOVakcinaciji);

        // setting QRCode
        podaciOPotvrdi.setQRCode(String.format(URL_ROOT + URL_RESOURCE_ROOT + "%s.xml", newPotvrdaId));
        potvrdaOVakcinaciji.setPodaciOPotvrdi(podaciOPotvrdi);
        this.repository.storeXML(potvrdaOVakcinaciji, false);
    }

    private PotvrdaOVakcinaciji.PodaciOPrimaocu generatePodaciOPrimaocu(LicniPodaciDetaljnije saglasnostLicniPodaci, Drzavljanstvo drzavljanstvo) {
        PotvrdaOVakcinaciji.PodaciOPrimaocu podaciOPrimaocu = new PotvrdaOVakcinaciji.PodaciOPrimaocu();
        podaciOPrimaocu.setIme(saglasnostLicniPodaci.getIme());
        podaciOPrimaocu.setPrezime(saglasnostLicniPodaci.getPrezime());
        podaciOPrimaocu.setDatumRodjenja(saglasnostLicniPodaci.getDatumRodjenja());
        podaciOPrimaocu.setPol(saglasnostLicniPodaci.getPol());
        podaciOPrimaocu.setDrzavljanstvo(drzavljanstvo);
        return podaciOPrimaocu;
    }

    private PotvrdaOVakcinaciji.PodaciOVakcini generatePodaciOVakcini(Saglasnost.OVakcinaciji oVakcinaciji, String korisnikId, String email)
            throws Exception {
        PotvrdaOVakcinaciji.PodaciOVakcini podaciOVakcini = new PotvrdaOVakcinaciji.PodaciOVakcini();
        List<DozaSaUstanovom> doze = new ArrayList<>();
        String potvrdaIzBazeId = this.rdfRepository.getPosljednjaPotvrdaIzBazeId(korisnikId);
        if(!potvrdaIzBazeId.equals("-1")){
            PotvrdaOVakcinaciji prethodnaPotvrda = repository.retrieveXML(potvrdaIzBazeId);
            doze.addAll(prethodnaPotvrda.getPodaciOVakcini().getDoze());
        }
        doze.add(this.generateDozaSaUstanovom(doze.size() + 1, oVakcinaciji));
        podaciOVakcini.setDoze(doze);

        if (doze.size() == 3) {
            this.odgovoriService.izbrisiOdgovor(email);
        } else {
            createNewTermin(email, podaciOVakcini);
        }
        return podaciOVakcini;
    }

    private void createNewTermin(String email, PotvrdaOVakcinaciji.PodaciOVakcini podaciOVakcini) throws Exception{
        Odgovori.Odgovor o = this.odgovoriService.vratiOdgovor(email);

        OdgovorTerminDTO odgovorTerminDTO = new OdgovorTerminDTO();
        odgovorTerminDTO.setGrad(o.getGrad());
        for (String s : o.getVakcine())
            odgovorTerminDTO.getVakcine().add(s);
        odgovorTerminDTO.setEmail(o.getEmail());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        HttpEntity<OdgovorTerminDTO> requestUpdate = new HttpEntity<>(odgovorTerminDTO, headers);
        ResponseEntity<OdgovorTerminDTO> entity = restTemplate.exchange("http://localhost:9000/api/sistemski-magacin/dobaviTermin",
                HttpMethod.POST, requestUpdate, OdgovorTerminDTO.class);

        this.odgovoriService.azurirajOdgovor(entity.getBody());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = "01/01/1971";
        LocalDate localDate = LocalDate.parse(date, formatter);

        if (Objects.requireNonNull(entity.getBody()).getTermin().equals("Empty")) {
            podaciOVakcini.setDatumNaredneDoze(
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(localDate.toString()));
        }
        else {
            podaciOVakcini.setDatumNaredneDoze(
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(entity.getBody().getTermin().split(" ")[0]));
        }

        this.mailService.sendMail("Termin",
                this.odgovoriService.generisiTekstOdgovora(Objects.requireNonNull(entity.getBody())),
                entity.getBody().getEmail());
    }

    private DozaSaUstanovom generateDozaSaUstanovom(int num, Saglasnost.OVakcinaciji oVakcinaciji) {
        DozaDetaljnije dozaDetaljnije = oVakcinaciji.getDoze().get(0);
        DozaSaUstanovom dozaSaUstanovom = new DozaSaUstanovom();
        dozaSaUstanovom.setRedniBroj(new BigInteger(String.valueOf(num)));
        dozaSaUstanovom.setDatum(dozaDetaljnije.getDatum());
        dozaSaUstanovom.setSerija(dozaDetaljnije.getSerija());
        dozaSaUstanovom.setProizvodjac(dozaDetaljnije.getProizvodjac());
        dozaSaUstanovom.setTip(dozaDetaljnije.getTip());
        dozaSaUstanovom.setUstanova(oVakcinaciji.getZdravstvenaUstanova());
        return dozaSaUstanovom;
    }
}
