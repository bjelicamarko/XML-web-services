package com.imunizacija.ImunizacijaApp.model.dto.rdf_dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

//<http://www.vakc-sistem.rs/predicate/issued>
//<http://www.vakc-sistem.rs/interesovanje/2312312> ;

@XmlRootElement(name = "documentsOfUser")
public class DocumentsOfUserDTO {
    private String interesovanjeID;
    private List<String> salgasnostList;
    private List<String> zahtevDZSList;
    private List<String> prihvaceniZahtevDZSList;
    private List<String> potvrdaOVakcList;

    public DocumentsOfUserDTO(){}

    public DocumentsOfUserDTO(String interesovanjeID, List<String> salgasnostList, List<String> zahtevDZSList,
                              List<String> potvrdaOVakcList, List<String> prihvaceniZahtevDZSList) {
        this.interesovanjeID = interesovanjeID;
        this.salgasnostList = salgasnostList;
        this.zahtevDZSList = zahtevDZSList;
        this.potvrdaOVakcList = potvrdaOVakcList;
        this.prihvaceniZahtevDZSList = prihvaceniZahtevDZSList;
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

    public List<String> getPrihvaceniZahtevDZSList() {
        return prihvaceniZahtevDZSList;
    }

    public void setPrihvaceniZahtevDZSList(List<String> prihvaceniZahtevDZSList) {
        this.prihvaceniZahtevDZSList = prihvaceniZahtevDZSList;
    }
}
