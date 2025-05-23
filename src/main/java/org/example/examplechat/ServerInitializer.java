package org.example.examplechat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerInitializer extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/server.fxml"))));
        primaryStage.setTitle("Server Chat");
        primaryStage.show();


             }

    public static void main(String[] args) {
        launch(args);
    }
}