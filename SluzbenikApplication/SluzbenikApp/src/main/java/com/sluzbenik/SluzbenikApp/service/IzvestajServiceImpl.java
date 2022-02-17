package com.sluzbenik.SluzbenikApp.service;

import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.IzvestajDTO;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.izvestaj.Izvestaj;
import com.sluzbenik.SluzbenikApp.repository.rdfRepository.DzsRdfRepository;
import com.sluzbenik.SluzbenikApp.repository.xmlFileReaderWriter.GenericXMLReaderWriter;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.GenericXMLRepository;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import com.sluzbenik.SluzbenikApp.transformers.XML2HTMLTransformer;
import com.sluzbenik.SluzbenikApp.transformers.XSLFOTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import java.io.StringWriter;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Map;

import static com.sluzbenik.SluzbenikApp.repository.Constants.*;
import static com.sluzbenik.SluzbenikApp.transformers.Constants.*;

@Service
public class IzvestajServiceImpl implements IzvestajService {

    @Autowired
    private GenericXMLRepository<Izvestaj> repository;

    @Autowired
    private GenericXMLReaderWriter<Izvestaj> repositoryReaderWriter;

    @Autowired
    private XSLFOTransformer transformerXML2PDF;

    @Autowired
    private XML2HTMLTransformer transformerXML2HTML;

    @Autowired
    private DzsRdfRepository dzsRdfRepository;

    @PostConstruct
    private void postConstruct(){
        this.repository.setRepositoryParams(PACKAGE_PATH_IZVESTAJ, COLLECTION_PATH_IZVESTAJ, new IdGeneratorPosInt());
        this.repositoryReaderWriter.setRepositoryParams(PACKAGE_PATH_IZVESTAJ, XML_SCHEMA_PATH_IZVESTAJ);
    }

    @Override
    public Izvestaj findOneById(String id) {
        return repository.retrieveXML(id);
    }

    @Override
    public byte[] generateIzvestajPDF(String id) throws Exception {
        return transformerXML2PDF.generatePDF(repository.retrieveXMLAsDOMNode(id), IZVESTAJ_XSL_FO_PATH, null);
    }

    @Override
    public String generateIzvestajHTML(String id) throws Exception {
        String htmlString = transformerXML2HTML.generateHTML(repository.retrieveXMLAsDOMNode(id), IZVESTAJ_XSL_PATH, null);
        return htmlString;
    }

    @Override
    public IzvestajDTO createReport(IzvestajDTO izvestajDTO, String dateFrom, String dateTo) {
        izvestajDTO.setBrojZelenih(this.dzsRdfRepository.getDzsBetweenDates(dateFrom, dateTo));
        return izvestajDTO; // ovdje praviti izvjestaj jer se ovdje nalaze svi podaci
    }

    @Override
    public String generateReport(IzvestajDTO izvjestajDTO, String dateFrom, String dateTo) throws DatatypeConfigurationException {
        IzvestajDTO izvestajDTO = createReport(izvjestajDTO, dateFrom, dateTo);
        Izvestaj i = new Izvestaj();
        i.setBrojDokumenataOInteresovanju(izvestajDTO.getBrojInteresovanja());
        i.setBrojPristiglihZahtevaZaDZS(izvestajDTO.getBrojZahteva());
        i.setBrojIzdatihZahtevaZaDZS(izvestajDTO.getBrojZelenih());
        i.setBrojPrimnljenihNovovakcinisanih(izvestajDTO.getNoveVakcine());
        i.setBrojPrimljenihVakcina(izvestajDTO.getStanje().get("Ukupno"));
        i.setProizvodjaci(new Izvestaj.Proizvodjaci());
        i.getProizvodjaci().getVakcina(); // ovo ako je null kreira novu listu
        for (Map.Entry<String, Integer> entry: izvestajDTO.getStanje().entrySet()) {
            if (!entry.getKey().equals("Ukupno")) {
                Izvestaj.Proizvodjaci.Vakcina v = new Izvestaj.Proizvodjaci.Vakcina();
                v.setNazivProizvodjaca(entry.getKey());
                v.setValue(BigInteger.valueOf(entry.getValue()));
                i.getProizvodjaci().getVakcina().add(v);
            }

        }
        i.setDatum(DatatypeFactory.newInstance().newXMLGregorianCalendar(LocalDate.now().toString()));

        return this.repository.storeXML(i, true);
    }


}
