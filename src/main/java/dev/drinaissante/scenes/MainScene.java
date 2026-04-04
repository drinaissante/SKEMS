package dev.drinaissante.scenes;

import dev.drinaissante.Main;
import dev.drinaissante.managers.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.Objects;

public class MainScene extends SKScene {
    private final ImageView logo = new ImageView(Main.ICON_NO_BG);

    public MainScene(SceneManager sceneManager) {
        super(sceneManager, "Main");

        logo.setFitWidth(150);
        logo.setPreserveRatio(true);

        ImageView gifBackground = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/gifs/main.gif"))));
        gifBackground.fitWidthProperty().bind(getStackPane().widthProperty());
        gifBackground.fitHeightProperty().bind(getStackPane().heightProperty());
        gifBackground.setPreserveRatio(false);
        gifBackground.setSmooth(true);

        getStackPane().getChildren().addFirst(gifBackground);
    }

    @Override
    public void setupToast() {
        if (toastReady)
            return;

        Rectangle dimmer = new Rectangle(scene.getWidth(), scene.getHeight(), Color.color(0, 0, 0, 0.8));
        dimmer.widthProperty().bind(getStackPane().widthProperty());
        dimmer.heightProperty().bind(getStackPane().heightProperty());

        getStackPane().getChildren().add(1, dimmer); // sits above GIF, below root

        super.setupToast();
    }

    @Override
    public void setup() {
        root.setCache(true);

        BorderPane titleBar = sceneManager.getTitleBar().getRoot();
        root.setTop(titleBar);

        // logo above welcome
        Label welcome = new Label("Welcome to SKEMS!");
        welcome.setFont(Font.font("SANS_SERIF", FontWeight.BOLD, 45));
        welcome.setTextFill(Color.valueOf("#ffd870"));
        welcome.setTextAlignment(TextAlignment.CENTER);

        Label selectRole = new Label("Select your role:");
        selectRole.setFont(Font.font("SANS_SERIF", FontWeight.NORMAL, 20));
        selectRole.setTextFill(Color.valueOf("#d9d9d9"));

        Button officerBtn = new Button("Officer");
        officerBtn.setPrefWidth(250);
        officerBtn.getStyleClass().add("role-btn");

        Button memberBtn = new Button("Member");
        memberBtn.setPrefWidth(250);
        memberBtn.getStyleClass().add("role-btn");

        // register / login

        Button registerBtn = new Button("REGISTER");
        registerBtn.setPrefWidth(200);
        registerBtn.getStyleClass().add("btn");

        Button loginBtn = new Button("LOGIN");
        loginBtn.setMinWidth(200);
        loginBtn.getStyleClass().add("btn");

        registerBtn.setOnAction(event -> sceneManager.switchScenes(sceneManager.getRegistrationScene()));
        loginBtn.setOnAction(event -> sceneManager.switchScenes(sceneManager.getLoginScene()));

        HBox registerLogin = new HBox(50, registerBtn, loginBtn);
        registerLogin.setAlignment(Pos.CENTER);

        VBox center = new VBox(15, logo, welcome, selectRole, officerBtn, memberBtn, registerLogin);
        center.setAlignment(Pos.CENTER);

        root.setCenter(center);
    }
}
