module com.example.interpreterexam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.interpreterexam to javafx.fxml;
    exports com.example.interpreterexam;
}