package dev.drinaissante.scenes;

import dev.drinaissante.managers.SceneManager;
import javafx.scene.Scene;

public abstract class SKScene {
    protected Scene scene;
    protected final SceneManager sceneManager;
    protected final String title;

    public SKScene(SceneManager sceneManager, String title) {
        this.sceneManager = sceneManager;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Scene getScene() {
        return scene;
    }

    public abstract void setup();
}
