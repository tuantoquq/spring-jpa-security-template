package com.openway.springtemplateapollo.payload.request;

import com.openway.springtemplateapollo.entity.Role;

public class AddUserRequest {
    private String email;
    private String password;
    private String address;
    private String gender;

    public AddUserRequest(String email, String password, String address, String gender) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.gender = gender;
    }
    public AddUserRequest(){

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
