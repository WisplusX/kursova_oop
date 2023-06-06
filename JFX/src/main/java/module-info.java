module com.kursova.jfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kursova.jfx to javafx.fxml;
    exports com.kursova.jfx;
}