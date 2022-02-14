package com.sluzbenik.SluzbenikApp.repository.xmlRepository;

import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.GradVakcineDTO;
import com.sluzbenik.SluzbenikApp.model.dto.comunication_dto.OdgovorTerminDTO;
import com.sluzbenik.SluzbenikApp.model.dto.termini_dto.GradDTO;
import com.sluzbenik.SluzbenikApp.model.dto.termini_dto.GradVakcinaKolicinaDTO;
import com.sluzbenik.SluzbenikApp.model.dto.termini_dto.VakcinaDTO;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.termini.SistemskiMagacin;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static com.sluzbenik.SluzbenikApp.repository.Constants.*;
import static com.sluzbenik.SluzbenikApp.template.XUpdateTemplate.UPDATE_SISTEMSKI_MAGACIN;


@Component
public class SistemskiMagacinRepository extends GenericXMLRepository<SistemskiMagacin>{

    public SistemskiMagacinRepository() {
        this.idGenerator = new IdGeneratorPosInt();
        this.packagePath = PACKAGE_PATH_TERMINI;
        this.collectionPath = COLLECTION_PATH_TERMINI;
    }

    public void updateVaccine(GradVakcinaKolicinaDTO gradVakcinaKolicinaDTO) {
        String contextXPath = String.format("//Grad[@Ime='%s']/Vakcina[@Naziv_proizvodjaca='%s']",
                gradVakcinaKolicinaDTO.getGrad(), gradVakcinaKolicinaDTO.getNazivProizvodjaca());

        try {
            Collection col = getOrCreateCollection(this.collectionPath);
            XUpdateQueryService xupdateService = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            xupdateService.setProperty("indent", "yes");

            String patch = String.valueOf(gradVakcinaKolicinaDTO.getKolicina());

            System.out.println("[INFO] Updating " + contextXPath + " node.");
            long mods = xupdateService.updateResource(XML_TERMIN,
                    String.format(UPDATE_SISTEMSKI_MAGACIN, contextXPath, patch));
            System.out.println("[INFO] " + mods + " modifications processed.");

        } catch (XMLDBException e) {
            e.printStackTrace();
        }
    }

    public List<VakcinaDTO> getVaccineStatusOfCity(String city) {
        String xpathExp = String.format("doc(\"%s\")//Grad[@Ime=\"%s\"]/Vakcina",
                "termini.xml", city);
        List<VakcinaDTO> vakcine = new ArrayList<>();

        try {
            Collection col = getOrCreateCollection(this.collectionPath);
            XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpathService.setProperty("indent", "yes");
            xpathService.setNamespace("", TERMIN_NAMESPACE_PATH);

            ResourceSet result = xpathService.query(xpathExp);
            ResourceIterator i = result.getIterator();
            Resource res = null;

            JAXBContext context = JAXBContext.newInstance("com.sluzbenik.SluzbenikApp.model.dto.termini_dto");
            Unmarshaller u = context.createUnmarshaller();
            while(i.hasMoreResources()) {
                try {
                    res = i.nextResource();
                    System.out.println(res.getContent());
                    String str = res.getContent().toString();
                    VakcinaDTO v = (VakcinaDTO) u.unmarshal(new StreamSource(new StringReader(str)));
                    System.out.println(v);
                    vakcine.add(v);
                } finally {
                    try {
                        assert res != null;
                        ((EXistResource)res).freeResources();
                    } catch (XMLDBException xe) {
                        xe.printStackTrace();
                    }
                }
            }

        } catch (XMLDBException | JAXBException e) {
            e.printStackTrace();
        }
        return vakcine;
    }

    public GradDTO getSelectedCity(GradVakcineDTO gradVakcineDTO) {
        String xpathExp = String.format("doc(\"%s\")//Grad[@Ime=\"%s\"]",
                "termini.xml", gradVakcineDTO.getGrad());
        try {
            Collection col = getOrCreateCollection(this.collectionPath);
            XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpathService.setProperty("indent", "yes");
            xpathService.setNamespace("", TERMIN_NAMESPACE_PATH);

            ResourceSet result = xpathService.query(xpathExp);
            ResourceIterator i = result.getIterator();
            Resource res = i.nextResource();

            JAXBContext context = JAXBContext.newInstance("com.sluzbenik.SluzbenikApp.model.dto.termini_dto");
            Unmarshaller u = context.createUnmarshaller();
            System.out.println(res.getContent());
            return (GradDTO) u.unmarshal(new StreamSource(new StringReader(res.getContent().toString())));

        } catch (XMLDBException | JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateTermin(OdgovorTerminDTO odgovor) {
        String contextXPath = String.format("//Grad[@Ime='%s']/Ustanova[@naziv='%s']",
                odgovor.getGrad(), odgovor.getUstanova());

        try {
            Collection col = getOrCreateCollection(this.collectionPath);
            XUpdateQueryService xupdateService = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            xupdateService.setProperty("indent", "yes");

            String patch = String.format("<Termin datum=\"%s\">%s</Termin>", odgovor.getTermin(),
                    odgovor.getVrednost());

            System.out.println("[INFO] Updating " + contextXPath + " node.");
            long mods = xupdateService.updateResource(XML_TERMIN,
                    String.format(UPDATE_SISTEMSKI_MAGACIN, contextXPath, patch));
            System.out.println("[INFO] " + mods + " modifications processed.");

        } catch (XMLDBException e) {
            e.printStackTrace();
        }
    }
}
