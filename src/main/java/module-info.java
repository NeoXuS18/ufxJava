module ufx.java.ufxjava {
    requires javafx.controls;
    requires javafx.fxml;


    opens ufx.java.ufxjava to javafx.fxml;
    exports ufx.java.ufxjava;
}