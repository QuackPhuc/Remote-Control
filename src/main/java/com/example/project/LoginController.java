
package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private Label invalid;


    public void loginButtonAction() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isValidLogin(username, password)) {
            Stage login = App.getStage();
            login.close();  //Destroy screen login
            // Login successful, switch to the client page
            App app = new App();
            Stage stage = new Stage();
            app.setClient(stage);

        } else {
            System.out.println("Invalid login credentials");
            invalidLogin();
        }
    }

    private boolean isValidLogin(String username, String password) {
        // Implement your own login validation logic here
        return "admin".equals(username) && "123".equals(password);
    }
    public void invalidLogin() {
        invalid.setText("Please sign up again!");
        invalid.setTextFill(Color.RED);
    }
}
