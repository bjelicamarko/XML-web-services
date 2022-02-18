package com.sluzbenik.SluzbenikApp.model.dto.dzs_dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "zahtev")
public class CreateDzsDTO {
    private String zahtevId;
    private String userId;
    private String userEmail;

    public CreateDzsDTO(){}

    public String getZahtevId() {
        return zahtevId;
    }

    public void setZahtevId(String zahtevId) {
        this.zahtevId = zahtevId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
