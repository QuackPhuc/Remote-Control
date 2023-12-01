// App.java
package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {


    private static Stage stage;
    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource( "login.fxml"));
        primaryStage.setScene(new Scene(root, 320, 240));
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setTitle("LOGIN");
        primaryStage.show();
        stage = primaryStage;
    }
    public void setClient(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource( "client.fxml"));

        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("RemoveControll");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
