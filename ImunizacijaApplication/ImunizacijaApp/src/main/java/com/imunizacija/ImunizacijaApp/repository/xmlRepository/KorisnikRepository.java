package com.imunizacija.ImunizacijaApp.repository.xmlRepository;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.korisnik.Korisnik;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import org.exist.xmldb.EXistResource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@Component
//@Scope("prototype") // kreira novu instancu na svaki @Autowired
public class KorisnikRepository extends GenericXMLRepository<Korisnik>{

    public KorisnikRepository(){
        this.idGenerator = new IdGeneratorPosInt();
        this.packagePath = PACKAGE_PATH_KORISNIK;
        this.collectionPath = COLLECTION_PATH_KORISNIK;
    }

    public boolean checkIfEmailAndIdUnique(Korisnik appUser, Collection col) throws XMLDBException {
        XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
        xpathService.setProperty("indent", "yes");

        xpathService.setNamespace("", KORISNIK_NAMESPACE_PATH);

        String xpathExp = "declare variable $data as document-node()+ := collection(\"/db/korisnik\");\n" +
                "\n" +
                "let $count_id := count($data//KorisnikID[.='" + appUser.getKorisnikID() + "'])\n" +
                "let $count_email := count($data//Email[.='" + appUser.getEmail() + "'])\n" +
                "return \n" +
                "    $count_id + $count_email";

        System.out.println("[INFO] Invoking XPath query service for: " + xpathExp);
        ResourceSet result = xpathService.query(xpathExp);

        ResourceIterator i = result.getIterator();
        Resource res = null;

        boolean retValue = false;

        if(i.hasMoreResources()) {
            try {
                res = i.nextResource();
                String response = (String) res.getContent();
                System.out.println("Ret value is: " + response);
                int respInt = Integer.parseInt(response);
                retValue = respInt == 0;
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
        return retValue;
    }

    public boolean insertUser(Korisnik appUser){
        Collection col = null;
        boolean success = false;
        try {
            System.out.println("[INFO] Retrieving the collection: " + collectionPath);
            col = getOrCreateCollection(collectionPath);
            if (checkIfEmailAndIdUnique(appUser, col)){
                super.storeXML(appUser, false);
                success = true;
                System.out.println("Created user!");
            }else{
                System.out.println("Couldn't create user, user with that email/id already exists!");
            }
        } catch(XMLDBException e) {
            e.printStackTrace();
        } finally {
            if(col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }

        return success;
    }
}
