package com.imunizacija.ImunizacijaApp.model.app_users;

import com.imunizacija.ImunizacijaApp.model.app_users.enums.Nationality;
import com.imunizacija.ImunizacijaApp.model.app_users.enums.UserType;

public class AppUser {
    private String userID;
    private String email;
    private String password;
    private UserType userType;
    private Nationality nationality;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }
}
