package com.sluzbenik.SluzbenikApp.repository;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;
import com.sluzbenik.SluzbenikApp.model.vakc_sistem.izvestaj.Izvestaj;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class IzvestajRepository extends StoreRetrieveXMLRepository {

    public void storeXML(Izvestaj izvestaj){
        OutputStream os = new ByteArrayOutputStream();
        try {
            JAXBContext context = JAXBContext.newInstance("com.sluzbenik.SluzbenikApp.model.vakc_sistem.izvestaj");

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(izvestaj, os);

            super.storeXML("db/izvestaj", izvestaj.getId() + ".xml", os);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public Izvestaj retrieveXML(String documentId){
        Izvestaj izvestaj = null;
        try {
            JAXBContext context = JAXBContext.newInstance("com.sluzbenik.SluzbenikApp.model.vakc_sistem.izvestaj");

            Unmarshaller unmarshaller = context.createUnmarshaller();

            izvestaj = (Izvestaj) unmarshaller.unmarshal(
                    super.retrieveXML("db/izvestaj", documentId));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return izvestaj;
    }
}
