module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens application to javafx.fxml;
    exports application;

    opens controller to javafx.fxml;
    exports controller;

    opens model to javafx.fxml;
    exports  model;
}