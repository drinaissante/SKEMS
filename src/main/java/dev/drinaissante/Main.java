package dev.drinaissante;

import dev.drinaissante.managers.SceneManager;
import dev.drinaissante.scenes.MainScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application {

    public static final Image ICON_BG = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sk_icon.jpg")));
    public static final Image ICON_NO_BG = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sk_icon_no_bg.png")));
    public static final String STYLES = Objects.requireNonNull(Main.class.getResource("/styles.css")).toExternalForm();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.getIcons().add(ICON_NO_BG);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        stage.setTitle("SKEMS");

        SceneManager sceneManager = new SceneManager(stage);

        // Setup the main page (role selection)
        MainScene mainScene = sceneManager.getMainScene();

        sceneManager.switchScenes(mainScene);
        stage.show();
    }
}