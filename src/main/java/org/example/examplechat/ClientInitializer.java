package org.example.examplechat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientInitializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/clientView.fxml"))));
        stage.setTitle("Client");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}