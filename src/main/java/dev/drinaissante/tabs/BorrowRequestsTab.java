package dev.drinaissante.tabs;

import dev.drinaissante.Main;
import dev.drinaissante.util.Fonts;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Objects;

// TODO database get requests
public class BorrowRequestsTab implements SKTab {
    private final HBox mainContent = new HBox(10);

    // TODO temp
    private final Image profileImg = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/default_profile.jpg")));

    // TODO temp equipmentImg
    private final Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/camera_1.png")));


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
        searchField.setPromptText("Search for Requests");
        searchField.setPrefWidth(280);
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

            // TODO fetch from database
            Label requesterName = new Label("Carson, Carl Emmanuel");
            requesterName.setFont(Font.font(Fonts.COMIC_NEUE_BOLD, FontWeight.BOLD, 16));

            Label reason = new Label("Pahiram nyakkk");
            reason.setFont(Font.font(Fonts.COMIC_NEUE, FontWeight.NORMAL, 14));

            VBox nameReasonBox = new VBox(5, requesterName, reason);

            hbox.getChildren().addAll(profileView, nameReasonBox);

            vbox.getChildren().add(hbox);
        }

        return vbox;
    }

    // TODO request / process request btn / date and time
    private VBox setupRightPanel() {
        VBox mainContent = new VBox(20);
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setStyle("-fx-background-color: #a6a6a680; -fx-padding: 10;");

        // TODO date
        HBox dateBox = new HBox();
        dateBox.setAlignment(Pos.TOP_RIGHT);

//        Region spacer = new Region();
//        HBox.setHgrow(spacer, Priority.ALWAYS);

        // TODO fetch date
        Label dateLbl = new Label("12/23/2026 6:07PM");
        dateLbl.setTextFill(Color.WHITE);
        dateLbl.setOpacity(0.7);

        dateBox.getChildren().addAll(dateLbl);

        // TODO requesterName, reason
        String requesterName = "Carson, Carl Emmanuel";
        String reason = "Pahiram nyakkk";

        Text requestText = new Text(requesterName + " requested to borrow:");
        requestText.setFill(Color.WHITE);
        Text reasonText = new Text(reason);
        reasonText.setFill(Color.WHITE);

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        Text equipment_name = new Text("Sony Mav");
        equipment_name.setFill(Color.WHITE);

        Text ownerText = new Text("Punzalan, Vince Adrian");
        ownerText.setFill(Color.WHITE);

        // TODO equipment_image
        VBox equipmentBox = new VBox(10, imageView, equipment_name, ownerText);
        equipmentBox.setAlignment(Pos.CENTER);
        equipmentBox.setMaxWidth(200);
        equipmentBox.setPrefWidth(200);
        equipmentBox.setStyle("""
                    -fx-background-color: gray;
                    -fx-padding: 20;
                """);

        Button processBtn = new Button("Process Request");
        processBtn.getStyleClass().add("btn");

        HBox.setHgrow(mainContent, Priority.ALWAYS);

        mainContent.getChildren().addAll(dateBox, requestText, reasonText, equipmentBox, processBtn);

        return mainContent;
    }
}