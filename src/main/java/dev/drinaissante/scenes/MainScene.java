package dev.drinaissante.scenes;

import dev.drinaissante.Main;
import dev.drinaissante.managers.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class MainScene extends SKScene {

    private final BorderPane root = new BorderPane();

    public MainScene(SceneManager sceneManager) {
        super(sceneManager, "Main");

        this.scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(Main.STYLES);
    }

    public BorderPane getRoot() {
        return root;
    }

    @Override
    public void setup() {
        root.setStyle("-fx-background-color: linear-gradient(to right, #000000 5%, #c89116 100%);");

        BorderPane titleBar = sceneManager.getTitleBar().getRoot();
        root.setTop(titleBar);

        // logo above welcome
        ImageView logo = new ImageView(Main.ICON_NO_BG);
        logo.setFitWidth(150);
        logo.setPreserveRatio(true);

        Label welcome = new Label("Welcome to SKEMS!");
        welcome.setFont(Font.font("SANS_SERIF", FontWeight.BOLD, 45));
        welcome.setTextFill(Color.WHITE);
        welcome.setTextAlignment(TextAlignment.CENTER);

        Label selectRole = new Label("Select your role:");
        selectRole.setFont(Font.font("SANS_SERIF", FontWeight.NORMAL, 20));
        selectRole.setTextFill(Color.WHITE);

        Button officerBtn = new Button("Officer");
        officerBtn.setPrefWidth(250);
        officerBtn.getStyleClass().add("role-btn");

        Button memberBtn = new Button("Member");
        memberBtn.setPrefWidth(250);
        memberBtn.getStyleClass().add("role-btn");

        Button registerBtn = new Button("REGISTER");
        registerBtn.setPrefWidth(200);
        registerBtn.getStyleClass().add("register-btn");

        registerBtn.setOnAction(event -> sceneManager.switchScenes(sceneManager.getRegistrationScene()));

        VBox center = new VBox(15, logo, welcome, selectRole, officerBtn, memberBtn, registerBtn);
        center.setAlignment(Pos.CENTER);

        root.setCenter(center);
    }
}
