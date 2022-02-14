package com.imunizacija.ImunizacijaApp.model.app_users;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "registrationDTO")
public class RegistrationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userId;
    private String nationalityType;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String firstName, String lastName, String email, String password, String userId, String nationalityType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.nationalityType = nationalityType;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNationalityType() {
        return nationalityType;
    }

    public void setNationalityType(String nationalityType) {
        this.nationalityType = nationalityType;
    }
}
