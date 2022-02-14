package com.sluzbenik.SluzbenikApp.model.dto.termini_dto;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "termin"
})
@XmlRootElement(name = "Ustanova")
public class UstanovaDTO {

    @XmlAttribute(name = "naziv", required = true)
    protected String naziv;

    @XmlElement(name = "Termin")
    private TerminDTO termin;

    public UstanovaDTO() {}

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public TerminDTO getTermin() {
        return termin;
    }

    public void setTermin(TerminDTO termin) {
        this.termin = termin;
    }

    @Override
    public String toString() {
        return "UstanovaDTO{" +
                "naziv='" + naziv + '\'' +
                ", termin=" + termin +
                '}';
    }
}
