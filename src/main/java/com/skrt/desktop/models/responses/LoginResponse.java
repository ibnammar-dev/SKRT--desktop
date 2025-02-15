package com.skrt.desktop.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skrt.desktop.models.User;

public class LoginResponse {
    @JsonProperty("token")
    private String token;
    
    @JsonProperty("user")
    private User user;
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
} 