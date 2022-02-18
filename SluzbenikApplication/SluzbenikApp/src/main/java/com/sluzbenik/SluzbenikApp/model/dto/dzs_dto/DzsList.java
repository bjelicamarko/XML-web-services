package com.sluzbenik.SluzbenikApp.model.dto.dzs_dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "zahtev")
public class DzsList {

    private List<String> dzsList;

    public DzsList(){}

    public DzsList(List<String> dzsList) { this.dzsList = dzsList; }

    public List<String> getDzsList() {
        return dzsList;
    }

    public void setDzsList(List<String> dzsList) {
        this.dzsList = dzsList;
    }
}
