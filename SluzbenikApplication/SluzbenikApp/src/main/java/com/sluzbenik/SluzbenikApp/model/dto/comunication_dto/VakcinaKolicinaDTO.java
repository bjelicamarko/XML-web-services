package com.sluzbenik.SluzbenikApp.model.dto.comunication_dto;

import javax.xml.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "mapa"
})
@XmlRootElement(name = "Kolicine")
public class VakcinaKolicinaDTO {

    @XmlElement(name = "mapa")
    private Map<String, MapaDTO> mapa;

    public VakcinaKolicinaDTO() {
        this.mapa = new HashMap<>();
    }

    @Override
    public String toString() {
        return "VakcinaKolicinaDTO{" +
                "mapa=" + mapa +
                '}';
    }

    public Map<String, MapaDTO> getMapa() {
        return mapa;
    }

    public void setMapa(Map<String, MapaDTO> mapa) {
        this.mapa = mapa;
    }
}
