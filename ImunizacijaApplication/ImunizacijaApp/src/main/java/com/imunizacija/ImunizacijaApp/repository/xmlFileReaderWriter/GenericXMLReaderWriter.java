package com.imunizacija.ImunizacijaApp.repository.xmlFileReaderWriter;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.IdentifiableEntity;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;
import com.imunizacija.ImunizacijaApp.model.vakc_sistem.zahtev_dzs.Zahtev;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.StringReader;

import static com.imunizacija.ImunizacijaApp.repository.Constants.PACKAGE_PATH_INTERESOVANJE;
import static com.imunizacija.ImunizacijaApp.repository.Constants.XML_SCHEMA_PATH_INTERESOVANJE;

@Component
@Scope("prototype") // kreira novu instancu na svaki @Autowired
public class GenericXMLReaderWriter<T extends IdentifiableEntity> {

    private String classPackage;
    private String schemaLocation;

    public GenericXMLReaderWriter(){
    }

    public void setRepositoryParams(String classPackage, String schemaLocation){
        this.classPackage = classPackage;
        this.schemaLocation = schemaLocation;
    }

    public T checkSchema(String document) {
        try {
            JAXBContext context = JAXBContext.newInstance(classPackage);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(schemaLocation));

            // Podešavanje unmarshaller-a za XML schema validaciju
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new XMLSchemaValidationHandler());

            //noinspection unchecked
            return (T) unmarshaller.unmarshal
                    (new StreamSource( new StringReader(document) ));
        } catch (JAXBException | SAXException e) {
            e.printStackTrace();
            return null;
        }
    }

    public T readFromXml(String path){
        File xmlFile = new File(path);
        try {
            JAXBContext context = JAXBContext.newInstance(classPackage);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(schemaLocation));

            // Podešavanje unmarshaller-a za XML schema validaciju
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new XMLSchemaValidationHandler());

            //noinspection unchecked
            return (T) unmarshaller.unmarshal(xmlFile);

        } catch (JAXBException | SAXException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeToXml(T obj, String baseFilePath){
        try {
            // Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
            JAXBContext context = JAXBContext.newInstance(classPackage);

            // Marshaller je objekat zadužen za konverziju iz objektnog u XML model
            Marshaller marshaller = context.createMarshaller();

            // Podešavanje marshaller-a
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            String folderPath = baseFilePath + obj.getClass().getSimpleName();
            boolean mkdirs = new File(folderPath).mkdirs();
            // Umesto System.out-a, može se koristiti FileOutputStream
            marshaller.marshal(obj, new File( folderPath + "/" + obj.getXmlId() + ".xml"));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
