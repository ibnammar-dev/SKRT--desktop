package com.skrt.desktop.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {
    @JsonProperty("email")
    private final String email;
    
    @JsonProperty("password")
    private final String password;
    
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }
    
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }
} 