package com.imunizacija.ImunizacijaApp.service;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.OdgovorTerminDTO;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.odgovori.Odgovori;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.potvrda_o_vakcinaciji.PotvrdaOVakcinaciji;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju.Saglasnost;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.util.*;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.GenericXMLRepository;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import com.imunizacija.ImunizacijaApp.transformers.XML2HTMLTransformer;
import com.imunizacija.ImunizacijaApp.transformers.XSLFOTransformer;
import com.imunizacija.ImunizacijaApp.utils.AuthenticationUtilities;
import com.imunizacija.ImunizacijaApp.utils.SparqlUtil;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.impl.BagImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.io.StringWriter;
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
    private XSLFOTransformer transformerXML2PDF;

    @Autowired
    private XML2HTMLTransformer transformerXML2HTML;

    @Autowired
    private OdgovoriService odgovoriService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MailService mailService;

    public static final String URL_RESOURCE_ROOT = "potvrda/";
    public static final String ISSUED_TO_PREDICATE_DB = "<http://www.vakc-sistem.rs/predicate/issuedTo>";
    public static final String CREATED_AT_PREDICATE_DB = "<http://www.vakc-sistem.rs/predicate/createdAt>";

    @PostConstruct // after init
    private void postConstruct(){
        this.repository.setRepositoryParams(PACKAGE_PATH_POTVRDA, COLLECTION_PATH_POTVRDA, new IdGeneratorPosInt());
    }

    @Override
    public PotvrdaOVakcinaciji findOneById(String id) {
        return repository.retrieveXML(id);
    }

    @Override
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
    public void generatePotvrdaOVakcinaciji(Saglasnost s) throws DatatypeConfigurationException, MessagingException {
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

        // setting QRCode
        podaciOPotvrdi.setQRCode(String.format(URL_RESOURCE_ROOT + "%s", newPotvrdaId));
        potvrdaOVakcinaciji.setPodaciOPotvrdi(podaciOPotvrdi);
        this.repository.storeXML(potvrdaOVakcinaciji, false);
    }

    private LicniPodaciJMBG generatePodaciOPrimaocu(LicniPodaciDetaljnije saglasnostLicniPodaci, Drzavljanstvo drzavljanstvo) {
        LicniPodaciJMBG licniPodaci = new LicniPodaciJMBG();
        licniPodaci.setIme(saglasnostLicniPodaci.getIme());
        licniPodaci.setPrezime(saglasnostLicniPodaci.getPrezime());
        licniPodaci.setDatumRodjenja(saglasnostLicniPodaci.getDatumRodjenja());
        licniPodaci.setPol(saglasnostLicniPodaci.getPol());
        if(drzavljanstvo.getTip().equals("DOMACE"))
            licniPodaci.setJMBG(drzavljanstvo.getJMBG());
        else
            licniPodaci.setJMBG("0101901404404");
        return licniPodaci;
    }

    private PotvrdaOVakcinaciji.PodaciOVakcini generatePodaciOVakcini(Saglasnost.OVakcinaciji oVakcinaciji, String korisnikId, String email)
            throws DatatypeConfigurationException, MessagingException {
        PotvrdaOVakcinaciji.PodaciOVakcini podaciOVakcini = new PotvrdaOVakcinaciji.PodaciOVakcini();
        List<DozaSaUstanovom> doze = new ArrayList<>();
        String potvrdaIzBazeId = this.getPosljednjaPotvrdaIzBazeId(korisnikId);
        if(!potvrdaIzBazeId.equals("-1")){
            PotvrdaOVakcinaciji prethodnaPotvrda = repository.retrieveXML(potvrdaIzBazeId);
            doze.addAll(prethodnaPotvrda.getPodaciOVakcini().getDoze());
        }
        doze.add(this.generateDozaSaUstanovom(doze.size() + 1, oVakcinaciji));
        podaciOVakcini.setDoze(doze);

        if (doze.size() == 3) { // sklanjamo iz baze
            this.odgovoriService.izbrisiOdgovor(email);
        } else {
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
            //convert String to LocalDate
            LocalDate localDate = LocalDate.parse(date, formatter);

            if (Objects.requireNonNull(entity.getBody()).getTermin().equals("Empty")) {
                podaciOVakcini.setDatumNaredneDoze(
                        DatatypeFactory.newInstance().newXMLGregorianCalendar(localDate.toString())); // today + 7 days
            } else {   // ima dodjeljen termin
                podaciOVakcini.setDatumNaredneDoze( // YYYY-MM-DD
                        DatatypeFactory.newInstance().newXMLGregorianCalendar(entity.getBody().getTermin().split(" ")[0]));
            }

            this.mailService.sendMail("Termin",
                    this.odgovoriService.generisiTekstOdgovora(Objects.requireNonNull(entity.getBody())),
                    entity.getBody().getEmail());
        }

        return podaciOVakcini;
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

    // TODO: ovo prebaciti u RdfRepository
    private String getPosljednjaPotvrdaIzBazeId(String korisnikId) {
        AuthenticationUtilities.ConnectionPropertiesFusekiJena conn = AuthenticationUtilities.setUpPropertiesFusekiJena();
        String sparqlCondition = "?potvrda " +  ISSUED_TO_PREDICATE_DB + "<" + OSOBA_NAMESPACE_PATH + korisnikId + "> ."
                + "?potvrda " + CREATED_AT_PREDICATE_DB + " ?datum";
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + POTVRDA_NAMED_GRAPH_URI, sparqlCondition);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        // Query the collection, dump output response with the use of ResultSetFormatter
        ResultSet results = query.execSelect();
        List<String[]> affirmationList = new ArrayList<>();
        while(results.hasNext()) {
            QuerySolution res = results.nextSolution();
            String affirmation = res.get("potvrda").toString();
            String date = res.get("datum").toString();
            affirmationList.add(new String[]{affirmation.substring(POTVRDA_NAMESPACE_PATH.length()), date}); //uzimamo samo id
        }
        query.close();
        if(affirmationList.isEmpty())
            return "-1"; // ako nema onda -1
        affirmationList.sort(Comparator.comparing(o -> LocalDate.parse(o[1])));

        List<String> sortedAffirmation = new ArrayList<>();
        affirmationList.forEach(affirmation -> sortedAffirmation.add(affirmation[0]));
        return sortedAffirmation.get(sortedAffirmation.size() - 1);
    }

    public static final String HAS_EMAIL_PREDICATE_DB = "http://www.vakc-sistem.rs/predicate/hasEmail";
    private String getKorisnikEmailById(String korisnikId) {
        AuthenticationUtilities.ConnectionPropertiesFusekiJena conn = AuthenticationUtilities.setUpPropertiesFusekiJena();
        String sparqlCondition = String.format("?osoba " + "<" + HAS_EMAIL_PREDICATE_DB + "> ?email . \n" +
                "FILTER (contains(str(?osoba), '%s') )", korisnikId);
        // ?osoba http://www.vakc-sistem.rs/predicate/hasEmail ?email .
        String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + OSOBA_NAMED_GRAPH_URI, sparqlCondition);

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);

        // Query the collection, dump output response with the use of ResultSetFormatter
        ResultSet results = query.execSelect();
        String email = "";
        while(results.hasNext()) {
            QuerySolution res = results.nextSolution();
            email = res.get("email").toString();
        }
        query.close();
        return email;
    }
}
