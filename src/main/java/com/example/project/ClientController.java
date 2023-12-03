package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import com.example.project.mail.sendMail;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController {
    public String textControl;
    @FXML
    private Button buttonsd;

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

    public void keyloggerFrame(String keyloggerFile) throws IOException {
        // Tạo đối tượng `JTextArea`
        JTextArea textArea = new JTextArea();

        // Đọc nội dung của file keylooger
        File file = new File(keyloggerFile);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            textArea.append(scanner.nextLine() + "\n");
        }
        scanner.close();

        // Thêm đối tượng `JTextArea` vào giao diện GUI???
        JFrame frame = new JFrame();
        frame.add(textArea);
        frame.pack();
        frame.setVisible(true);
    }

    public void saveas(String sourceFilePath){

        // Create a file dialog to choose the destination file location
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
    }

    private sendMail send = new sendMail(to, from,password,subject);
    public void buttonShutdown(ActionEvent eventc1) throws IOException {
        System.out.println("shutdown");
        textControl = "shutdown";
//        send.send(textControl);
        buttonsd.setVisible(false);
    }

    public void buttonSleep(ActionEvent eventc1) throws IOException{
        System.out.println("sleep");
        textControl = "sleep";
//        send.send(textControl);
    }
    public void buttonRestart(ActionEvent eventc1) throws IOException{
        System.out.println("restart");
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
}

