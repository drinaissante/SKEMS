package dev.drinaissante;

import dev.drinaissante.model.Delta;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TitleBar {

    private final BorderPane root = new BorderPane();
    private final Stage stage;
    private boolean dragging = false;
    private double targetX, targetY;

    public TitleBar(Stage stage) {
        this.stage = stage;

        setup();
    }

    public void setup() {
        root.setMaxWidth(Double.MAX_VALUE);

        root.getStyleClass().add("title-bar");

        ImageView logo = new ImageView(Main.ICON_NO_BG);
        logo.setFitHeight(24);
        logo.setPreserveRatio(true);

        Label appTitle = new Label("Sine Kultura Equipment Management System");
        appTitle.setTextFill(Color.WHITE);
        appTitle.setFont(Font.font("SANS_SERIF", FontWeight.BOLD, 16));

        HBox leftBox = new HBox(8, logo, appTitle);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        Button closeBtn = new Button("X");
        closeBtn.setPrefWidth(30);
        Button minimizeBtn = new Button("—");
        minimizeBtn.setPrefWidth(30);
        Button maximizeBtn = new Button("⬜");
        maximizeBtn.setPrefWidth(30);

        closeBtn.getStyleClass().add("title-bar-btn");
        minimizeBtn.getStyleClass().add("title-bar-btn");
        maximizeBtn.getStyleClass().add("title-bar-btn");

        closeBtn.setOnAction(event -> {
            stage.close();
            Platform.exit();
            System.exit(0);
        });
        minimizeBtn.setOnAction(event -> stage.setIconified(true));
        maximizeBtn.setOnAction(event -> stage.setMaximized(!stage.isMaximized()));

        buttons.getChildren().addAll(minimizeBtn, maximizeBtn, closeBtn);

        root.setLeft(leftBox);
        root.setRight(buttons);

        final Delta delta = new Delta();

        root.setOnMousePressed(event -> {
            if (!(event.getTarget() instanceof Button)) { // ignore clicks on buttons
                delta.x = event.getSceneX();
                delta.y = event.getSceneY();
                dragging = true;
            }
        });

        root.setOnMouseDragReleased(event -> dragging = false);

        root.setOnMouseDragged(event -> {
            if (dragging) {
                targetX = event.getScreenX() - delta.x;
                targetY = event.getScreenY() - delta.y;

                stage.setX(stage.getX() + (targetX - stage.getX()));
                stage.setY(stage.getY() + (targetY - stage.getY()));
            }
        });
    }

    public BorderPane getRoot() {
        return root;
    }
}
