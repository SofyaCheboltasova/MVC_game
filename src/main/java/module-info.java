module ru.nsu.scheboltasova.gameemvc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens ru.nsu.scheboltasova.gameemvc to javafx.fxml;
    exports ru.nsu.scheboltasova.gameemvc;
}