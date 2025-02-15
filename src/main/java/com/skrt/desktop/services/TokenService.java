package com.skrt.desktop.services;

import com.skrt.desktop.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private static TokenService instance;
    
    private String token;
    private User currentUser;
    
    private TokenService() {}
    
    public static TokenService getInstance() {
        if (instance == null) {
            instance = new TokenService();
        }
        return instance;
    }
    
    public void setToken(String token) {
        this.token = token;
        logger.debug("Auth token updated");
    }
    
    public String getToken() {
        return token;
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
        logger.debug("Current user updated: {}", user.getEmail());
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public void clear() {
        this.token = null;
        this.currentUser = null;
        logger.debug("Token service cleared");
    }
    
    public boolean isAuthenticated() {
        return token != null && currentUser != null;
    }
} 