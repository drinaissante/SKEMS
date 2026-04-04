package dev.drinaissante.scenes;

import dev.drinaissante.Main;
import dev.drinaissante.managers.ModalManager;
import dev.drinaissante.managers.SceneManager;
import dev.drinaissante.util.Fonts;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Objects;

public class LoginScene extends SKScene {
    private final ImageView gifBackground = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/gifs/login.gif"))));

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
        welcome.setTextFill(Color.valueOf("#ffd870"));
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
            boolean passed = !(studentNumField.getText().isEmpty() && pwField.getText().isEmpty());

            if (!passed) {
                ModalManager.createModal(this, 3, "All fields must be filled to continue!");
            }

            // TODO make sure studentNumField has only 10 digits
            // TODO database (check if auth / has account)
            // TODO set scene to dashboard
        });

        // forgot pw
        Text forgotPw = new Text("Forgot Password?");
        forgotPw.setCursor(Cursor.HAND);
        forgotPw.setFill(Color.valueOf("#d9d9d9"));
        forgotPw.setOnMouseClicked(event -> sceneManager.switchScenes(sceneManager.getForgotPassScene()));
        forgotPw.setOnMouseEntered(event -> forgotPw.setFill(Color.valueOf("#ffd870")));
        forgotPw.setOnMouseExited(event -> forgotPw.setFill(Color.valueOf("#d9d9d9")));


        // create account (registrationScene)
        Text createAccount = new Text("Create Account");
        createAccount.setFill(Color.valueOf("#d9d9d9"));
        createAccount.setCursor(Cursor.HAND);
        createAccount.setOnMouseClicked(event -> sceneManager.switchScenes(sceneManager.getRegistrationScene()));
        createAccount.setOnMouseEntered(event -> createAccount.setFill(Color.valueOf("#ffd870")));
        createAccount.setOnMouseExited(event -> createAccount.setFill(Color.valueOf("#d9d9d9")));

        VBox forgotCA = new VBox(10, forgotPw, createAccount);
        forgotCA.setAlignment(Pos.CENTER);

        // cancel button (mainScene)
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setCursor(Cursor.HAND);
        cancelBtn.setOnMouseClicked(event -> sceneManager.switchScenes(sceneManager.getMainScene()));

        VBox sidePanel = new VBox(20, logoWelcome, studentNumField, pwField, loginBtn, forgotCA);
        sidePanel.setStyle("-fx-background-color: #111111; -fx-padding: 30;");
        sidePanel.setAlignment(Pos.CENTER);

        sidePanel.setPrefWidth(400);
        sidePanel.setMinWidth(400);
        sidePanel.setMaxWidth(400);

        HBox mainContent = new HBox();

        // GIF
        gifBackground.setPreserveRatio(false);
        gifBackground.fitWidthProperty().bind(getStackPane().widthProperty().subtract(sidePanel.widthProperty()));
        gifBackground.setTranslateX(0);
        gifBackground.setTranslateY(0);
        gifBackground.setSmooth(true);

        Region overlay = new Region();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.5);");


        Text mainText = new Text("Same Passion, New Vision.");
        mainText.setFont(Font.font(Fonts.COMIC_NEUE_BOLD, 40));
        mainText.setFill(Color.valueOf("#d9d9d9"));

        VBox center = new VBox(mainText);
        center.setAlignment(Pos.CENTER);

        Text creditText = new Text("shot by: shooter");
        creditText.setFont(Font.font(Fonts.COMIC_NEUE, 20));
        creditText.setFill(Color.valueOf("#d9d9d9"));

        StackPane.setAlignment(creditText, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(creditText, new Insets(10));

        StackPane gifWrapper = new StackPane(gifBackground, overlay, center, creditText);
        gifBackground.fitHeightProperty().bind(getStackPane().heightProperty().subtract(titleBar.heightProperty()));

        mainContent.getChildren().addAll(sidePanel, gifWrapper);
        root.setCenter(mainContent);
    }
}
