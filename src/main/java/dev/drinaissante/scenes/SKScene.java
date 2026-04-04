package dev.drinaissante.scenes;

import dev.drinaissante.Main;
import dev.drinaissante.managers.SceneManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public abstract class SKScene {
    protected final SceneManager sceneManager;
    protected final String title;
    protected final BorderPane root = new BorderPane();
    private final StackPane stackPane = new StackPane();
    private final VBox toastContainer = new VBox(10);
    protected Scene scene;
    protected boolean doneSetup = false;
    protected boolean toastReady = false;    // tracks setupToast()

    public SKScene(SceneManager sceneManager, String title) {
        this.sceneManager = sceneManager;
        this.title = title;

        stackPane.getChildren().addFirst(root);

        this.scene = new Scene(stackPane, 1280, 720);
        scene.getStylesheets().add(Main.STYLES);

        root.setCache(true);
        stackPane.setCache(true);
    }

    public void setupToast() {
        if (toastReady) return;

        toastContainer.setPadding(new Insets(20));
        toastContainer.setMouseTransparent(true);
        toastContainer.setAlignment(Pos.BOTTOM_LEFT);
        toastContainer.setFillWidth(false);

        stackPane.getChildren().add(toastContainer);
        StackPane.setAlignment(toastContainer, Pos.BOTTOM_LEFT);

        toastReady = true;  // <-- use separate flag here
    }

    public VBox getToastContainer() {
        return toastContainer;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDoneSetup() {
        return doneSetup;
    }

    public Scene getScene() {
        return scene;
    }

    public abstract void setup();
}
