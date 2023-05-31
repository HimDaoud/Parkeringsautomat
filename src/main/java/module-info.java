module com.example.parkering {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.parkering to javafx.fxml;
    exports com.example.parkering;
}