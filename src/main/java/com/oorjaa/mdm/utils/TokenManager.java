package com.oorjaa.mdm.utils;

import org.springframework.stereotype.Component;

@Component
public class TokenManager {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean hasToken() {

        return token != null &&
                !token.isBlank();

    }

    public void clearToken() {
        token = null;
    }

}