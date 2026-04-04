package dev.drinaissante.scenes;

import dev.drinaissante.Main;
import dev.drinaissante.managers.ModalManager;
import dev.drinaissante.managers.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class RegistrationScene extends SKScene {

    private final ImageView logo = new ImageView(Main.ICON_NO_BG);
    private static final List<String> OFFICER_ROLES = List.of("Photographer", "Videographer", "Video Editor", "Graphics Designer", "Layout Editor");

    public RegistrationScene(SceneManager sceneManager) {
        super(sceneManager, "Registration");

        logo.setFitWidth(80);
        logo.setPreserveRatio(true);
    }

    @Override
    public void setup() {
        root.setStyle("-fx-background-color: linear-gradient(to right, #000000 5%, #c89116 100%);");

        // title bar
        BorderPane titleBar = sceneManager.getTitleBar().getRoot();
        root.setTop(titleBar);

        // logo above text
        Label registration = new Label("SKEMS Registration");
        registration.setFont(Font.font("SANS_SERIF", FontWeight.BOLD, 33));
        registration.setTextFill(Color.valueOf("#d9d9d9"));
        registration.setTextAlignment(TextAlignment.CENTER);

        HBox logoRegistration = new HBox(20, logo, registration);
        logoRegistration.setAlignment(Pos.CENTER);

        VBox textFields = getTextFields();

        // terms and condition
        CheckBox checkBox = new CheckBox();
        checkBox.setStyle("-fx-background-color: #d9d9d9");

        Text tnc = new Text("I agree to the terms and conditions.");
        tnc.setOnMouseClicked(event -> checkBox.setSelected(!checkBox.isSelected()));

        HBox checkConditions = new HBox(10, checkBox, tnc);
        checkConditions.setAlignment(Pos.CENTER);

        // cancel
        Button cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().add("btn");
        cancelBtn.setOnAction(e -> sceneManager.switchScenes(sceneManager.getMainScene()));

        // create account
        Button createAccount = new Button("Create Account");
        createAccount.getStyleClass().add("btn");
        createAccount.setOnAction(e -> {
            boolean passed = textFields.getChildren().stream()
                    .filter(node -> node instanceof TextField)
                    .map(node -> (TextField) node)
                    .allMatch(tf -> tf.getText() != null && !tf.getText().isBlank());

            if (!passed) {
                ModalManager.createModal(this, 3, "All fields must be filled to continue!");
            }

            // TODO make sure all fields are filled.
            // TODO database - check if student number is already used.
        });

        HBox cancelCreate = new HBox(10, cancelBtn, createAccount);
        cancelCreate.setAlignment(Pos.CENTER);

        VBox sidePanel = new VBox(30, logoRegistration, textFields, checkConditions, cancelCreate);
        sidePanel.setStyle("-fx-background-color: #aa7c13; -fx-padding: 30;");
        sidePanel.setAlignment(Pos.CENTER);

        root.setLeft(sidePanel);
    }

    private VBox getTextFields() {
        // name
        TextField nameField = new TextField();
        nameField.setPromptText("Given Name");
        nameField.setMaxWidth(200);
        nameField.getStyleClass().add("text-field");

        // last name
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        lastNameField.setMaxWidth(200);
        lastNameField.getStyleClass().add("text-field");

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

        // roles
        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.setPromptText("Role/s");
        roleBox.setMaxWidth(200);
        roleBox.getItems().addAll(OFFICER_ROLES);
        roleBox.getStyleClass().add("text-field");

        // pw
        TextField pwField = new TextField();
        pwField.setPromptText("Password");
        pwField.setMaxWidth(200);
        pwField.getStyleClass().add("text-field");

        // confirm pw
        TextField confirmPwField = new TextField();
        confirmPwField.setPromptText("Confirm Password");
        confirmPwField.setMaxWidth(200);
        confirmPwField.getStyleClass().add("text-field");

        VBox textFields = new VBox(10, nameField, lastNameField, studentNumField, roleBox, pwField, confirmPwField);
        textFields.setAlignment(Pos.CENTER);

        return textFields;
    }
}
