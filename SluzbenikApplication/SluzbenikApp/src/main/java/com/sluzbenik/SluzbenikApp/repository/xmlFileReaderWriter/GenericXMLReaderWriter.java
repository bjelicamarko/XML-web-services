package com.sluzbenik.SluzbenikApp.repository.xmlFileReaderWriter;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.IdentifiableEntity;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class GenericXMLReaderWriter<T extends IdentifiableEntity> {

    private final String classPackage;
    private final String schemaLocation;

    public GenericXMLReaderWriter(String classPackage, String schemaLocation){
        this.classPackage = classPackage;
        this.schemaLocation = schemaLocation;
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
