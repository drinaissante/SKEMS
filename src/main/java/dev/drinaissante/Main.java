package dev.drinaissante;

import dev.drinaissante.model.Delta;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;
import java.util.Objects;

public class Main extends Application {

    private final Image icon = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sk_icon.jpg")));

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        stage.getIcons().add(icon);

        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        stage.setTitle("SKEMS");

        // contents
        BorderPane titleBar = setupTitleBar(stage);
        root.setTop(titleBar);

        // side panel
        VBox sidePanel = setupSidePanel();
        root.setLeft(sidePanel);

        // main content (dashboard)
        VBox dashboard = setupDashboard();
        root.setCenter(dashboard);

        Scene scene = new Scene(root, 1280, 720, Color.BLACK);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    private BorderPane setupTitleBar(Stage stage) {
        BorderPane titleBar = new BorderPane();
        titleBar.setStyle("-fx-background-color: #1E1E1E; -fx-padding: 10;");

        ImageView logo = new ImageView(icon);
        logo.setFitHeight(24);
        logo.setPreserveRatio(true);

        Label appTitle = new Label("Sine Kultura Equipment Management System");
        appTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        HBox leftBox = new HBox(8, logo, appTitle);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        Button closeBtn = new Button("X");
        Button minimizeBtn = new Button("—");
        Button maximizeBtn = new Button("⬜");

        String btnStyle = "-fx-background-color: transparent; -fx-text-fill: white;";
        closeBtn.setStyle(btnStyle);
        minimizeBtn.setStyle(btnStyle);
        maximizeBtn.setStyle(btnStyle);

        closeBtn.setOnAction(event -> {
            stage.close();
            Platform.exit();
            System.exit(0);
        });
        minimizeBtn.setOnAction(event -> stage.setIconified(true));
        maximizeBtn.setOnAction(event -> stage.setMaximized(!stage.isMaximized()));

        buttons.getChildren().addAll(minimizeBtn, maximizeBtn, closeBtn);

        titleBar.setLeft(leftBox);
        titleBar.setRight(buttons);

        final Delta delta = new Delta();
        titleBar.setOnMousePressed(event -> {
            delta.x = stage.getX() - event.getScreenX();
            delta.y = stage.getY() - event.getScreenY();
        });
        titleBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + delta.x);
            stage.setY(event.getScreenY() + delta.y);
        });

        return titleBar;
    }

    private VBox setupSidePanel() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(180);
        sidebar.setStyle("-fx-background-color: #1E1E1E;");
        sidebar.setSpacing(15);

        // tabs
        ToggleButton dashboardBtn = new ToggleButton("Dashboard");
        ToggleButton equipmentBtn = new ToggleButton("Equipment");
        ToggleButton borrowBtn = new ToggleButton("Borrow Requests");

        ToggleGroup group = new ToggleGroup();
        dashboardBtn.setToggleGroup(group);
        equipmentBtn.setToggleGroup(group);
        borrowBtn.setToggleGroup(group);

        dashboardBtn.setSelected(true);

        // TODO add other tabs

        for (ToggleButton btn : List.of(dashboardBtn, equipmentBtn, borrowBtn)) {
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.CENTER_RIGHT);
            btn.setCursor(Cursor.HAND);

            btn.getStyleClass().add("sidebar-toggle");
        }

        sidebar.getChildren().addAll(dashboardBtn, equipmentBtn, borrowBtn);

        return sidebar;
    }
    // linear-gradient(to right, #292832 0.000%, #272530 8.333%, #24222e 16.667%, #201f2b 25.000%, #1c1b27 33.333%, #181624 41.667%, #141220 50.000%, #100e1c 58.333%, #0c0a18 66.667%, #080614 75.000%, #050311 83.333%, #02000e 91.667%, #00000b 100.000%);

    private VBox setupDashboard() {
        VBox mainContent = new VBox();
        mainContent.setStyle("-fx-background-color: linear-gradient(to right, #292832 0.000%, #272530 8.333%, #24222e 16.667%, #201f2b 25.000%, #1c1b27 33.333%, #181624 41.667%, #141220 50.000%, #100e1c 58.333%, #0c0a18 66.667%, #080614 75.000%, #050311 83.333%, #02000e 91.667%, #00000b 100.000%);" +
                "-fx-padding: 20;");
        mainContent.setSpacing(20);

        Label statsTitle = new Label("Equipment Stats");
        statsTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        HBox statsPanel = new HBox(20);
        statsPanel.setAlignment(Pos.CENTER_LEFT);

        Label available = new Label("Available: 124");
        Label checkedOut = new Label("Checked Out: 18");
        Label pending = new Label("Pending: 5");
        Label overdue = new Label("OVerdue: 2");

        for (Label stat : List.of(available, checkedOut, pending, overdue)) {
            stat.setStyle("-fx-background-color: linear-gradient(to right, #292832 0.000%, #141220 50.000%, #00000b 100.000%); " +
                    "-fx-padding: 20; -fx-text-fill: white;");
        }

        statsPanel.getChildren().addAll(available, checkedOut, pending, overdue);

        mainContent.getChildren().addAll(statsTitle, statsPanel);

        return mainContent;
    }

}