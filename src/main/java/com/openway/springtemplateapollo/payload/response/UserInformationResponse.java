package com.openway.springtemplateapollo.payload.response;

import com.google.gson.annotations.Expose;

public class UserInformationResponse {
    @Expose
    private int id;
    @Expose
    private String email;
    @Expose
    private String address;
    @Expose
    private String gender;
    @Expose
    private String refreshToken;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
