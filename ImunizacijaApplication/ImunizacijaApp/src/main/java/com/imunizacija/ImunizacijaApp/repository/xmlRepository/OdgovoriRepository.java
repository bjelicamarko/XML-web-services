package com.imunizacija.ImunizacijaApp.repository.xmlRepository;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.OdgovorTerminDTO;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.odgovori.Odgovori;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
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

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;
import static com.imunizacija.ImunizacijaApp.template.XUpdateTemplate.*;

@Component
public class OdgovoriRepository extends GenericXMLRepository<Odgovori>{

    public OdgovoriRepository() {
        this.idGenerator = new IdGeneratorPosInt();
        this.packagePath = PACKAGE_PATH_ODGOVORI;
        this.collectionPath = COLLECTION_PATH_ODGOVORI;
    }

    public List<Odgovori.Odgovor> vratiOdgovore(String parametar) {
        String  xpathExp = String.format("doc(\"odgovori.xml\")//Termin[contains(text(),'%s')]/..", parametar);

        List<Odgovori.Odgovor> odgovori = new ArrayList<>();
        try {
            Collection col = getOrCreateCollection(this.collectionPath);
            XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpathService.setProperty("indent", "yes");

            xpathService.setNamespace("", ODGOVORI_NAMESPACE_PATH);
            System.out.println("[INFO] Invoking XPath query service for: " + xpathExp);
            ResourceSet result = xpathService.query(xpathExp);

            // handle the results
            System.out.println("[INFO] Handling the results... ");

            ResourceIterator i = result.getIterator();
            Resource res = null;
            JAXBContext context = JAXBContext.newInstance(PACKAGE_PATH_ODGOVORI);
            Unmarshaller u = context.createUnmarshaller();
            while(i.hasMoreResources()) {
                try {
                    res = i.nextResource();
                    System.out.println(res.getContent());
                    String str = res.getContent().toString();
                    Odgovori.Odgovor v = (Odgovori.Odgovor) u.unmarshal(new StreamSource(new StringReader(str)));
                    System.out.println(v);
                    odgovori.add(v);
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
        return odgovori;
    }

    public void dodajOdgovor(OdgovorTerminDTO odgovor) {
        String contextXPath = "//Odgovori";
        try {
            Collection col = getOrCreateCollection(this.collectionPath);
            XUpdateQueryService xupdateService = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            xupdateService.setProperty("indent", "yes");
            StringBuilder sb = new StringBuilder();
            for (String vak : odgovor.getVakcine())
                sb.append(String.format("<Vakcina>%s</Vakcina>\n",vak));
            String xmlFragment = String.format(
                            "   <Odgovor Indikator=\"%s\">\n" +
                            "        %s" +
                            "        <Grad>%s</Grad>\n" +
                            "        <Termin>%s</Termin>        \n" +
                            "        <Vrednost>%s</Vrednost>\n" +
                            "        <Ustanova>%s</Ustanova>\n" +
                            "        <Dodeljena_vakcina>%s</Dodeljena_vakcina>\n" +
                            "        <Razlog>%s</Razlog>\n" +
                            "        <Email>%s</Email>\n" +
                            "</Odgovor>",
                    odgovor.getIndikator(), sb.toString(),
                    odgovor.getGrad(), odgovor.getTermin(), odgovor.getVrednost(),
                    odgovor.getUstanova(), odgovor.getVakcinaDodeljena(),
                    odgovor.getRazlog(), odgovor.getEmail());

            System.out.println("[INFO] Updating " + contextXPath + " node.");
            System.out.println(String.format(APPEND, contextXPath, xmlFragment));
            long mods = xupdateService.updateResource("odgovori.xml",
                    String.format(APPEND, contextXPath, xmlFragment));
            System.out.println("[INFO] " + mods + " modifications processed.");

        } catch (XMLDBException e) {
            e.printStackTrace();
        }
    }

    public void azurirajOdgovor(OdgovorTerminDTO odgovor) {
        String contextXPath = String.format("//Email[contains(text(),'%s')]/..", odgovor.getEmail());
        try {
            Collection col = getOrCreateCollection(this.collectionPath);
            XUpdateQueryService xupdateService = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            xupdateService.setProperty("indent", "yes");
            StringBuilder sb = new StringBuilder();
            for (String vak : odgovor.getVakcine())
                sb.append(String.format("<Vakcina>%s</Vakcina>\n",vak));
            String xmlFragment = String.format(
                            "        %s" +
                            "        <Grad>%s</Grad>\n" +
                            "        <Termin>%s</Termin>        \n" +
                            "        <Vrednost>%s</Vrednost>\n" +
                            "        <Ustanova>%s</Ustanova>\n" +
                            "        <Dodeljena_vakcina>%s</Dodeljena_vakcina>\n" +
                            "        <Razlog>%s</Razlog>\n" +
                            "        <Email>%s</Email>\n", sb.toString(),
                    odgovor.getGrad(), odgovor.getTermin(), odgovor.getVrednost(),
                    odgovor.getUstanova(), odgovor.getVakcinaDodeljena(),
                    odgovor.getRazlog(), odgovor.getEmail());

            System.out.println("[INFO] Updating " + contextXPath + " node.");
            System.out.println(String.format(UPDATE, contextXPath, xmlFragment));
            long mods = xupdateService.updateResource("odgovori.xml",
                    String.format(UPDATE, contextXPath, xmlFragment));
            System.out.println("[INFO] " + mods + " modifications processed.");

        } catch (XMLDBException e) {
            e.printStackTrace();
        }
    }

    public void izbrisiOdgovor(String email) {
        String contextXPath = String.format("//Email[contains(text(),'%s')]/..", email);
        try {
            Collection col = getOrCreateCollection(this.collectionPath);
            XUpdateQueryService xupdateService = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            xupdateService.setProperty("indent", "yes");

            System.out.println("[INFO] Removing " + contextXPath + " node.");
            long mods = xupdateService.updateResource("odgovori.xml", String.format(REMOVE, contextXPath));
            System.out.println("[INFO] " + mods + " modifications processed.");
        } catch (XMLDBException e) {
            e.printStackTrace();
        }
    }

    public Odgovori.Odgovor vratiOdgovor(String email) {
        String  xpathExp = String.format("doc(\"odgovori.xml\")//Email[contains(text(),'%s')]/..", email);

        try {
            Collection col = getOrCreateCollection(this.collectionPath);
            XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpathService.setProperty("indent", "yes");

            xpathService.setNamespace("", ODGOVORI_NAMESPACE_PATH);
            System.out.println("[INFO] Invoking XPath query service for: " + xpathExp);
            ResourceSet result = xpathService.query(xpathExp);

            ResourceIterator i = result.getIterator();
            Resource res = null;
            JAXBContext context = JAXBContext.newInstance(PACKAGE_PATH_ODGOVORI);
            Unmarshaller u = context.createUnmarshaller();
            res = i.nextResource();
            if (res == null) // ako je prazan upit
                return null;
            System.out.println(res.getContent());
            String str = res.getContent().toString();
            Odgovori.Odgovor odgovor = (Odgovori.Odgovor) u.unmarshal(new StreamSource(new StringReader(str)));
            System.out.println(odgovor);
            return odgovor;

        } catch (XMLDBException | JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }
}
