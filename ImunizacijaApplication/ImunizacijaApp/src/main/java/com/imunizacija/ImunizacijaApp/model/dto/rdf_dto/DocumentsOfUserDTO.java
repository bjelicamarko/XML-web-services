package com.imunizacija.ImunizacijaApp.model.dto.rdf_dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "documents-of-user")
public class DocumentsOfUserDTO {
    private String interesovanjeID;
    private List<String> salgasnostList;
    private List<String> zahtevDZSList;
    private List<String> potvrdaOVakcList;

    public DocumentsOfUserDTO(){}

    public DocumentsOfUserDTO(String interesovanjeID, List<String> salgasnostList, List<String> zahtevDZSList, List<String> potvrdaOVakcList) {
        this.interesovanjeID = interesovanjeID;
        this.salgasnostList = salgasnostList;
        this.zahtevDZSList = zahtevDZSList;
        this.potvrdaOVakcList = potvrdaOVakcList;
    }

    public String getInteresovanjeID() {
        return interesovanjeID;
    }

    public void setInteresovanjeID(String interesovanjeID) {
        this.interesovanjeID = interesovanjeID;
    }

    public List<String> getSalgasnostList() {
        return salgasnostList;
    }

    public void setSalgasnostList(List<String> salgasnostList) {
        this.salgasnostList = salgasnostList;
    }

    public List<String> getZahtevDZSList() {
        return zahtevDZSList;
    }

    public void setZahtevDZSList(List<String> zahtevDZSList) {
        this.zahtevDZSList = zahtevDZSList;
    }

    public List<String> getPotvrdaOVakcList() {
        return potvrdaOVakcList;
    }

    public void setPotvrdaOVakcList(List<String> potvrdaOVakcList) {
        this.potvrdaOVakcList = potvrdaOVakcList;
    }
}
