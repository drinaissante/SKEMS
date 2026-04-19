package dev.drinaissante.scenes;

import dev.drinaissante.Main;
import dev.drinaissante.managers.SceneManager;
import dev.drinaissante.tabs.*;
import dev.drinaissante.util.Fonts;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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

    private SKTab equipmentsTab, dashboardTab, borrowRequestsTab, historyLogsTab;

    public DashboardScene(SceneManager sceneManager) {
        super(sceneManager, "Dashboard");
    }

    @Override
    public void setup() {
        root.setStyle("-fx-background-color: linear-gradient(to right, #000000 5%, #c89116 100%);");

        // contents
        BorderPane titleBar = sceneManager.getTitleBar().getRoot();
        root.setTop(titleBar);

        // setup tabs
        setupTabs();

        // side panel
        VBox sidePanel = setupSidePanel();
        root.setLeft(sidePanel);

        // main content (dashboard)
        root.setCenter(dashboardTab.getNode());
    }

    public void setupTabs() {
        this.dashboardTab = new DashboardTab().build();

        // TODO actual data / value
        this.equipmentsTab = new EquipmentsTab().build();

        this.borrowRequestsTab = new BorrowRequestsTab().build();

        this.historyLogsTab = new HistoryLogsTab().build();
    }

    // TODO logout profile auth, add other tabs
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
//        ToggleButton reservationsBtn = new ToggleButton("Reservations");
        ToggleButton historyLogBtn = new ToggleButton("History Log");
//        ToggleButton reportsBtn = new ToggleButton("Reports");

        ToggleGroup group = new ToggleGroup();
        dashboardBtn.setToggleGroup(group);
        dashboardBtn.setOnAction(e -> switchMainContent(dashboardTab.getNode()));

        equipmentBtn.setToggleGroup(group);
        equipmentBtn.setOnAction(e -> switchMainContent(equipmentsTab.getNode()));

        borrowBtn.setToggleGroup(group);
        borrowBtn.setOnAction(e -> switchMainContent(borrowRequestsTab.getNode()));

//        reservationsBtn.setToggleGroup(group);

        historyLogBtn.setToggleGroup(group);
        historyLogBtn.setOnAction(e -> switchMainContent(historyLogsTab.getNode()));

//        reportsBtn.setToggleGroup(group);

        dashboardBtn.setSelected(true);

        for (ButtonBase btn : List.of(dashboardBtn, equipmentBtn, borrowBtn, historyLogBtn)) {
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

    // TODO the profile here
    private HBox setupProfile() {
        // TODO fetch profile picture, name
        final Image profile = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/default_profile.jpg")));

        ImageView profileView = new ImageView(profile);
        profileView.setFitWidth(70);
        profileView.setFitHeight(70);
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

    private void switchMainContent(Node node) {
        root.setCenter(node);
    }

}
