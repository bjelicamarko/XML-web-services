package com.sluzbenik.SluzbenikApp.model.dto.termini_dto;

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

    public GradDTO createGradDTO() { return new GradDTO(); }


}
