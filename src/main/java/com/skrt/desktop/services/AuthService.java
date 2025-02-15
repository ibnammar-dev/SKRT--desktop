package com.skrt.desktop.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.skrt.desktop.config.ApiConfig;
import com.skrt.desktop.models.requests.LoginRequest;
import com.skrt.desktop.models.requests.RegisterRequest;
import com.skrt.desktop.models.responses.LoginResponse;
import com.skrt.desktop.utils.ApiResponse;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    
    public AuthService() {
        this.objectMapper = new ObjectMapper();
        this.baseUrl = ApiConfig.API_BASE_URL;
        logger.info("AuthService initialized with base URL: {}", baseUrl);
    }
    
    public CompletableFuture<ApiResponse<LoginResponse>> login(String email, String password) {
        logger.info("Attempting login for user: {}", email);
        return CompletableFuture.supplyAsync(() -> {
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpPost request = new HttpPost(baseUrl + ApiConfig.AUTH_LOGIN);
                
                // Set headers
                request.setHeader("Content-Type", "application/json");
                
                // Create request body
                String json = objectMapper.writeValueAsString(new LoginRequest(email, password));
                StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
                request.setEntity(entity);
                
                logger.debug("Sending login request to: {}", request.getUri());
                logger.debug("Request body: {}", json);
                
                return client.execute(request, response -> {
                    String responseBody = new String(response.getEntity().getContent().readAllBytes());
                    logger.debug("Received response: {}", responseBody);
                    
                    // Create type reference for proper deserialization
                    TypeFactory typeFactory = objectMapper.getTypeFactory();
                    return objectMapper.readValue(responseBody, 
                        typeFactory.constructParametricType(ApiResponse.class, LoginResponse.class));
                });
            } catch (Exception e) {
                logger.error("Failed to login for user: " + email, e);
                throw new RuntimeException("Failed to login", e);
            }
        });
    }
    
    public CompletableFuture<ApiResponse<Void>> register(String firstName, String lastName, String email, String password) {
        logger.info("Attempting to register user: {}", email);
        return CompletableFuture.supplyAsync(() -> {
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpPost request = new HttpPost(baseUrl + ApiConfig.AUTH_REGISTER);
                
                // Set headers
                request.setHeader("Content-Type", "application/json");
                
                // Create request body
                RegisterRequest registerRequest = new RegisterRequest(firstName, lastName, email, password);
                String json = objectMapper.writeValueAsString(registerRequest);
                StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
                request.setEntity(entity);
                
                logger.debug("Sending registration request to: {}", request.getUri());
                logger.debug("Request body: {}", json);
                
                return client.execute(request, response -> {
                    String responseBody = new String(response.getEntity().getContent().readAllBytes());
                    logger.debug("Received response: {}", responseBody);
                    
                    // Create type reference for proper deserialization
                    TypeFactory typeFactory = objectMapper.getTypeFactory();
                    return objectMapper.readValue(responseBody, 
                        typeFactory.constructParametricType(ApiResponse.class, Void.class));
                });
            } catch (Exception e) {
                logger.error("Failed to register user: " + email, e);
                throw new RuntimeException("Failed to register", e);
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