package com.imunizacija.ImunizacijaApp.repository;


import com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje.Interesovanje;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class InteresovanjeRepository extends StoreRetrieveXMLRepository {

    public void storeXML(Interesovanje interesovanje){
        OutputStream os = new ByteArrayOutputStream();

        try {
            JAXBContext context = JAXBContext.newInstance("com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje");

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(interesovanje, os);

            super.storeXML("db/interesovanje", interesovanje.getId() +".xml", os);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public Interesovanje retrieveXML(String documentId){
        Interesovanje interesovanje = null;
        try {
            JAXBContext context = JAXBContext.newInstance("com.imunizacija.ImunizacijaApp.model.vakc_sistem.interesovanje");

            Unmarshaller unmarshaller = context.createUnmarshaller();

            interesovanje = (Interesovanje) unmarshaller.unmarshal(
                    super.retrieveXML("db/interesovanje", documentId));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return interesovanje;
    }
}
