package com.imunizacija.ImunizacijaApp.model.dto.rdf_dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "documentsOfUser")
public class DocumentsOfUserWithDZSDTO {
    private String interesovanjeID;
    private List<String> salgasnostList;
    private List<String> zahtevDZSList;
    private List<String> prihvaceniZahtevDZSList;
    private List<String> potvrdaOVakcList;
    private List<String> dzsList;

    public DocumentsOfUserWithDZSDTO() {}

    public DocumentsOfUserWithDZSDTO(DocumentsOfUserDTO documentsOfUserDTO){
        this.interesovanjeID = documentsOfUserDTO.getInteresovanjeID();
        this.salgasnostList = documentsOfUserDTO.getSalgasnostList();
        this.zahtevDZSList = documentsOfUserDTO.getZahtevDZSList();
        this.prihvaceniZahtevDZSList = documentsOfUserDTO.getPrihvaceniZahtevDZSList();
        this.potvrdaOVakcList = documentsOfUserDTO.getPotvrdaOVakcList();
    }

    public DocumentsOfUserWithDZSDTO(String interesovanjeID, List<String> salgasnostList,
                                     List<String> zahtevDZSList, List<String> prihvaceniZahtevDZSList, List<String> potvrdaOVakcList, List<String> dzsList) {
        this.interesovanjeID = interesovanjeID;
        this.salgasnostList = salgasnostList;
        this.zahtevDZSList = zahtevDZSList;
        this.prihvaceniZahtevDZSList = prihvaceniZahtevDZSList;
        this.potvrdaOVakcList = potvrdaOVakcList;
        this.dzsList = dzsList;
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

    public List<String> getPrihvaceniZahtevDZSList() {
        return prihvaceniZahtevDZSList;
    }

    public void setPrihvaceniZahtevDZSList(List<String> prihvaceniZahtevDZSList) {
        this.prihvaceniZahtevDZSList = prihvaceniZahtevDZSList;
    }

    public List<String> getPotvrdaOVakcList() {
        return potvrdaOVakcList;
    }

    public void setPotvrdaOVakcList(List<String> potvrdaOVakcList) {
        this.potvrdaOVakcList = potvrdaOVakcList;
    }

    public List<String> getDzsList() {
        return dzsList;
    }

    public void setDzsList(List<String> dzsList) {
        this.dzsList = dzsList;
    }
}
