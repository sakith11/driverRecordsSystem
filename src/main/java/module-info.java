module com.example.cw2_4_5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cw2_4_5 to javafx.fxml;
    exports com.example.cw2_4_5;
}