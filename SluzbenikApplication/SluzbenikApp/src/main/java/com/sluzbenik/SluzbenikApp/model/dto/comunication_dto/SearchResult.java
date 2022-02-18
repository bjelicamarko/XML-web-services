package com.sluzbenik.SluzbenikApp.model.dto.comunication_dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Search_result")
public class SearchResult {

    @XmlElement(name = "Document_id", required = true)
    private String documentId;

    @XmlElement(name = "Referencing", required = true)
    private Referencing referencing;

    @XmlElement(name = "Referenced_by", required = true)
    private ReferencedBy referencedBy;

    public SearchResult() {
        this.referencing = new Referencing();
        this.referencedBy = new ReferencedBy();
    }

    public SearchResult(String documentId, Referencing referencing, ReferencedBy referencedBy) {
        this.documentId = documentId;
        this.referencing = referencing;
        this.referencedBy = referencedBy;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Referencing getReferencing() {
        return referencing;
    }

    public void setReferencing(Referencing referencing) {
        this.referencing = referencing;
    }

    public void addReference(String id){
        this.referencing.add(id);
    }

    public ReferencedBy getReferencedBy() {
        return referencedBy;
    }

    public void setReferencedBy(ReferencedBy referencedBy) {
        this.referencedBy = referencedBy;
    }

    public void addReferenceBy(String id){
        this.referencedBy.add(id);
    }
}
