package com.imunizacija.ImunizacijaApp.model.app_users;

import com.imunizacija.ImunizacijaApp.model.vakc_sistem.korisnik.Korisnik;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class KorisnikBasicInfoDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String userID;

    public KorisnikBasicInfoDTO() {
    }

    public KorisnikBasicInfoDTO(Korisnik korisnik){
        this.firstName = korisnik.getIme();
        this.lastName = korisnik.getPrezime();
        this.email = korisnik.getEmail();
        this.userID = korisnik.getKorisnikID();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
