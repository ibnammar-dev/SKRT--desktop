package com.skrt.desktop.controllers;

import com.skrt.desktop.services.AuthService;
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

public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    
    @FXML
    private TextField firstNameField;
    
    @FXML
    private TextField lastNameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private Button registerButton;
    
    @FXML
    private Hyperlink loginLink;
    
    @FXML
    private Label errorLabel;
    
    private final AuthService authService;
    
    public RegisterController() {
        this.authService = new AuthService();
    }
    
    @FXML
    private void handleRegister() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        // Validation
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please fill in all fields");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match");
            return;
        }
        
        registerButton.setDisable(true);
        errorLabel.setText("");
        
        authService.register(firstName, lastName, email, password)
            .thenAccept(response -> Platform.runLater(() -> {
                if (response.isSuccess()) {
                    logger.info("Registration successful for user: {}", email);
                    handleLogin(); // Navigate to login screen after successful registration
                } else {
                    String errorMessage = response.getMessage();
                    if (response.getErrors() != null && !response.getErrors().isEmpty()) {
                        errorMessage = response.getErrors().values().stream()
                            .flatMap(java.util.Arrays::stream)
                            .findFirst()
                            .orElse(errorMessage);
                    }
                    errorLabel.setText(errorMessage);
                    logger.warn("Registration failed: {}", errorMessage);
                }
            }))
            .exceptionally(throwable -> {
                Platform.runLater(() -> {
                    String errorMessage = throwable.getCause() != null ? 
                        throwable.getCause().getMessage() : 
                        "An error occurred during registration";
                    errorLabel.setText(errorMessage);
                    logger.error("Registration error", throwable);
                });
                return null;
            })
            .whenComplete((v, t) -> Platform.runLater(() -> registerButton.setDisable(false)));
    }
    
    @FXML
    private void handleLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent loginView = loader.load();
            
            Stage stage = (Stage) registerButton.getScene().getWindow();
            Scene scene = new Scene(loginView);
            scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
            
            stage.setScene(scene);
            stage.setTitle("SKRT Desktop - Login");
            stage.centerOnScreen();
            
            logger.info("Navigated to login screen");
        } catch (IOException e) {
            logger.error("Failed to load login view", e);
            errorLabel.setText("Failed to navigate to login screen");
        }
    }
} 