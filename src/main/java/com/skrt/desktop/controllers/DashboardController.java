package com.skrt.desktop.controllers;

import com.skrt.desktop.models.User;
import com.skrt.desktop.services.TokenService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        // TODO: Navigate back to login screen
    }
} 