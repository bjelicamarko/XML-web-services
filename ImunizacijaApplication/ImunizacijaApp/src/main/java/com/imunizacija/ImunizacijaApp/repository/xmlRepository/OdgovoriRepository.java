package com.imunizacija.ImunizacijaApp.repository.xmlRepository;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.OdgovorTerminDTO;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.odgovori.Odgovori;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XUpdateQueryService;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;
import static com.imunizacija.ImunizacijaApp.template.XUpdateTemplate.APPEND;

@Component
public class OdgovoriRepository extends GenericXMLRepository<Odgovori>{

    public OdgovoriRepository() {
        this.idGenerator = new IdGeneratorPosInt();
        this.packagePath = PACKAGE_PATH_ODGOVORI;
        this.collectionPath = COLLECTION_PATH_ODGOVORI;
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
}
