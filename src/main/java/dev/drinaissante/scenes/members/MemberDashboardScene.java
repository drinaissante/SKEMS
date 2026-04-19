package dev.drinaissante.scenes.members;

import dev.drinaissante.Main;
import dev.drinaissante.managers.SceneManager;
import dev.drinaissante.scenes.SKScene;
import dev.drinaissante.tabs.SKTab;
import dev.drinaissante.tabs.members.MemberEquipmentsTab;
import dev.drinaissante.util.Fonts;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Objects;

public class MemberDashboardScene extends SKScene {

    private SKTab equipmentsTab;

    public MemberDashboardScene(SceneManager sceneManager) {
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
        root.setCenter(equipmentsTab.getNode());
    }

    public void setupTabs() {
        // TODO actual data / value
        this.equipmentsTab = new MemberEquipmentsTab().build();
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
        ToggleButton equipmentBtn = new ToggleButton("Equipment");

        ToggleGroup group = new ToggleGroup();

        equipmentBtn.setToggleGroup(group);
        equipmentBtn.setOnAction(e -> switchMainContent(equipmentsTab.getNode()));
        equipmentBtn.setSelected(true);

        equipmentBtn.setMaxWidth(Double.MAX_VALUE);
        equipmentBtn.setAlignment(Pos.CENTER_RIGHT);
        equipmentBtn.setCursor(Cursor.HAND);

        equipmentBtn.getStyleClass().add("sidebar-toggle");

        sidebar.getChildren().add(equipmentBtn);

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
        Text name = new Text("Torres, Treb");
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
