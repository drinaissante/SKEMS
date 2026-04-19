package dev.drinaissante.tabs;

import dev.drinaissante.Main;
import dev.drinaissante.util.Fonts;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Objects;
import java.util.Random;

public class HistoryLogsTab implements SKTab {

    private final HBox mainContent = new HBox(30);

    // TODO temp
    private final Image profileImg = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/profile.jpg")));
    // TODO temp equipmentImg
    private final Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/camera_1.png")));
    private final Random rand = new Random();

    @Override
    public Node getNode() {
        return this.mainContent;
    }

    @Override
    public SKTab build() {
        mainContent.setStyle("-fx-background-color: linear-gradient(to left, #292832 0.000%, #272530 8.333%, #24222e 16.667%, #201f2b 25.000%, #1c1b27 33.333%, #181624 41.667%, #141220 50.000%, #100e1c 58.333%, #0c0a18 66.667%, #080614 75.000%, #050311 83.333%, #02000e 91.667%, #00000b 100.000%);" +
                "-fx-padding: 30;");

        // TODO search name
        TextField searchField = new TextField();
        searchField.setPromptText("Search:");
        searchField.setPrefWidth(500);
        searchField.setStyle("""
                    -fx-padding: 10;
                    -fx-background-color: #a6a6a6;
                    -fx-background-radius: 14px;
                """);

        // setup requests
        VBox requests = setupRequests();

        // TODO messages
        ScrollPane scrollPane = new ScrollPane(requests);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.getStyleClass().add("scroll-pane");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        HBox searchBox = new HBox(searchField);

        VBox searchScroll = new VBox(10, searchBox, scrollPane);

        VBox rightPanel = setupRightPanel();
        rightPanel.setFillWidth(true);

        HBox.setHgrow(mainContent, Priority.ALWAYS);
        mainContent.getChildren().addAll(searchScroll, rightPanel);

        return this;
    }

    // TODO database fetch requests (owner, requester_name, reason, image, equipment_name, date)
    private VBox setupRequests() {
        // profile should just be the item itself
        VBox vbox = new VBox();
        vbox.setPrefWidth(600);
        vbox.setMaxWidth(600);


        // TODO fetch from database
        // make per message a different transparency
        for (int i = 0; i < 12; i++) {
            HBox hbox = new HBox(10);
            HBox.setHgrow(hbox, Priority.ALWAYS);
            boolean even = i % 2 == 0;

            hbox.setStyle("""
                    -fx-background-color: %s;
                    -fx-padding: 10 30 10 30;
                    -fx-text-fill: black;
                    """.formatted(even ? "#a6a6a6" : "#a6a6a680")
            );
            hbox.setOnMouseEntered(event -> hbox.setStyle("""
                    -fx-background-color: %s;
                    -fx-padding: 10 30 10 30;
                    -fx-text-fill: black;
                    """.formatted("#a6a6a620")
            ));

            hbox.setOnMouseExited(event -> hbox.setStyle("""
                    -fx-background-color: %s;
                    -fx-padding: 10 30 10 30;
                    -fx-text-fill: black;
                    """.formatted(even ? "#a6a6a6" : "#a6a6a680")
            ));

            // TODO temp | fetch profile auth
            ImageView profileView = new ImageView(profileImg);
            profileView.setFitHeight(30);
            profileView.setFitWidth(30);

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

            String requester = rand.nextBoolean() ? "Torres, Trevor Basti" : "Ayoki Konnichiwa";
            String equpm = rand.nextBoolean() ? "Nikon Z 30" : "Sony Mavica";

            // TODO fetch from database
            Label requesterName = new Label(requester);
            requesterName.setFont(Font.font(Fonts.COMIC_NEUE_BOLD, FontWeight.BOLD, 16));

            Label equipment = new Label(equpm);
            equipment.setFont(Font.font(Fonts.COMIC_NEUE, FontWeight.NORMAL, 14));

            VBox nameEquipmentBox = new VBox(5, requesterName, equipment);

            Label dateLbl = new Label("September 13, 2025");
            dateLbl.setTextFill(Color.DARKGRAY);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            hbox.getChildren().addAll(profileView, nameEquipmentBox, spacer, dateLbl);

            vbox.getChildren().add(hbox);
        }

        return vbox;
    }

    // TODO request / process request btn / date and time
    private VBox setupRightPanel() {
        VBox mainContent = new VBox(20);
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setStyle("-fx-background-color: #a6a6a680; -fx-padding: 10;");


        // TODO requesterName, reason
        String requesterName = "Torres, Trevor Basti";
        String position = "Videographer";

        Label requestLbl = new Label(requesterName);
        requestLbl.setTextFill(Color.WHITE);
        Label requestPositionLbl = new Label(position);
        requestPositionLbl.setTextFill(Color.WHITE);

        VBox requesterPosition = new VBox(10, requestLbl, requestPositionLbl);
        requesterPosition.setAlignment(Pos.TOP_CENTER);

        Label requestText = new Label("Request Information");
        requestText.setStyle("-fx-background-color: #fccb52; -fx-padding: 10;");


        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);


        Text equipment_name = new Text("Equipment: Nikon Z 30");
        equipment_name.setFill(Color.WHITE);
        Text purpose = new Text("Purpose: Video Shooting (Outside Activity)");
        purpose.setFill(Color.WHITE);
        Text dateOfUse = new Text("Date of Use: September 13, 2025");
        dateOfUse.setFill(Color.WHITE);
        Text dateOfReturn = new Text("Date of Return: September 15, 2025");
        dateOfReturn.setFill(Color.WHITE);

        // TODO equipment_image
        VBox equipmentBox = new VBox(10, imageView, equipment_name, purpose, dateOfUse, dateOfReturn);
        equipmentBox.setAlignment(Pos.CENTER);
        equipmentBox.setMaxWidth(200);
        equipmentBox.setPrefWidth(200);
        equipmentBox.setStyle("""
                    -fx-background-color: gray;
                    -fx-padding: 20;
                """);

        Label requestFeedback = new Label("Request Feedback");
        requestFeedback.setStyle("-fx-background-color: #fccb52; -fx-padding: 10;");

        Label requestResult = new Label("APPROVED");
        requestResult.setTextFill(Color.valueOf("#7ed957"));
        requestResult.setFont(Font.font(Fonts.COMIC_NEUE_BOLD, FontWeight.BOLD, 16));

        HBox.setHgrow(mainContent, Priority.ALWAYS);

        mainContent.getChildren().addAll(requesterPosition, requestText, equipmentBox, requestFeedback, requestResult);

        return mainContent;
    }
}
