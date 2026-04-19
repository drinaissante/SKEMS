package dev.drinaissante.tabs.members;

import dev.drinaissante.Main;
import dev.drinaissante.tabs.SKTab;
import dev.drinaissante.util.ColorUtil;
import dev.drinaissante.util.EquipmentItem;
import dev.drinaissante.util.Fonts;
import dev.drinaissante.util.QRGenerator;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MemberEquipmentsTab implements SKTab {

    private final List<EquipmentItem> equipmentData = new ArrayList<>();
    private final TilePane equipmentGrid = new TilePane();

    private final List<String> STATUS = List.of("AVAILABLE", "UNAVAILABLE", "Clear");
    private final Image searchImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/search.png")));
    private final BorderPane mainContent = new BorderPane();
    private final List<EquipmentItem> allItems = new ArrayList<>();
    private final List<EquipmentItem> filteredItems = new ArrayList<>();
    private final Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/camera_1.png")));
    private final Image image2 = new Image(QRGenerator.qrCodesPath + File.separator + "bca94bcf-63ee-4598-aa88-898ef2077e82.png");

    @Override
    public Node getNode() {
        return mainContent;
    }

    @Override
    public SKTab build() {
        mainContent.setStyle("-fx-background-color: linear-gradient(to left, #292832 0.000%, #272530 8.333%, #24222e 16.667%, #201f2b 25.000%, #1c1b27 33.333%, #181624 41.667%, #141220 50.000%, #100e1c 58.333%, #0c0a18 66.667%, #080614 75.000%, #050311 83.333%, #02000e 91.667%, #00000b 100.000%);" +
                "-fx-padding: 20;");

        // searchBox
        HBox searchBox = buildSearchBox();

        // sort
        ComboBox<String> sortBy = new ComboBox<>();
        sortBy.setPrefWidth(200);
        sortBy.getItems().addAll(STATUS);
        sortBy.setPromptText("Sort By");
        sortBy.setStyle("-fx-background-color: #a6a6a6; -fx-padding: 5;");

        renderGrid();

        sortBy.setOnAction(event -> {
            String value = sortBy.getValue();

            if (value == null) return;

            if (value.equalsIgnoreCase("Clear")) {
                for (EquipmentItem item : allItems) {
                    equipmentGrid.getChildren().add(
                            getItem(item.status(), item.name(), item.owner())
                    );
                }

                sortBy.setValue(null);
            } else {
                applyFilter(value);
            }
        });

        Text sortByText = new Text("Sort By");
        sortByText.setFont(Font.font("Sans_Serif", FontWeight.BOLD, 18));
        sortByText.setFill(Color.WHITE);

        HBox searchWithSort = new HBox(20, searchBox, new VBox(sortByText, sortBy));

        ScrollPane scrollPane = setupEquipmentScrollPane();

        HBox statsPanel = new HBox(20);

        // TODO actual data / value
        statsPanel.getChildren().addAll(
                buildStat("Available:", "124", Color.GREEN),
                buildStat("Unavailable:", "50", Color.WHITE)
        );

        Region VSpacer = new Region();
        VBox.setVgrow(VSpacer, Priority.ALWAYS);

        VBox content = new VBox(30, searchWithSort, scrollPane, VSpacer, statsPanel);

        mainContent.setCenter(content);

        return this;
    }

    private HBox buildSearchBox() {
        TextField searchField = new TextField();
        searchField.setPromptText("Search for Equipment");
        searchField.setPrefWidth(380);
        searchField.setMaxWidth(380);
        searchField.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");

        filteredItems.addAll(allItems);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            applyFilter(newVal);
        });

        ImageView searchIcon = new ImageView(searchImg);
        searchIcon.setFitHeight(20);
        searchIcon.setFitWidth(20);
        searchIcon.setPreserveRatio(true);

        HBox searchBox = new HBox(5, searchIcon, searchField);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPrefWidth(400);
        searchBox.setStyle("""
                -fx-background-color: #a6a6a6;
                -fx-text-fill: white;
                -fx-padding: 5 5 5 10;
                """);
        searchBox.setMaxWidth(Region.USE_PREF_SIZE);

        return searchBox;
    }

    private void renderGrid() {
        equipmentGrid.getChildren().clear();

        for (EquipmentItem item : filteredItems) {
            equipmentGrid.getChildren().add(
                    getItem(item.status(), item.name(), item.owner())
            );
        }
    }

    private ScrollPane setupEquipmentScrollPane() {
        equipmentGrid.setPrefColumns(5);
        equipmentGrid.setHgap(20);
        equipmentGrid.setVgap(20);
        equipmentGrid.setPadding(new Insets(10));
        equipmentGrid.setAlignment(Pos.TOP_LEFT);

        // TODO temp
        Random rand = new Random();

        for (int i = 0; i < 20; i++) {
            // TODO database
            String name = rand.nextBoolean() ? "Sony Mavica" : "Nikon Z 30";
            String owner = rand.nextBoolean() ? "Ayoki Konnichiwa" : "Jason Gudoy";
            String status = rand.nextBoolean() ? "AVAILABLE" : "UNAVAILABLE";

            EquipmentItem item = new EquipmentItem(name, owner, status);
            equipmentData.add(item);
            allItems.add(item);

            VBox box = getItem(status, name, owner);
//
            equipmentGrid.getChildren().add(box);
        }

        ScrollPane scrollPane = new ScrollPane(equipmentGrid);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);

        return scrollPane;
    }

    private HBox buildStat(String labelText, String valueText, Color valueFill) {
        Label label = new Label(labelText);
        Label value = new Label(valueText);
        value.setTextFill(valueFill);

        HBox row = new HBox(10);
        row.getStyleClass().add("equipment-stats");
        row.setMaxWidth(235);
        row.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        row.getChildren().addAll(label, spacer, value);
        return row;
    }

    private void applyFilter(String query) {
        filteredItems.clear();

        if (query == null || query.isBlank()) {
            filteredItems.addAll(allItems);
        } else {
            String lower = query.toLowerCase();

            for (EquipmentItem item : allItems) {
                if (item.name().toLowerCase().contains(lower)
                        || item.owner().toLowerCase().contains(lower)
                        || item.status().toLowerCase().equalsIgnoreCase(lower)) {
                    filteredItems.add(item);
                }
            }
        }

        renderGrid();
    }

    // TODO get the image from database
    private VBox getItem(String status, String name, String owner) {
        VBox item = new VBox(10);
        item.getStyleClass().add("equipment-item");

        ScaleTransition st = new ScaleTransition(Duration.millis(200), item);
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setToX(1.1);
        st.setToY(1.1);

        item.setOnMouseEntered(event -> {
            st.setRate(1);
            st.playFromStart();
        });
        item.setOnMouseExited(event -> {
            st.setRate(-1);
            st.play();
        });

        // TODO fetch image | add skeleton
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        imageView.setPreserveRatio(true);

        Label statusLbl = getStatusLbl(status);
        HBox statusHBox = new HBox(statusLbl);
        statusHBox.setAlignment(Pos.CENTER);

        // StackPane: image centered, label top-left
        StackPane imagePane = new StackPane(imageView);
        StackPane.setAlignment(imagePane, Pos.CENTER);

        Text nameText = new Text(name);
        Text ownerText = new Text("Owner: " + owner);

        VBox nameBox = new VBox(nameText, ownerText);
        nameBox.setAlignment(Pos.CENTER_LEFT);

        item.getChildren().addAll(statusHBox, imagePane, nameBox);

        item.setCursor(Cursor.HAND);

        Popup popup = generatePopup(status, name, owner);

        item.setOnMouseClicked(e -> popup.show(Main.STAGE, e.getScreenX() + 10, e.getScreenY()));
        return item;
    }

    private Label getStatusLbl(String status) {
        status = status.toUpperCase();
        Label statusLbl = new Label(status);

        String valueFill = ColorUtil.toHexCode(Color.GRAY);

        switch (status) {
            case "AVAILABLE":
                valueFill = "#7ed957";
                break;
            case "UNAVAILABLE":
                valueFill = "#545454";
            default:
                break;
        }

        statusLbl.setStyle("""
                -fx-padding: 0 15 0 15;
                -fx-background-radius: 12;
                -fx-background-color: %s;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                """.formatted(valueFill)
        );

        return statusLbl;
    }

    private Popup generatePopup(String status, String name, String owner) {
        // popup
        Popup popup = new Popup();
        VBox popupContent = new VBox(15);
        popupContent.setStyle("-fx-background-color: rgba(0,0,0,0.6); -fx-padding: 20;");

        // TODO the picture itself
        HBox camWithInfo = new HBox(10);
        camWithInfo.setStyle("-fx-background-color: gray; -fx-padding: 15; -fx-background-radius: 12;");

        ImageView imageView2 = new ImageView(image);
        imageView2.setFitWidth(100);
        imageView2.setFitHeight(100);
        imageView2.setPreserveRatio(true);

        Label statusLbl = getStatusLbl(status);

        Text nameText = new Text(name);
        nameText.setFill(Color.WHITE);
        nameText.setFont(Font.font(Fonts.COMIC_NEUE_BOLD, FontWeight.BOLD, 16));
        Text ownerText = new Text("Owner: " + owner);
        ownerText.setFill(Color.WHITE);
        ownerText.setFont(Font.font(Fonts.COMIC_NEUE_BOLD, FontWeight.BOLD, 16));

        HBox nameWithStatus = new HBox(10, nameText, statusLbl);

        VBox textBox = new VBox(10, nameWithStatus, ownerText);
        textBox.setAlignment(Pos.CENTER_LEFT);

        camWithInfo.getChildren().addAll(imageView2, textBox);

        // TODO notes (to add in database)
        HBox noteWithQR = new HBox(15);

        Label noteText = new Label("NOTE:\nShutter Count: 953");
        noteText.setFont(Font.font(Fonts.COMIC_NEUE_BOLD, FontWeight.BOLD, 16));
        noteText.setStyle("-fx-background-color: gray; -fx-text-fill: white; -fx-padding: 10; -fx-background-radius: 10;");

        VBox qrBox = new VBox(10);
        qrBox.setAlignment(Pos.CENTER);
        Label qrLbl = new Label("QR Code");
        qrLbl.setStyle("""
                -fx-padding: 0 15 0 15;
                -fx-background-radius: 8;
                -fx-background-color: #a6a6a6;
                -fx-text-fill: white;
                """);

        // TODO TODOOOOOOOOOOOOOOOOOOOOOO

        // TODO the qr image here
        ImageView qrImageView = new ImageView(image2);
        qrImageView.setFitWidth(100);
        qrImageView.setFitHeight(100);
        qrImageView.setPreserveRatio(true);

        qrBox.getChildren().addAll(qrLbl, qrImageView);

        noteWithQR.getChildren().addAll(noteText, qrBox);

        popupContent.getChildren().addAll(camWithInfo, noteWithQR);
        popupContent.setOnMouseExited(event -> popup.hide());

        popup.getContent().add(popupContent);
        popup.setAutoHide(true);

        return popup;
    }
}