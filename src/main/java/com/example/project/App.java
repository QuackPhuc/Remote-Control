// App.java
package com.example.project;

import com.example.project.server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class App extends Application {
    private static Stage stage;
    public static Stage getStage() {
        return stage;
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource( "login.fxml"));
        Parent root =  loader.load();

        primaryStage.setScene(new Scene(root, 320, 350));
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setTitle("LOGIN");
        primaryStage.show();
        stage = primaryStage;
    }
}

