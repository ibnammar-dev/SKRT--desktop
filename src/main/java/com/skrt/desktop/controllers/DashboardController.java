package com.skrt.desktop.controllers;

import com.skrt.desktop.models.User;
import com.skrt.desktop.services.TokenService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DashboardController {
    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    
    @FXML
    private Label userNameLabel;
    
    @FXML
    private Label userEmailLabel;
    
    @FXML
    private StackPane contentArea;
    
    @FXML
    public void initialize() {
        User user = TokenService.getInstance().getCurrentUser();
        userNameLabel.setText(user.getFullName());
        userEmailLabel.setText(user.getEmail());
        logger.info("Dashboard initialized for user: {}", user.getEmail());
    }
    
    @FXML
    private void handleDashboard() {
        logger.debug("Dashboard menu clicked");
        // TODO: Show dashboard content
    }
    
    @FXML
    private void handlePosts() {
        logger.debug("Posts menu clicked");
        // TODO: Load posts view into content area
    }
    
    @FXML
    private void handleProfile() {
        logger.debug("Profile menu clicked");
        // TODO: Load profile view into content area
    }
    
    @FXML
    private void handleLogout() {
        logger.debug("Logout clicked");
        TokenService.getInstance().clear();
        
        try {
            // Load the login view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent loginView = loader.load();
            
            // Get the current stage
            Stage stage = (Stage) contentArea.getScene().getWindow();
            
            // Create new scene with login view
            Scene scene = new Scene(loginView);
            scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
            
            // Set the scene and update stage
            stage.setScene(scene);
            stage.setTitle("SKRT Desktop - Login");
            stage.setMaximized(false);
            stage.centerOnScreen();
            stage.sizeToScene();
            
            logger.info("Successfully logged out and returned to login screen");
        } catch (IOException e) {
            logger.error("Failed to load login view", e);
        }
    }
} 