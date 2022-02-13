package com.sluzbenik.SluzbenikApp.repository.xmlRepository;

import com.sluzbenik.SluzbenikApp.dto.GradVakcinaKolicinaDTO;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.termini.SistemskiMagacin;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XUpdateQueryService;


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
        ///Vakcina[@Naziv_proizvodjaca=\"%s\"]"
        String contextXPath = String.format("//Grad[@Ime='%s']/Vakcine/Vakcina[@Naziv_proizvodjaca='%s']",
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
}
