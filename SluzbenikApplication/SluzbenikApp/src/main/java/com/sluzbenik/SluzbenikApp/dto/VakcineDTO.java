package com.sluzbenik.SluzbenikApp.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
})
@XmlRootElement(name = "Vakcine")
public class VakcineDTO {

    @XmlElement(name = "Vakcina")
    List<VakcinaDTO> vakcine;

    public VakcineDTO() {}

    public List<VakcinaDTO> getVakcina() {
        return vakcine;
    }

    public void setVakcina(List<VakcinaDTO> vakcine) {
        this.vakcine = vakcine;
    }
}
