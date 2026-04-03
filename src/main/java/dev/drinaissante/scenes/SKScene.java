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
    protected final Scene scene;
    private final StackPane stackPane = new StackPane();
    private final VBox toastContainer = new VBox(10);
    protected boolean doneSetup = false;

    public SKScene(SceneManager sceneManager, String title) {
        this.sceneManager = sceneManager;
        this.title = title;

        stackPane.getChildren().add(root);
        setupToast();

        this.scene = new Scene(stackPane, 1280, 720);
        scene.getStylesheets().add(Main.STYLES);
    }

    protected void setupToast() {
        toastContainer.setPadding(new Insets(20));
        toastContainer.setMouseTransparent(true);

        toastContainer.setAlignment(Pos.BOTTOM_LEFT);
        toastContainer.setFillWidth(false);

        stackPane.getChildren().add(toastContainer);
        StackPane.setAlignment(toastContainer, Pos.BOTTOM_LEFT);
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
