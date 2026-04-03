package dev.drinaissante.managers;

import dev.drinaissante.TitleBar;
import dev.drinaissante.scenes.DashboardScene;
import dev.drinaissante.scenes.MainScene;
import dev.drinaissante.scenes.RegistrationScene;
import dev.drinaissante.scenes.SKScene;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private final Stage stage;
    private final TitleBar titleBar;

    private final DashboardScene dashboardScene;
    private final MainScene mainScene;
    private final RegistrationScene registrationScene;

    private Scene currentScene;

    public SceneManager(Stage stage) {
        this.stage = stage;
        this.titleBar = new TitleBar(stage);

        this.dashboardScene = new DashboardScene(this);
        this.mainScene = new MainScene(this);
        this.registrationScene = new RegistrationScene(this);
    }

    public void switchScenes(SKScene skScene) {
        skScene.setup();

        this.currentScene = skScene.getScene();
        stage.setScene(skScene.getScene());

        stage.setTitle("SKEMS | " + skScene.getTitle());
    }

    public TitleBar getTitleBar() {
        return titleBar;
    }

    public DashboardScene getDashboardScene() {
        return dashboardScene;
    }

    public MainScene getMainScene() {
        return mainScene;
    }

    public RegistrationScene getRegistrationScene() {
        return registrationScene;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public Stage getStage() {
        return stage;
    }
}
