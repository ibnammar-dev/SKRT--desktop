package com.skrt.desktop.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skrt.desktop.config.ApiConfig;
import com.skrt.desktop.models.User;
import com.skrt.desktop.utils.ApiResponse;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.util.concurrent.CompletableFuture;

public class AuthService {
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    
    public AuthService() {
        this.objectMapper = new ObjectMapper();
        this.baseUrl = ApiConfig.API_BASE_URL;
    }
    
    public CompletableFuture<ApiResponse<User>> login(String email, String password) {
        return CompletableFuture.supplyAsync(() -> {
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpPost request = new HttpPost(baseUrl + ApiConfig.AUTH_LOGIN);
                
                String json = objectMapper.writeValueAsString(new LoginRequest(email, password));
                StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
                request.setEntity(entity);
                
                return client.execute(request, response -> {
                    String responseBody = new String(response.getEntity().getContent().readAllBytes());
                    return objectMapper.readValue(responseBody, ApiResponse.class);
                });
            } catch (Exception e) {
                throw new RuntimeException("Failed to login", e);
            }
        });
    }
    
    private static class LoginRequest {
        private final String email;
        private final String password;
        
        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
        
        public String getEmail() {
            return email;
        }
        
        public String getPassword() {
            return password;
        }
    }
} 