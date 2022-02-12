package com.imunizacija.ImunizacijaApp.repository.xmlRepository;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.dummy.Dummy;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@Component
public class TerminRepository extends GenericXMLRepository<Dummy>{

    public TerminRepository() {
        this.idGenerator = new IdGeneratorPosInt();
        this.packagePath = "";
        this.collectionPath = COLLECTION_PATH_TERMIN;
    }

    public void loadXmlTermini() {
        try {
            Collection col = getOrCreateCollection(this.collectionPath);
            System.out.println("[INFO] Retrieving the collection: " + this.collectionPath);
            XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpathService.setProperty("indent", "yes");
            xpathService.setNamespace("",TERMIN_NAMESPACE_PATH);

            String xpathExp = String.format("for $i in doc(\"%s\")//grad[@ime=\"%s\"]/ustanova return $i",
                    "/db/termini/termini.xml", "Novi Sad");

            System.out.println("[INFO] Invoking XPath query service for: " + xpathExp);
            ResourceSet result = xpathService.query(xpathExp);
            System.out.println(result.getSize());
            ResourceIterator i = result.getIterator();
            Resource res = null;

            while (i.hasMoreResources()) {
                try {
                    res = i.nextResource();
                    res.getContent();
                    String response = (String) res.getContent();
                    System.out.println("Ret value is: " + response);

                } catch(XMLDBException e){
                    System.out.println("Error with query for user!");
                } catch(NumberFormatException e){
                    System.out.println("Error with query for user, query returned non int value!");
                } finally {
                    try {
                        if (res != null)
                            ((EXistResource)res).freeResources();
                    } catch (XMLDBException xe) {
                        xe.printStackTrace();
                    }
                }
            }
        } catch (XMLDBException e) {
            e.printStackTrace();
        }

    }
}
