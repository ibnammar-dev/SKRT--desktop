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

public class LoginController {
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
        String email = emailField.getText();
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
                    // TODO: Navigate to main screen
                    System.out.println("Login successful");
                } else {
                    errorLabel.setText(response.getMessage());
                }
            }))
            .exceptionally(throwable -> {
                Platform.runLater(() -> {
                    errorLabel.setText("An error occurred during login");
                    loginButton.setDisable(false);
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