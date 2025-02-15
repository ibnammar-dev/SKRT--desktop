module com.skrt.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires jakarta.validation;
    requires ch.qos.logback.classic;
    
    opens com.skrt.desktop to javafx.fxml;
    opens com.skrt.desktop.controllers to javafx.fxml;
    opens com.skrt.desktop.models to com.fasterxml.jackson.databind;
    
    exports com.skrt.desktop;
    exports com.skrt.desktop.controllers;
    exports com.skrt.desktop.models;
}