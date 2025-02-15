package com.skrt.desktop.controllers;

import com.skrt.desktop.services.AuthService;
import com.skrt.desktop.utils.ApiResponse;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                    // TODO: Store the token and navigate to main screen
                    System.out.println("Login successful. Token: " + response.getData());
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
        // TODO: Navigate to register screen
        System.out.println("Navigate to register");
    }
} 