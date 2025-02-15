module com.skrt.desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires jakarta.validation;
    requires org.slf4j;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;

    opens com.skrt.desktop to javafx.fxml;
    opens com.skrt.desktop.controllers to javafx.fxml;
    opens com.skrt.desktop.models to com.fasterxml.jackson.databind;
    opens com.skrt.desktop.models.requests to com.fasterxml.jackson.databind;
    opens com.skrt.desktop.models.responses to com.fasterxml.jackson.databind;
    opens com.skrt.desktop.utils to com.fasterxml.jackson.databind;
    opens com.skrt.desktop.services to com.fasterxml.jackson.databind;
    
    exports com.skrt.desktop;
    exports com.skrt.desktop.controllers;
    exports com.skrt.desktop.models;
    exports com.skrt.desktop.models.requests;
    exports com.skrt.desktop.models.responses;
    exports com.skrt.desktop.services;
}