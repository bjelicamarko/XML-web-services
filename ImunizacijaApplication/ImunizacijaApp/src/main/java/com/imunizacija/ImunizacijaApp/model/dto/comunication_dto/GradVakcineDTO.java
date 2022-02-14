package com.imunizacija.ImunizacijaApp.model.dto.comunication_dto;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "grad",
        "vakcine",
})
@XmlRootElement(name = "GradVakcine")
public class GradVakcineDTO {

    @XmlElement(name = "grad")
    private String grad;

    @XmlElement(name = "vakcina", required = true)
    private List<String> vakcine;

    public GradVakcineDTO() {
        this.vakcine = new ArrayList<>();
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public List<String> getVakcine() {
        return vakcine;
    }

    public void setVakcine(List<String> vakcine) {
        this.vakcine = vakcine;
    }

    @Override
    public String toString() {
        return "GradVakcineDTO{" +
                "grad='" + grad + '\'' +
                ", vakcine=" + vakcine +
                '}';
    }
}
