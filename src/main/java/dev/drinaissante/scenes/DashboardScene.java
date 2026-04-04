package dev.drinaissante.scenes;

import dev.drinaissante.Main;
import dev.drinaissante.managers.SceneManager;
import dev.drinaissante.util.Fonts;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;

public class DashboardScene extends SKScene {

    public DashboardScene(SceneManager sceneManager) {
        super(sceneManager, "Dashboard");
    }

    @Override
    public void setup() {
        root.setStyle("-fx-background-color: linear-gradient(to right, #000000 5%, #c89116 100%);");

        // contents
        BorderPane titleBar = sceneManager.getTitleBar().getRoot();
        root.setTop(titleBar);

        // side panel
        VBox sidePanel = setupSidePanel();
        root.setLeft(sidePanel);

        // main content (dashboard)
        VBox dashboard = setupMainContent();
        root.setCenter(dashboard);
    }

    // TODO logout profile, add other tabs
    private VBox setupSidePanel() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(275);
        sidebar.setStyle("-fx-background-color: #111111;");
        sidebar.setSpacing(15);

        // profile
        HBox profileBox = setupProfile();

        sidebar.getChildren().add(profileBox);

        // tabs
        ToggleButton dashboardBtn = new ToggleButton("Dashboard");
        ToggleButton equipmentBtn = new ToggleButton("Equipment");
        ToggleButton borrowBtn = new ToggleButton("Borrow Requests");
        ToggleButton reservationsBtn = new ToggleButton("Reservations");
        ToggleButton historyLogBtn = new ToggleButton("History Log");
        ToggleButton reportsBtn = new ToggleButton("Reports");

        ToggleGroup group = new ToggleGroup();
        dashboardBtn.setToggleGroup(group);
        equipmentBtn.setToggleGroup(group);
        borrowBtn.setToggleGroup(group);
        reservationsBtn.setToggleGroup(group);
        historyLogBtn.setToggleGroup(group);
        reportsBtn.setToggleGroup(group);

        dashboardBtn.setSelected(true);

        for (ButtonBase btn : List.of(dashboardBtn, equipmentBtn, borrowBtn, reservationsBtn, historyLogBtn, reportsBtn)) {
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.CENTER_RIGHT);
            btn.setCursor(Cursor.HAND);

            btn.getStyleClass().add("sidebar-toggle");

            sidebar.getChildren().add(btn);
        }

        Button logoutBtn = new Button("Logout");
        logoutBtn.setMaxWidth(100);
        logoutBtn.setCursor(Cursor.HAND);
        logoutBtn.getStyleClass().add("sidebar-toggle");
        logoutBtn.setStyle("-fx-padding: 10;");

        logoutBtn.setOnAction(event -> {
            // TODO logout profile auth

            sceneManager.switchScenes(sceneManager.getMainScene());
        });

        HBox logoutBox = new HBox(logoutBtn);
        logoutBox.setAlignment(Pos.BASELINE_LEFT);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(spacer, logoutBox);

        return sidebar;
    }

    private VBox setupMainContent() {
        final VBox mainContent = new VBox();

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
        Label overdue = new Label("Overdue: 2");

        for (Label stat : List.of(available, checkedOut, pending, overdue)) {
            stat.setStyle("-fx-background-color: linear-gradient(to right, #292832 0.000%, #141220 50.000%, #00000b 100.000%); " +
                    "-fx-padding: 20; -fx-text-fill: white;");
        }

        statsPanel.getChildren().addAll(available, checkedOut, pending, overdue);

        mainContent.getChildren().addAll(statsTitle, statsPanel);

        return mainContent;
    }

    // TODO the profile here
    private HBox setupProfile() {
        // TODO fetch profile picture, name
        final Image profile = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/profile.jpg")));

        ImageView profileView = new ImageView(profile);
        profileView.setFitWidth(90);
        profileView.setFitHeight(90);
        profileView.setPreserveRatio(false);

        final Circle clip = new Circle();
        clip.radiusProperty().set(30);
        clip.centerXProperty().bind(profileView.fitWidthProperty().divide(2));
        clip.centerYProperty().bind(profileView.fitHeightProperty().divide(2));
        profileView.setClip(clip);

        final Circle border = new Circle();
        border.radiusProperty().set(32); // slightly larger for border
        border.centerXProperty().bind(profileView.fitWidthProperty().divide(2));
        border.centerYProperty().bind(profileView.fitHeightProperty().divide(2));
        border.setStroke(Color.GOLD);
        border.setFill(Color.TRANSPARENT);

        StackPane avatar = new StackPane(border, profileView);

        // TODO fetch name
        Text name = new Text("Valencia, Apollo Eildrick");
        name.setFill(Color.valueOf("#d9d9d9"));
        name.setFont(Font.font(Fonts.COMIC_NEUE_BOLD, 16));

        Text viewProfile = new Text("View Profile");
        viewProfile.setFill(Color.valueOf("#d9d9d9"));
        viewProfile.setFont(Font.font(Fonts.COMIC_NEUE, 14));
        viewProfile.setCursor(Cursor.HAND);
        // TODO -> clickable
        viewProfile.setOnMouseEntered(event -> viewProfile.setFill(Color.valueOf("#ffd870")));
        viewProfile.setOnMouseExited(event -> viewProfile.setFill(Color.valueOf("#d9d9d9")));

        VBox nameViewBox = new VBox(name, viewProfile);
        nameViewBox.setAlignment(Pos.CENTER_LEFT);

        HBox profileBox = new HBox(10, avatar, nameViewBox);
        profileBox.setAlignment(Pos.TOP_CENTER);

        return profileBox;
    }
}
