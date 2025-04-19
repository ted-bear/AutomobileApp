package com.toporkov.automobileapp.web.dto.domain;

import java.util.List;
import java.util.UUID;

public class RegistrationManagerDTO {

    private String firstname;

    private String lastname;

    private String username;

    private String password;

    private String passwordConfirmation;

    private List<UUID> enterprises;

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public List<UUID> getEnterprises() {
        return enterprises;
    }

    public void setEnterprises(List<UUID> enterprises) {
        this.enterprises = enterprises;
    }
}
