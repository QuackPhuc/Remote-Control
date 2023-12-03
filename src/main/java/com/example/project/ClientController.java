package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import com.example.project.mail.sendMail;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController {
    public String textControl;
    @FXML
    private Button buttonsd;

    @FXML
    private TextField timer;
    @FXML
    private AnchorPane pane_visable;
    public void handleExitImageClick(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
    }

    private String to = "phimphu8@gmail.com";

    // Sender's email ID needs to be mentioned
    private String from = "dangtanphat9a1@gmail.com";
    private String username = "dangtanphat9a1@gmail.com";//change accordingly
    private String password = "mylxemednkcccfzz";//change accordingly
    private String subject = "ScreenShot";

    private sendMail send = new sendMail(to, from,password,subject);
    public void buttonShutdown(ActionEvent eventc1) throws IOException, InterruptedException {
        System.out.println("shutdown");
        textControl = "shutdown";

        send.sendContent(textControl);

    }
    public void buttonSleep(ActionEvent eventc1) throws IOException{
        System.out.println("sleep");
        textControl = "sleep";
        send.sendContent(textControl);
    }
    public void buttonRestart(ActionEvent eventc1) throws IOException {
        pane_visable.setVisible(true);
        System.out.println("restart");
        send.sendContent("restart");
        pane_visable.setVisible(false);
    }
    public void buttonLogout(ActionEvent eventc1) throws IOException{
        System.out.println("logout");
    }
    public void buttonScrshot(ActionEvent eventc1) throws IOException{
        System.out.println("scrshot");
    }
    public void buttonZoom(ActionEvent eventc1) throws IOException{
        System.out.println("zoom");
    }
    public void buttonSaveas(ActionEvent eventc1) throws IOException{
        System.out.println("saveas");
    }
    public void buttonLogTurnOn(ActionEvent eventc1) throws IOException{
        System.out.println("turnon");
    }
    public void buttonLogTurnOff(ActionEvent eventc1) throws IOException{
        System.out.println("turnoff");
    }
    //Nhap timer
    @FXML
    private void handleKeyTimer(KeyEvent event){
        String input = timer.getText();
        if (!isValidNumber(input)) {
            timer.setText(input.replaceAll("[^\\d]", ""));
            event.consume();
        }
    }
    private boolean isValidNumber(String input) {
        return input.matches("\\d*");
    }

}

