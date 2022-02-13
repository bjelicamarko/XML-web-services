package com.sluzbenik.SluzbenikApp.dto;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "grad",
        "nazivProizvodjaca",
        "kolicina",
})
@XmlRootElement(name = "GradVakcinaKolicina")
public class GradVakcinaKolicinaDTO {

    @XmlElement(name = "grad")
    private String grad;
    @XmlElement(name = "nazivProizvodjaca")
    private String nazivProizvodjaca;
    @XmlElement(name = "kolicina")
    private int kolicina;

    public GradVakcinaKolicinaDTO() {}

    public GradVakcinaKolicinaDTO(String grad, String nazivProizvodjaca, int kolicina) {
        this.grad = grad;
        this.nazivProizvodjaca = nazivProizvodjaca;
        this.kolicina = kolicina;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getNazivProizvodjaca() {
        return nazivProizvodjaca;
    }

    public void setNazivProizvodjaca(String nazivProizvodjaca) {
        this.nazivProizvodjaca = nazivProizvodjaca;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }
}
