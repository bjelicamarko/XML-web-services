package com.imunizacija.ImunizacijaApp.repository.xmlRepository;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.odgovori.Odgovori;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.zahtev_dzs.Zahtev;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XQueryService;
import org.xmldb.api.modules.XUpdateQueryService;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;
import static com.imunizacija.ImunizacijaApp.template.XUpdateTemplate.REMOVE;

@Component
public class ZahteviRepository extends StoreRetrieveXMLRepository{

    public String packagePath;
    public String collectionPath;

    public ZahteviRepository() {
        this.packagePath = PACKAGE_PATH_ZAHTEV_DZS;
        this.collectionPath = COLLECTION_PATH_ZAHTEV_DZS;
    }

    public void deleteZahtev(String id) {
        try {
            Collection col = getOrCreateCollection(this.collectionPath);
            XQueryService xupdateService = (XQueryService) col.getService("XQueryService", "1.0");
            xupdateService.setProperty("indent", "yes");

            System.out.println("Deleting: " + id);
            String xpathExp = "xmldb:remove(\"/db/zahtev_dzs/\", \"" + id+ ".xml\")";
            xupdateService.query(xpathExp);
            System.out.println("Deleted: " + id);
        } catch (XMLDBException e) {
            e.printStackTrace();
        }
    }
}
