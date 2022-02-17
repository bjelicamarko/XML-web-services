package com.imunizacija.ImunizacijaApp.repository.xmlRepository;

import com.imunizacija.ImunizacijaApp.model.dto.comunication_dto.SearchResults;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.IdentifiableEntity;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import org.exist.xmldb.EXistResource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Locale;

import static com.imunizacija.ImunizacijaApp.repository.Constants.*;
import static com.imunizacija.ImunizacijaApp.repository.Constants.UTIL_NAMESPACE_PATH;

@Component
@Scope("prototype") // kreira novu instancu na svaki @Autowired
public class GenericXMLRepository<T extends IdentifiableEntity> extends StoreRetrieveXMLRepository {

    public String packagePath;
    public String collectionPath;
    public IdGeneratorPosInt idGenerator;
    public String namespacePath;

    public GenericXMLRepository() {}

    public void setRepositoryParams(String packagePath, String collectionPath, IdGeneratorPosInt idGenerator, String namespacePath){
        this.idGenerator = idGenerator;
        this.packagePath = packagePath;
        this.collectionPath = collectionPath;
        this.namespacePath = namespacePath;
    }

    public String storeXML(T entity, boolean generateId){
        OutputStream os = new ByteArrayOutputStream();
        Collection col = null;
        XMLResource res = null;

        try {
            System.out.println("[INFO] Retrieving the collection: " + collectionPath);
            col = getOrCreateCollection(collectionPath);

            if (generateId){
                entity.setXmlId(idGenerator.generateId(col.listResources()));
            }

            JAXBContext context = JAXBContext.newInstance(packagePath);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(entity, os);

            String documentId = entity.getXmlId() + ".xml";
            System.out.println("[INFO] Inserting the document: " + documentId);
            res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);

            res.setContent(os);
            System.out.println("[INFO] Storing the document: " + res.getId());

            col.storeResource(res);
            System.out.println("[INFO] Done.");
            return documentId;
        } catch(JAXBException | XMLDBException e) {
            e.printStackTrace();
            return "-1";
        } finally {
            closeResources(col, res);
        }
    }

    public T retrieveXML(String documentId){
        T entity = null;
        try {
            JAXBContext context = JAXBContext.newInstance(packagePath);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            //noinspection unchecked
            entity = (T) unmarshaller.unmarshal(super.retrieveXML(collectionPath, documentId));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public Node retrieveXMLAsDOMNode(String documentId){
        return super.retrieveXML(collectionPath, documentId);
    }

    public SearchResults searchDocuments(String userId, String searchText) throws XMLDBException {
        searchText = searchText.toLowerCase(Locale.ROOT);
        String andor = calcAndor(userId, searchText);

        Collection col = getOrCreateCollection(this.collectionPath);
        XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
        xpathService.setProperty("indent", "yes");

        xpathService.setNamespace("", this.namespacePath);
        xpathService.setNamespace("util", UTIL_NAMESPACE_PATH);

        String collPath = "/" + this.collectionPath;
        String xpathExp = String.format("\ndeclare variable $data as document-node()* := collection(\"%s\");\n" +
                "\n" +
                "for $doc in $data\n" +
                "let $email := $doc//util:Email_adresa\n" +
                "let $jmbg := $doc//util:JMBG\n" +
                "let $br_pasosa := $doc//util:Br_pasosa\n" +
                "let $br_stranca := $doc//util:Evidencioni_broj_stranca\n" +
                "let $id := $doc//@Id\n" +
                "let $vals := $doc//text()[contains(lower-case(.),'%s')]\n" +
                "let $attrs := $doc//*[contains(lower-case(@*),'%s')]/@*\n" +

                "let $ret := if ( " +
                "(count($vals) > 0 or count($attrs) > 0) " +
                "%s " +
                "( ($email = '%s') or ($jmbg = '%s') or ($br_pasosa = '%s') or ($br_stranca = '%s') ) " +
                ")" +
                "" +
                "then $id else ()\n" +

                "for $r in $ret\n" +
                "return string($r)" +
                "", collPath, searchText, searchText, andor, userId, userId, userId, userId);

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

                searchResults.getSearchResults().add(response /* + ".xml"*/);

            } catch(XMLDBException e){
                System.out.println("Error with query for consent!");
                e.printStackTrace();
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

    private String calcAndor(String userId, String searchText) {
        // Oba puna -- userId pun -- searchText pun -- oba prazna
        //   and           and           or                or
        if(!userId.equals("") && !searchText.equals("")) return "and";
        if(!userId.equals("") && searchText.equals("")) return "and";
        if(userId.equals("") && searchText.equals("")) return "or";
        else return "or"; // oba prazna
    }



}
