package com.skrt.desktop.controllers;

import com.skrt.desktop.models.User;
import com.skrt.desktop.models.responses.LoginResponse;
import com.skrt.desktop.services.AuthService;
import com.skrt.desktop.services.TokenService;
import com.skrt.desktop.utils.ApiResponse;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Hyperlink registerLink;
    
    @FXML
    private Label errorLabel;
    
    private final AuthService authService;
    
    public LoginController() {
        this.authService = new AuthService();
    }
    
    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        
        if (email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please fill in all fields");
            return;
        }
        
        loginButton.setDisable(true);
        errorLabel.setText("");
        
        authService.login(email, password)
            .thenAccept(response -> Platform.runLater(() -> {
                if (response.isSuccess()) {
                    logger.info("Login successful for user: {}", email);
                    
                    // Store token and user data
                    LoginResponse loginData = response.getData();
                    TokenService.getInstance().setToken(loginData.getToken());
                    TokenService.getInstance().setCurrentUser(loginData.getUser());
                    
                    // Navigate to dashboard
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
                        Parent dashboard = loader.load();
                        
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        Scene scene = new Scene(dashboard);
                        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
                        
                        stage.setScene(scene);
                        stage.setTitle("SKRT Desktop - Dashboard");
                        stage.setMaximized(true);
                        
                        logger.info("Navigated to dashboard");
                    } catch (IOException e) {
                        logger.error("Failed to load dashboard", e);
                        errorLabel.setText("Failed to load dashboard");
                    }
                } else {
                    String errorMessage = response.getMessage();
                    if (response.getErrors() != null && !response.getErrors().isEmpty()) {
                        errorMessage = response.getErrors().values().stream()
                            .flatMap(java.util.Arrays::stream)
                            .findFirst()
                            .orElse(errorMessage);
                    }
                    errorLabel.setText(errorMessage);
                    logger.warn("Login failed: {}", errorMessage);
                }
            }))
            .exceptionally(throwable -> {
                Platform.runLater(() -> {
                    String errorMessage = throwable.getCause() != null ? 
                        throwable.getCause().getMessage() : 
                        "An error occurred during login";
                    errorLabel.setText(errorMessage);
                    logger.error("Login error", throwable);
                });
                return null;
            })
            .whenComplete((v, t) -> Platform.runLater(() -> loginButton.setDisable(false)));
    }
    
    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            Parent registerView = loader.load();
            
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(registerView);
            scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
            
            stage.setScene(scene);
            stage.setTitle("SKRT Desktop - Create Account");
            stage.centerOnScreen();
            
            logger.info("Navigated to registration screen");
        } catch (IOException e) {
            logger.error("Failed to load registration view", e);
            errorLabel.setText("Failed to navigate to registration screen");
        }
    }
} 