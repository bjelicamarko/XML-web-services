package com.sluzbenik.SluzbenikApp.model.dto.termini_dto;


import javax.xml.bind.annotation.*;
import java.math.BigInteger;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "value"
})
@XmlRootElement(name = "Vakcina")
public class VakcinaDTO {

    @XmlAttribute(name = "Naziv_proizvodjaca", required = true)
    protected String nazivProizvodjaca;

    @XmlValue
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger value;

    public VakcinaDTO() {}

    public String getNazivProizvodjaca() {
        return nazivProizvodjaca;
    }

    public void setNazivProizvodjaca(String nazivProizvodjaca) {
        this.nazivProizvodjaca = nazivProizvodjaca;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "VakcinaDTO{" +
                "nazivProizvodjaca='" + nazivProizvodjaca + '\'' +
                ", kolicina=" + value +
                '}';
    }
}
