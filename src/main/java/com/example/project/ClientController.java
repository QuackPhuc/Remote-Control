package com.example.project;

import com.example.project.mail.receiveMail;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import com.example.project.mail.sendMail;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.awt.*;
import javafx.scene.control.TextArea;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ClientController {
    public String textControl;
    @FXML private Button buttonsd, buttonsl, buttonres,buttonlog,
            buttonscrshot,buttonzoom,buttonsaveasScr,
            buttonLogTurnOn,buttonLogTurnOff,buttonsaveasLog,
            buttonStartPrc,buttonStartApp,
            buttonBackAddress, buttonGetAddress, buttonResGet,buttonSaveGetFile;
    @FXML private Label responsePower,responeScr, label_warning;
    @FXML private ImageView scrshot;
    @FXML private TextArea LogText;
    private int[] number = {0,0,0,0,0,0,0,0,0,0};
    public void handleExitImageClick(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
    }
    private String key = "AQDPPHIF";
    private String to = "projectmangmaytinh2004@gmail.com";

    // Sender's email ID needs to be mentioned
    private String from = "phimphu8@gmail.com";
    private String username = "phimphu8@gmail.com";//change accordingly
    private String password = "ezhtzbrxnlkgyfmu";//change accordingly
    private String subject = "";
    private sendMail send = new sendMail(to, from,password,subject);
    private receiveMail receive = new receiveMail(username,password);
    public void OnButtonShutdown(ActionEvent event) throws IOException {
        if (!buttonsd.isDisabled()){
        comfirmRequest("5",5);
        System.out.println("ok");
        }
    }
    public void OnButtonSleep(ActionEvent event) throws IOException{
        if (!buttonsl.isDisabled()){
            comfirmRequest("8",8);
        }
    }
    public void OnButtonRestart(ActionEvent event) throws IOException{
        if (!buttonres.isDisable()){
            comfirmRequest("6",6);
        }
    }
    public void OnButtonLogout(ActionEvent event) throws IOException{
        if (!buttonlog.isDisable()){
            comfirmRequest("7",7);
        }
    }

    public void OnButtonScrshot(ActionEvent event) throws IOException {
        System.out.println("scrshot");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to screenshot?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            number[4] += 1;
            Platform.runLater(() -> {
                buttonscrshot.setDisable(true);
                responeScr.setText("Please wait a few minutes!");
            });

            Thread newThread = new Thread(() -> {
                send.setSubject(key + " " + number[4]);
                send.sendContent("4");
                File oldfile = new File("src/main/resources/com/example/project/file/screen.png");
                oldfile.delete();

                long startTime = System.currentTimeMillis();
                long timeout = 60000; // 60 seconds in milliseconds

                boolean correctResponseReceived = false;
                while (System.currentTimeMillis() - startTime < timeout && !correctResponseReceived) {
                    receiveMail receive = new receiveMail(username, password);
                    receive.receiveMail();
                    System.out.println(receive.getContent());

                    if (receive.getContent().equals(key + " " + number[4])) {
                        correctResponseReceived = true;
                        File file = new File("src/main/resources/com/example/project/file/screen.png");
                        Image image = new Image(file.toURI().toString());
                        Platform.runLater(() -> {
                            scrshot.setImage(image);
                            buttonscrshot.setDisable(false);
                            buttonzoom.setDisable(false);
                            buttonsaveasScr.setDisable(false);
                            responeScr.setText("Successful!");
                        });
                        break;
                    }

                    try {
                        Thread.sleep(5000); // Wait for 1 second before checking again
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (!correctResponseReceived) {
                    Platform.runLater(() -> {
                        buttonscrshot.setDisable(false);
                        responeScr.setText("Failed, please send again!");
                        responeScr.setTextFill(Color.RED);
                    });
                }
            });
            newThread.start();
        } else {
            buttonscrshot.setDisable(false);
        }
    }

    public void OnButtonZoom(ActionEvent event) throws IOException{
        if (!buttonzoom.isDisable()){
            Platform.runLater(()->{
                File file = new File("src/main/resources/com/example/project/file/screen.png");
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
        String sourceFilePath = "src/main/resources/com/example/project/file/screen.png";
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
            number[1]+=1;
            Platform.runLater(() -> {
                buttonLogTurnOn.setDisable(true);
            });
            Thread newThread = new Thread(() -> {
                send.setSubject(key+ " "+ number[1]);
                send.sendContent("1");
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
        number[1]+=1;
        if (!buttonLogTurnOff.isDisable()){
            Platform.runLater(() -> {
                buttonLogTurnOff.setDisable(true);
            });
            Thread newThread = new Thread(() -> {
                send.setSubject(key+ " "+ number[1]);
                send.sendContent("2");
                long startTime = System.currentTimeMillis();
                long timeout = 60000; // 60 seconds in milliseconds
                boolean correctResponseReceived = false;
                File oldfile = new File("src/main/resources/com/example/project/file/keylog.txt");
                oldfile.delete();
                while (System.currentTimeMillis() - startTime < timeout && !correctResponseReceived){
                    receiveMail receive = new receiveMail(username,password);
                    receive.receiveMail();
                    if (receive.getContent().equals(key+" "+ number[1])){
                        File file = new File("src/main/resources/com/example/project/file/keylog.txt");
                        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                            StringBuilder content = new StringBuilder();
                            String text;
                            while ((text = br.readLine()) != null) {
                                content.append(text).append("\n");
                            }
                            Platform.runLater(() -> {
                                LogText.setText(String.valueOf(content));
                                buttonLogTurnOff.setDisable(false);
                                buttonsaveasLog.setDisable(false);
                                buttonLogTurnOn.setDisable(false);
                            });
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(5000); // Wait for 1 second before checking again
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
    public void comfirmRequest(String pow, int index) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to " + pow + "?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            number[index]+=1;
            Platform.runLater(() -> {
                buttonsd.setDisable(true);
                buttonsl.setDisable(true);
                buttonres.setDisable(true);
                buttonlog.setDisable(true);
                responsePower.setText("Please wait a few minutes!");
            });
            Thread newThread = new Thread(() -> {
                send.setSubject(key +" "+ number[index]);
                send.sendContent(pow);
                long startTime = System.currentTimeMillis();
                long timeout = 60000; // 60 seconds in milliseconds

                boolean correctResponseReceived = false;
                while (System.currentTimeMillis() - startTime < timeout && !correctResponseReceived) {
                    receiveMail receive = new receiveMail(username,password);
                    receive.receiveMail();
                    if (receive.getContent().equals(key+" "+number[index])){
                        if (receive.getText().equals("successfull")){
                            Platform.runLater(() -> {
                                buttonsd.setDisable(false);
                                buttonsl.setDisable(false);
                                buttonres.setDisable(false);
                                buttonlog.setDisable(false);
                                responsePower.setText("Successfull!");
                            });
                        }
                        break;
                    }
                    try {
                        Thread.sleep(5000); // Wait for 1 second before checking again
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!correctResponseReceived) {
                    Platform.runLater(() -> {
                        buttonsd.setDisable(false);
                        buttonsl.setDisable(false);
                        buttonres.setDisable(false);
                        buttonlog.setDisable(false);
                        responsePower.setText("Failed, please sendmail again!");
                    });
                }
            });
            newThread.start();
        }
        else {
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
        number[3]+=1;
        Platform.runLater(()->{
            buttonStartPrc.setDisable(true);
        });
        Thread thread = new Thread(()->{
            send.setSubject(key +" "+ number[3]);
            send.sendContent("3");

            File oldfile = new File("src/main/resources/com/example/project/file/listPrc.txt");
            oldfile.delete();
            long startTime = System.currentTimeMillis();
            long timeout = 60000; // 60 seconds in milliseconds

            boolean correctResponseReceived = false;
            while (System.currentTimeMillis() - startTime < timeout && !correctResponseReceived) {
                receiveMail receive = new receiveMail(username, password);
                receive.receiveMail();
                System.out.println(receive.getContent());
                if (receive.getContent().equals(key + " " + number[3])) {
                    Platform.runLater(() -> {
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
                    break;
                }
                try {
                    Thread.sleep(5000); // Wait for 1 second before checking again
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!correctResponseReceived){
                buttonStartPrc.setDisable(false);
            }
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
        number[0]+=1;
        Platform.runLater(()->{
            buttonStartApp.setDisable(true);
        });
        Thread thread = new Thread(()->{
            send.setSubject(key +" "+ number[0]);
            send.sendContent("0");
            long startTime = System.currentTimeMillis();
            long timeout = 60000; // 60 seconds in milliseconds

            boolean correctResponseReceived = false;
            File oldfile = new File("src/main/resources/com/example/project/file/listApp.txt");
            oldfile.delete();
            while (System.currentTimeMillis() - startTime < timeout && !correctResponseReceived) {
                receiveMail receive = new receiveMail(username, password);
                receive.receiveMail();
                System.out.println(receive.getContent());
                if (receive.getContent().equals(key + " " + number[0])) {
                    Platform.runLater(() -> {
                        System.out.println("ok");
                        Applist = FXCollections.observableArrayList();
                        app_Name.setCellValueFactory(new PropertyValueFactory<TaskInfo.Application, String>("app_Name"));
                        loadAppFromFile();
                        app_Table.setItems(Applist);
                        buttonStartApp.setDisable(false);
                    });
                    break;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!correctResponseReceived){
                buttonStartApp.setDisable(false);
            }
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

    //Get file
    @FXML
    private TableView <TaskInfo.address> table_address;
    @FXML
    private TableColumn<TaskInfo.address,String> name_address;
    private ObservableList<TaskInfo.address> Addresslist;
    private String path=" ";
    private Boolean isGetAddress = false;
    public void OnButtonBackAddress(ActionEvent event){

    }
    public void OnButtonGetAddress(ActionEvent event){
        number[9]+=1;
        Platform.runLater(()->{
            buttonGetAddress.setDisable(true);
        });
        send.setSubject(key +" "+ number[9]);
        if (number[9]==1){
            send.sendContent("9");
        }
        TaskInfo.address selectedItem = table_address.getSelectionModel().getSelectedItem();
        if ((selectedItem != null)&&(number[9]!=1)) {
            path = " "+selectedItem.getName_address();
            send.sendContent("9"+ path);
            System.out.println("Selected text: " + selectedItem.getName_address());
        }
        else {
           if (number[9]!=1){
               label_warning.setText("Please, choose address!");
               Platform.runLater(()->{
                   buttonGetAddress.setDisable(false);
               });
               return;
           }
        }
        long startTime = System.currentTimeMillis();
        long timeout = 60000; // 60 seconds in milliseconds

        boolean correctResponseReceived = false;
        File oldfile = new File("src/main/resources/com/example/project/file/filelists.txt");
        oldfile.delete();
        while (System.currentTimeMillis() - startTime < timeout && !correctResponseReceived) {
            receiveMail receive = new receiveMail(username, password);
            receive.receiveMail();
            System.out.println(receive.getContent());
            if (receive.getContent().equals(key + " " + number[9]+ " ok")){
                number[9]=0;
                path="";
                Platform.runLater(()->{
                    buttonGetAddress.setDisable(false);
                });
                System.out.println("Nhan file roi");
                // Luu file luon
            }
            if (receive.getContent().equals(key + " " + number[9])){
                Platform.runLater(() -> {
                    System.out.println("ok");
                    Addresslist = FXCollections.observableArrayList();
                    name_address.setCellValueFactory(new PropertyValueFactory<TaskInfo.address, String>("name_address"));
                    loadAddressFromFile();
                    table_address.setItems(Addresslist);
                    buttonGetAddress.setDisable(false);
                });
                break;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!correctResponseReceived){
            buttonGetAddress.setDisable(false);
        }
    }
    public void loadAddressFromFile() {
        File file = new File("src/main/resources/com/example/project/file/filelists.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                TaskInfo.address taskinfo = new TaskInfo.address(line);
                Addresslist.add(taskinfo);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void OnButtonResGet(ActionEvent event){
        Platform.runLater(()->{
            buttonBackAddress.setDisable(true);
        });
        path=" ";
    }
    public void OnButtonSaveGetFile(ActionEvent event){

    }
}

