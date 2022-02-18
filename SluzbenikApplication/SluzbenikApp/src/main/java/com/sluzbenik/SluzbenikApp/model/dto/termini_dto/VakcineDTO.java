package com.sluzbenik.SluzbenikApp.model.dto.termini_dto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
})
@XmlRootElement(name = "Vakcine")
public class VakcineDTO {

    @XmlElement(name = "Vakcina")
    private List<VakcinaDTO> vakcine;

    public VakcineDTO() {}

    public List<VakcinaDTO> getVakcina() {
        return vakcine;
    }

    public void setVakcina(List<VakcinaDTO> vakcine) {
        this.vakcine = vakcine;
    }
}
