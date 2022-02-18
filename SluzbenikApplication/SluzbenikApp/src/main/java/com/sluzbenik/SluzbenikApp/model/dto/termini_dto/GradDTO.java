package com.sluzbenik.SluzbenikApp.model.dto.termini_dto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "vakcine",
        "ustanove"
})
@XmlRootElement(name = "Grad")
public class GradDTO {

    @XmlAttribute(name = "Ime", required = true)
    protected String ime;

    @XmlElement(name = "Vakcina")
    private List<VakcinaDTO> vakcine;

    @XmlElement(name = "Ustanova")
    private List<UstanovaDTO> ustanove;

    public GradDTO() {}

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public List<VakcinaDTO> getVakcine() {
        return vakcine;
    }

    public void setVakcine(List<VakcinaDTO> vakcine) {
        this.vakcine = vakcine;
    }

    public List<UstanovaDTO> getUstanove() {
        return ustanove;
    }

    public void setUstanove(List<UstanovaDTO> ustanove) {
        this.ustanove = ustanove;
    }

    @Override
    public String toString() {
        return "GradDTO{" +
                "ime='" + ime + '\'' +
                ", vakcine=" + vakcine +
                ", ustanove=" + ustanove +
                '}';
    }
}
