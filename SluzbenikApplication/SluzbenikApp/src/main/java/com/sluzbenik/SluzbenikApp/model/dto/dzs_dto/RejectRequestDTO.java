package com.sluzbenik.SluzbenikApp.model.dto.dzs_dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "odbijanje")
public class RejectRequestDTO {
    private String zahtevId;
    private String userEmail;
    private String reason;

    public RejectRequestDTO() {
    }

    public String getZahtevId() {
        return zahtevId;
    }

    public void setZahtevId(String zahtevId) {
        this.zahtevId = zahtevId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
