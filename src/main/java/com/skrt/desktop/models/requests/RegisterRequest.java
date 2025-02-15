package com.skrt.desktop.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest {
    @JsonProperty("firstName")
    private final String firstName;
    
    @JsonProperty("lastName")
    private final String lastName;
    
    @JsonProperty("email")
    private final String email;
    
    @JsonProperty("password")
    private final String password;
    
    public RegisterRequest(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    
    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }
    
    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
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