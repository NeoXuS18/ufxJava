module application {
    requires javafx.controls;
    requires javafx.fxml;


    opens application to javafx.fxml;
    exports application;

    opens controller to javafx.fxml;
    exports controller;

    opens model to javafx.fxml;
    exports  model;
}