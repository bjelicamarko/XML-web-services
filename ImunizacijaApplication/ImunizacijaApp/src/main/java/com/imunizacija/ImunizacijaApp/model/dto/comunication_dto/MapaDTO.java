package com.imunizacija.ImunizacijaApp.model.dto.comunication_dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.HashMap;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "mapa"
})
@XmlRootElement(name = "Mapa")
public class MapaDTO {

    private Map<String, Integer> mapa;

    public MapaDTO() {
        this.mapa = new HashMap<>();
    }

    @Override
    public String toString() {
        return "MapaDTO{" +
                "mapa=" + mapa +
                '}';
    }

    public Map<String, Integer> getMapa() {
        return mapa;
    }

    public void setMapa(Map<String, Integer> mapa) {
        this.mapa = mapa;
    }
}
