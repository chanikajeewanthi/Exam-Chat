module org.example.examplechat {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.examplechat to javafx.fxml;
    exports org.example.examplechat;
    exports org.example.examplechat.controller;
    opens org.example.examplechat.controller to javafx.fxml;
}