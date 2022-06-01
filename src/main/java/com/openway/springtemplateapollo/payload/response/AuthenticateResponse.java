package com.openway.springtemplateapollo.payload.response;


import com.google.gson.annotations.Expose;

public class AuthenticateResponse {
    @Expose
    private String accessToken;
    @Expose
    private String refreshToken;

    public AuthenticateResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
