
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
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.example.project.mail.sendMail;
import com.example.project.mail.receiveMail;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField, codeField;
    @FXML
    private Label invalid;
    @FXML
    private Button buttonLogin;
    private String username;
    private String password;
    private String code;
    @FXML
    public void initialize() {
        buttonLogin.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    loginButtonAction();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    public void loginButtonAction() throws IOException {
        this.username = usernameField.getText();
        this.password = passwordField.getText();
        this.code = codeField.getText();
        invalid.setText("Please wait for a few seconds!");
        invalid.setTextFill(Color.GREEN);
        buttonLogin.setDisable(true);
        usernameField.setDisable(true);
        passwordField.setDisable(true);
        codeField.setDisable(true);
        Thread newthread = new Thread(()->{
            if (isValidLogin(username, password, code)) {
                try {
                    openClientWindow(username, password, code);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(()->{
                    buttonLogin.setDisable(false);
                    usernameField.setDisable(false);
                    passwordField.setDisable(false);
                    codeField.setDisable(false);
                    Stage login = App.getStage();
                    login.close();
                });

            } else {
                Platform.runLater(()->{
                    buttonLogin.setDisable(false);
                    usernameField.setDisable(false);
                    passwordField.setDisable(false);
                    codeField.setDisable(false);
                    System.out.println("Invalid login credentials");
                    invalid.setText("Please sign up again!");
                    invalid.setTextFill(Color.RED);
                });
            }
        });
        newthread.start();
    }

    private boolean isValidLogin(String username, String password, String code) {

        sendMail send = new sendMail("projectmangmaytinh2004@gmail.com", username, password, code + " connect");
        send.sendContent("0");
        if (!send.check){
            return false;
        }
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


    private void openClientWindow(String username, String password, String code) throws IOException {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));
                Parent root = loader.load();

                ClientController clientController = loader.getController();

                //init
                clientController.initData(username, password, code);
                Stage stage = new Stage();
                stage.setScene(new Scene(root, 800, 500));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.centerOnScreen();
                stage.setTitle("RemoveControll");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace(); // or handle the exception appropriately
            }
        });
    }
}
