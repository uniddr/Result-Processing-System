module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires java.mail;
    requires passay;

    opens org.example to javafx.fxml;
    exports org.example;
}