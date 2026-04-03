package dev.drinaissante.scenes;

import dev.drinaissante.Main;
import dev.drinaissante.managers.SceneManager;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class RegistrationScene extends SKScene {
    private final BorderPane root = new BorderPane();

    public RegistrationScene(SceneManager sceneManager) {
        super(sceneManager, "Registration");

        this.scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(Main.STYLES);
    }

    @Override
    public void setup() {
        root.setStyle("-fx-background-color: linear-gradient(to right, #000000 5%, #c89116 100%);");

        // contents
        BorderPane titleBar = sceneManager.getTitleBar().getRoot();
        root.setTop(titleBar);

        // side panel
//        VBox sidePanel = setupSidePanel();
//        root.setLeft(sidePanel);

        // main content (dashboard)
//        VBox dashboard = setupDashboard();
//        root.setCenter(dashboard);
    }
}
