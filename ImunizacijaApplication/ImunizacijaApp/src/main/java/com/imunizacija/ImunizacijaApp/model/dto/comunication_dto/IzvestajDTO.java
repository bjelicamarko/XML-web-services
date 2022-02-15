package com.imunizacija.ImunizacijaApp.model.dto.comunication_dto;

import javax.xml.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "brojInteresovanja",
        "brojZahteva",
        "noveVakcine",
        "stanje",
        "brojZelenih"
})
@XmlRootElement(name = "Izvestaj")
public class IzvestajDTO {

    @XmlElement(name = "broj_interesovanja")
    private int brojInteresovanja;

    @XmlElement(name = "broj_zahteva")
    private int brojZahteva;

    @XmlElement(name = "nove_vakcine")
    private int noveVakcine;

    @XmlElement(name = "stanje")
    private Map<String, Integer> stanje;

    @XmlElement(name = "broj_zelenih")
    private int brojZelenih;

    public IzvestajDTO() {
        this.stanje = new HashMap<>();
    }

    @Override
    public String toString() {
        return "IzvestajDTO{" +
                "brojInteresovanja=" + brojInteresovanja +
                ", brojZahteva=" + brojZahteva +
                ", noveVakcine=" + noveVakcine +
                ", stanje=" + stanje +
                ", brojZelenih=" + brojZelenih +
                '}';
    }

    public int getBrojInteresovanja() {
        return brojInteresovanja;
    }

    public void setBrojInteresovanja(int brojInteresovanja) {
        this.brojInteresovanja = brojInteresovanja;
    }

    public int getBrojZahteva() {
        return brojZahteva;
    }

    public void setBrojZahteva(int brojZahteva) {
        this.brojZahteva = brojZahteva;
    }

    public int getNoveVakcine() {
        return noveVakcine;
    }

    public void setNoveVakcine(int noveVakcine) {
        this.noveVakcine = noveVakcine;
    }

    public Map<String, Integer> getStanje() {
        return stanje;
    }

    public void setStanje(Map<String, Integer> stanje) {
        this.stanje = stanje;
    }

    public int getBrojZelenih() {
        return brojZelenih;
    }

    public void setBrojZelenih(int brojZelenih) {
        this.brojZelenih = brojZelenih;
    }
}
