package dev.drinaissante.scenes;

import dev.drinaissante.Main;
import dev.drinaissante.managers.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class ForgotPassScene extends SKScene {

    public ForgotPassScene(SceneManager sceneManager) {
        super(sceneManager, "Forgot Password");
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

        Label resetSkemsPassword = new Label("Reset SKEMS Password");
        resetSkemsPassword.setFont(Font.font("SANS_SERIF", FontWeight.BOLD, 28));
        resetSkemsPassword.setTextFill(Color.valueOf("#d9d9d9"));
        resetSkemsPassword.setTextAlignment(TextAlignment.CENTER);

        // logo / resetSkemsPassword
        HBox logoResetSkems = new HBox(15, logo, resetSkemsPassword);
        logoResetSkems.setAlignment(Pos.CENTER);

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

        // new pw
        TextField pwField = new TextField();
        pwField.setPromptText("Password");
        pwField.setMaxWidth(200);
        pwField.getStyleClass().add("text-field");

        // confirm pw
        TextField confirmField = new TextField();
        confirmField.setPromptText("Confirm Password");
        confirmField.setMaxWidth(200);
        confirmField.getStyleClass().add("text-field");

        // go back
        Button goBackBtn = new Button("Go back");
        goBackBtn.setMaxWidth(150);
        goBackBtn.getStyleClass().add("btn");
        goBackBtn.setOnAction(e -> sceneManager.switchScenes(sceneManager.getLoginScene()));

        VBox sidePanel = new VBox(20, logoResetSkems, studentNumField, pwField, goBackBtn);
        sidePanel.setStyle("-fx-background-color: #aa7c13; -fx-padding: 30;");
        sidePanel.setAlignment(Pos.CENTER);

        root.setLeft(sidePanel);
    }
}
