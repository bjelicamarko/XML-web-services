package com.sluzbenik.SluzbenikApp.dto;

import com.sluzbenik.SluzbenikApp.model.vakc_sistem.digitalni_zeleni_sertifikat.DigitalniZeleniSertifikat;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rs.vakc_sistem.digitalni_zeleni_sertifikat
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link VakcinaDTO }
     *
     */
    public VakcinaDTO createVakcinaDTO() {
        return new VakcinaDTO();
    }


}
