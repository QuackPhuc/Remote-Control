module com.example.projectremovecontrol {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires activation;
    requires org.jsoup;
    requires java.sql;
    requires com.github.kwhat.jnativehook;


    opens com.example.project to javafx.fxml;
    exports com.example.project;
    exports com.example.project.mail;
    opens com.example.project.mail to javafx.fxml;
}