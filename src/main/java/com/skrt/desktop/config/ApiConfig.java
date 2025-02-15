package com.skrt.desktop.config;

public class ApiConfig {
    public static final String API_BASE_URL = "http://localhost:8000/api";
    
    // Auth endpoints
    public static final String AUTH_LOGIN = "/auth/login";
    public static final String AUTH_REGISTER = "/auth/register";
    public static final String AUTH_LOGOUT = "/auth/logout";
    
    // User endpoints
    public static final String USERS_LIST = "/users";
    public static final String USER_DETAILS = "/users/{id}";
    public static final String USER_UPDATE = "/users/{id}";
    public static final String USER_DELETE = "/users/{id}";
    
    // Post endpoints
    public static final String POSTS_LIST = "/posts";
    public static final String POST_DETAILS = "/posts/{id}";
    public static final String POST_CREATE = "/posts";
    public static final String POST_UPDATE = "/posts/{id}";
    public static final String POST_DELETE = "/posts/{id}";
    
    // Headers
    public static final String HEADER_AUTH = "X-API-TOKEN";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_JSON = "application/json";
    
    private ApiConfig() {
        // Private constructor to prevent instantiation
    }
} 