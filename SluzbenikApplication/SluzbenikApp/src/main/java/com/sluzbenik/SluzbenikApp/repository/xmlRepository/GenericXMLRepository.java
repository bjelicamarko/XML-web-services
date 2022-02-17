package com.sluzbenik.SluzbenikApp.repository.xmlRepository;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.IdentifiableEntity;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.id_generator.IdGeneratorInterface;
import com.sluzbenik.SluzbenikApp.repository.xmlRepository.id_generator.IdGeneratorPosInt;
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
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Component
@Scope("prototype") // kreira novu instancu na svaki @Autowired
public class GenericXMLRepository<T extends IdentifiableEntity> extends StoreRetrieveXMLRepository {

    protected String packagePath;
    protected String collectionPath;
    protected IdGeneratorPosInt idGenerator;

    public GenericXMLRepository (){
    }

    public void setRepositoryParams(String packagePath, String collectionPath, IdGeneratorPosInt idGenerator) {
        this.idGenerator = idGenerator;
        this.collectionPath = collectionPath;
        this.packagePath = packagePath;
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

            return entity.getXmlId();
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

}
