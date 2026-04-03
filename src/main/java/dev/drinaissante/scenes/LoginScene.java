package dev.drinaissante.scenes;

import dev.drinaissante.Main;
import dev.drinaissante.managers.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class LoginScene extends SKScene {
    public LoginScene(SceneManager sceneManager) {
        super(sceneManager, "Login");
    }

    @Override
    public void setup() {
        root.setStyle("-fx-background-color: linear-gradient(to right, #000000 5%, #c89116 100%);");

        // title bar
        BorderPane titleBar = sceneManager.getTitleBar().getRoot();
        root.setTop(titleBar);

        // logo above welcome
        ImageView logo = new ImageView(Main.ICON_NO_BG);
        logo.setFitWidth(80);
        logo.setPreserveRatio(true);

        Label welcome = new Label("Welcome to SKEMS!");
        welcome.setFont(Font.font("SANS_SERIF", FontWeight.BOLD, 28));
        welcome.setTextFill(Color.valueOf("#d9d9d9"));
        welcome.setTextAlignment(TextAlignment.CENTER);

        // logo / welcome
        VBox logoWelcome = new VBox(15, logo, welcome);
        logoWelcome.setAlignment(Pos.CENTER);


        // student num
        TextField studentNumField = new TextField();
        studentNumField.setPromptText("Student Number");
        studentNumField.setMaxWidth(200);
        studentNumField.getStyleClass().add("text-field");
        // make sure to only accept 10 digits
        studentNumField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() != 10) {
                studentNumField.setStyle("-fx-text-fill: red;");
            } else {
                studentNumField.setStyle("-fx-text-fill: green;");
            }
        });

        // pw
        TextField pwField = new TextField();
        pwField.setPromptText("Password");
        pwField.setMaxWidth(200);
        pwField.getStyleClass().add("text-field");

        // login
        Button loginBtn = new Button("Login");
        loginBtn.setMaxWidth(150);
        loginBtn.getStyleClass().add("btn");
        loginBtn.setOnAction(e -> {
            // TODO make sure studentNumField has only 10 digits
            // TODO database (check if auth / has account)
            // TODO set scene to dashboard
        });

        // forgot pw
        Text forgotPw = new Text("Forgot Password?");
        forgotPw.setCursor(Cursor.HAND);
        forgotPw.setOnMouseClicked(event -> sceneManager.switchScenes(sceneManager.getForgotPassScene()));

        // create account (registrationScene)
        Text createAccount = new Text("Create Account");
        createAccount.setCursor(Cursor.HAND);
        createAccount.setOnMouseClicked(event -> sceneManager.switchScenes(sceneManager.getRegistrationScene()));

        VBox forgotCA = new VBox(10, forgotPw, createAccount);
        forgotCA.setAlignment(Pos.CENTER);

        // cancel button (mainScene)
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setCursor(Cursor.HAND);
        cancelBtn.setOnMouseClicked(event -> sceneManager.switchScenes(sceneManager.getMainScene()));

        VBox sidePanel = new VBox(20, logoWelcome, studentNumField, pwField, loginBtn, forgotCA);
        sidePanel.setStyle("-fx-background-color: #aa7c13; -fx-padding: 30;");
        sidePanel.setAlignment(Pos.CENTER);

        root.setLeft(sidePanel);
    }
}
