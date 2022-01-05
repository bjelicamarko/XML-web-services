package com.sluzbenik.SluzbenikApp.repository;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import org.exist.xmldb.EXistResource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.base.Collection;

import javax.xml.bind.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

public class DigitalniZeleniSertifikatRepository extends StoreRetrieveXMLRepository {

    public void storeXML(DigitalniZeleniSertifikat digitalniZeleniSertifikat){
        Collection col = null;
        XMLResource res = null;
        OutputStream os = new ByteArrayOutputStream();

        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance("com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat");

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

//            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//            dbf.setNamespaceAware(true);
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            Document doc = db.newDocument();

            // marshal the contents to an output stream
//            marshaller.marshal(digitalniZeleniSertifikat, doc);
            marshaller.marshal(digitalniZeleniSertifikat, os);

//            super.storeXML("db/digitalni_zeleni_sertifikat", digitalniZeleniSertifikat.getPodaciOSertifikatu().getBrojSertifikata(), doc);
            super.storeXML("db/digitalni_zeleni_sertifikat", digitalniZeleniSertifikat.getPodaciOSertifikatu().getBrojSertifikata()+".xml", os);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public DigitalniZeleniSertifikat retrieveXML(String documentId){
        DigitalniZeleniSertifikat digitalniZeleniSertifikat = null;
        try {
            JAXBContext context = JAXBContext.newInstance("com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat");

            Unmarshaller unmarshaller = context.createUnmarshaller();

            digitalniZeleniSertifikat = (DigitalniZeleniSertifikat) unmarshaller.unmarshal(
                    super.retrieveXML("db/digitalni_zeleni_sertifikat", documentId));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return digitalniZeleniSertifikat;
    }
}
