package dev.drinaissante;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private Label label;

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javaFxVersion = System.getProperty("javafx.version");

        label.setText("Hello, JavaFX " + javaFxVersion + "\nRunning on Java " + javaVersion);
    }
}
