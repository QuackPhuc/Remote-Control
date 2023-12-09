
package com.example.project;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import com.example.project.mail.sendMail;
import com.example.project.mail.receiveMail;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField,codeField;
    @FXML
    private Label invalid;
    @FXML
    private Button buttonLogin;
    private String username;
    private String password;
    private String code;


    public void loginButtonAction() throws IOException {
        this.username = usernameField.getText();
        this.password = passwordField.getText();
        this.code = codeField.getText();

        if (isValidLogin(username, password, code)) {
            openClientWindow(username, password,code);
            Stage login = App.getStage();
            login.close();

        } else {
            System.out.println("Invalid login credentials");
            invalidLogin();
        }
    }

    private boolean isValidLogin(String username, String password, String code) {
        Platform.runLater(()->{
            invalid.setText("Please wait for a few seconds!");
            invalid.setTextFill(Color.RED);
        });
        sendMail send = new sendMail("projectmangmaytinh2004@gmail.com",username,password,code + " connect");
        send.sendContent("0");
        long startTime = System.currentTimeMillis();
        long timeout = 60000; // 60 seconds in milliseconds

        boolean correctResponseReceived = false;
        while (System.currentTimeMillis() - startTime < timeout && !correctResponseReceived) {
            receiveMail receive = new receiveMail(username, password);
            receive.receiveMail();
            System.out.println(receive.getContent());
            if (receive.getContent().equals(code + " " + "connect")){
                return true;
            }
        }
        return false;
    }
    public void invalidLogin() {
        invalid.setText("Please sign up again!");
        invalid.setTextFill(Color.RED);
    }
    private void openClientWindow(String username, String password, String code) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));
        Parent root = loader.load();

        ClientController clientController = loader.getController();

        //init
        clientController.initData(username, password,code);
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 800, 500));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.setTitle("RemoveControll");
        stage.show();
    }
}
