package com.imunizacija.ImunizacijaApp.repository.xmlRepository;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.saglasnost_za_imunizaciju.Saglasnost;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.SearchResults;
import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Component;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;

import java.util.Locale;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;

@Component
public class SaglasnostRepository extends GenericXMLRepository<Saglasnost> {

    public SaglasnostRepository(){
        this.idGenerator = new IdGeneratorPosInt();
        this.packagePath = PACKAGE_PATH_SAGLASNOST;
        this.collectionPath = COLLECTION_PATH_SAGLASNOST;
    }

    public SearchResults searchUserDocumentsJMBG(String jmbg, String searchText) throws XMLDBException {
        searchText = searchText.toLowerCase(Locale.ROOT);

        Collection col = getOrCreateCollection(this.collectionPath);
        XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
        xpathService.setProperty("indent", "yes");

        xpathService.setNamespace("", SAGLASNOST_NAMESPACE_PATH);
        xpathService.setNamespace("util", UTIL_NAMESPACE_PATH);

        String xpathExp = String.format("\ndeclare variable $data as document-node()* := collection(\"/db/saglasnost_za_imunizaciju\");\n" +
                "\n" +
                "for $doc in $data\n" +
                "let $jmbg := $doc//util:JMBG\n" +
                "let $id := $doc//@Id\n" +
                "let $vals := $doc//text()[contains(lower-case(.),'%s')]\n" +
                "let $attrs := $doc//*[contains(lower-case(@*),'%s')]/@*\n" +
                "let $ret := if ( (count($vals) > 0 or count($attrs) > 0) and ($jmbg = '%s')) then $id else ()\n" +
                "for $r in $ret\n" +
                "return string($r)" +
                "", searchText, searchText, jmbg);

        System.out.println("[INFO] Invoking XPath query service for: " + xpathExp);
        ResourceSet result = xpathService.query(xpathExp);

        ResourceIterator i = result.getIterator();
        Resource res = null;

        SearchResults searchResults = new SearchResults();

        while(i.hasMoreResources()) {
            try {
                res = i.nextResource();
                String response = (String) res.getContent();
                System.out.println("Ret value is: " + response);

                searchResults.getSearchResults().add(response + ".xml");

            } catch(XMLDBException e){
                System.out.println("Error with query for consent!");
            } finally {
                try {
                    if (res != null)
                        ((EXistResource)res).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }

        return searchResults;
    }

    public SearchResults searchUserDocumentsBrojPasosa(String brojPasosa, String searchText) throws XMLDBException {
//        XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
//        xpathService.setProperty("indent", "yes");
//
//        xpathService.setNamespace("", SAGLASNOST_NAMESPACE_PATH);
//
//        String xpathExp = String.format("declare variable $data as document-node()* := collection(\"/db/saglasnost_za_imunizaciju\");\n" +
//                "\n" +
//                "" +
//                "let $jmbg := $data//util:JMBG\n" +
//                "for $val at $data//\n" +
//                "let $attrVals := $data//$val/@*\n" +
//                "for $attrVal at $attrVals\n" +
//                "where $jmbg = '%s' and (fn:contains($val, '%s') or fn:contains($attrVal, '%s'))\n" +
//                "return $data/@Id" +
//                "", jmbg, searchText, searchText);
//
//        System.out.println("[INFO] Invoking XPath query service for: " + xpathExp);
//        ResourceSet result = xpathService.query(xpathExp);
//
//        ResourceIterator i = result.getIterator();
//        Resource res = null;
//
//        List<String> ids = new ArrayList<>();
//
//        if(i.hasMoreResources()) {
//            try {
//                res = i.nextResource();
//                String response = (String) res.getContent();
//                System.out.println("Ret value is: " + response);
//                ids.add(response);
//            } catch(XMLDBException e){
//                System.out.println("Error with query for consent!");
//            } finally {
//                try {
//                    if (res != null)
//                        ((EXistResource)res).freeResources();
//                } catch (XMLDBException xe) {
//                    xe.printStackTrace();
//                }
//            }
//        }
//
        return null;
    }

}
