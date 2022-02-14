package com.imunizacija.ImunizacijaApp.repository.xmlRepository;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.IdentifiableEntity;
import com.imunizacija.ImunizacijaApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

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

import static com.imunizacija.ImunizacijaApp.repository.Constants.COLLECTION_PATH_INTERESOVANJE;
import static com.imunizacija.ImunizacijaApp.repository.Constants.PACKAGE_PATH_INTERESOVANJE;

@Component
@Scope("prototype") // kreira novu instancu na svaki @Autowired
public class GenericXMLRepository<T extends IdentifiableEntity> extends StoreRetrieveXMLRepository {

    public String packagePath;
    public String collectionPath;
    public IdGeneratorPosInt idGenerator;

    public GenericXMLRepository() {}

    public void setRepositoryParams(String packagePath, String collectionPath, IdGeneratorPosInt idGenerator){
        this.idGenerator = idGenerator;
        this.packagePath = packagePath;
        this.collectionPath = collectionPath;
    }

    public void storeXML(T entity, boolean generateId){
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
        } catch(JAXBException | XMLDBException e) {
            e.printStackTrace();
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
}
