package com.imunizacija.ImunizacijaApp.model.dto.comunication_dto;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "vakcine",
        "grad",
        "termin",
        "vrednost",
        "ustanova",
        "vakcinaDodeljena",
        "razlog",
        "email"
})
@XmlRootElement(name = "Odgovor")
public class OdgovorTerminDTO {

    @XmlAttribute(name = "indikator", required = true) // Da ili Ne
    private String indikator;

    @XmlElement(name = "vakcina", required = true)
    private List<String> vakcine;

    @XmlElement(name = "grad")
    private String grad;

    @XmlElement(name = "termin")
    private String termin;

    @XmlElement(name = "vrednost")
    private int vrednost;

    @XmlElement(name = "ustanova")
    private String ustanova;

    @XmlElement(name = "dodeljena-vakcina")
    private String vakcinaDodeljena;

    @XmlElement(name = "razlog")
    private String razlog;

    @XmlElement(name = "email")
    private String email;

    public OdgovorTerminDTO() {
        this.vakcine = new ArrayList<>();
        this.indikator = "Ne";
        this.termin = "Empty";
    }

    @Override
    public String toString() {
        return "OdgovorTerminDTO{" +
                "indikator='" + indikator + '\'' +
                ", vakcine=" + vakcine +
                ", grad='" + grad + '\'' +
                ", termin='" + termin + '\'' +
                ", vrednost=" + vrednost +
                ", ustanova='" + ustanova + '\'' +
                ", vakcinaDodeljena='" + vakcinaDodeljena + '\'' +
                ", razlog='" + razlog + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getIndikator() {
        return indikator;
    }

    public void setIndikator(String indikator) {
        this.indikator = indikator;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getTermin() {
        return termin;
    }

    public void setTermin(String termin) {
        this.termin = termin;
    }

    public int getVrednost() {
        return vrednost;
    }

    public void setVrednost(int vrednost) {
        this.vrednost = vrednost;
    }

    public String getUstanova() {
        return ustanova;
    }

    public void setUstanova(String ustanova) {
        this.ustanova = ustanova;
    }

    public List<String> getVakcine() {
        return vakcine;
    }

    public void setVakcine(List<String> vakcine) {
        this.vakcine = vakcine;
    }

    public String getVakcinaDodeljena() {
        return vakcinaDodeljena;
    }

    public void setVakcinaDodeljena(String vakcinaDodeljena) {
        this.vakcinaDodeljena = vakcinaDodeljena;
    }

    public String getRazlog() {
        return razlog;
    }

    public void setRazlog(String razlog) {
        this.razlog = razlog;
    }

}
