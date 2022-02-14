package com.sluzbenik.SluzbenikApp.model.dto.comunication_dto;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "grad",
        "termin",
        "vrednost",
        "ustanova",
        "vakcina",
        "razlog",
})
@XmlRootElement(name = "Odgovor")
public class OdgovorTerminDTO {

    @XmlElement(name = "grad")
    private String grad;

    @XmlElement(name = "termin")
    private String termin;

    @XmlElement(name = "vrednost")
    private int vrednost;

    @XmlElement(name = "ustanova")
    private String ustanova;

    @XmlElement(name = "vakcina")
    private String vakcina;

    @XmlElement(name = "razlog")
    private String razlog;

    public OdgovorTerminDTO() {}

    @Override
    public String toString() {
        return "OdgovorTerminDTO{" +
                "grad='" + grad + '\'' +
                ", termin='" + termin + '\'' +
                ", vrednost=" + vrednost +
                ", ustanova='" + ustanova + '\'' +
                ", vakcina='" + vakcina + '\'' +
                ", razlog='" + razlog + '\'' +
                '}';
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

    public String getVakcina() {
        return vakcina;
    }

    public void setVakcina(String vakcina) {
        this.vakcina = vakcina;
    }

    public String getRazlog() {
        return razlog;
    }

    public void setRazlog(String razlog) {
        this.razlog = razlog;
    }
}
