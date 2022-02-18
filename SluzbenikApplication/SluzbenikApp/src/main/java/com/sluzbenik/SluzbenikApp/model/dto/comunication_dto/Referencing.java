package com.sluzbenik.SluzbenikApp.model.dto.comunication_dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Referencing")
public class Referencing {

    @XmlElement(name = "Document_id", required = true)
    private List<String> documentIds;

    public Referencing(){
        this.documentIds = new ArrayList<>();
    }

    public Referencing(List<String> documentIds) {
        this.documentIds = documentIds;
    }

    public List<String> getDocumentIds() {
        return documentIds;
    }

    public void setDocumentIds(List<String> documentIds) {
        this.documentIds = documentIds;
    }

    public void add(String id) {
        this.documentIds.add(id);
    }
}
