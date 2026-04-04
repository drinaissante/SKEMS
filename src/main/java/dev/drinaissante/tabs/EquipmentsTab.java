package dev.drinaissante.tabs;

import dev.drinaissante.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.util.List;
import java.util.Objects;

public class EquipmentsTab implements SKTab {

    private final List<String> STATUS = List.of("Available", "Checked Out", "Pending", "Unavailable");
    private final Image searchImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/search.png")));
    private final BorderPane mainContent = new BorderPane();

    @Override
    public Node getNode() {
        return mainContent;
    }

    @Override
    public SKTab build() {
//        Region overlay = new Region();
//        overlay.prefWidthProperty().bind(getStackPane().widthProperty());
//        overlay.prefHeightProperty().bind(getStackPane().heightProperty());
//        overlay.setStyle("""
//                -fx-background-color: linear-gradient( to right, rgba(0,0,0,0.8) 0%, rgba(0,0,0,0.4) 40%, rgba(0,0,0,0.1) 100% );
//                """);
        mainContent.setStyle("-fx-background-color: linear-gradient(to left, #292832 0.000%, #272530 8.333%, #24222e 16.667%, #201f2b 25.000%, #1c1b27 33.333%, #181624 41.667%, #141220 50.000%, #100e1c 58.333%, #0c0a18 66.667%, #080614 75.000%, #050311 83.333%, #02000e 91.667%, #00000b 100.000%);" +
                "-fx-padding: 20;");

        // searchBox
        HBox searchBox = buildSearchBox();

        ScrollPane scrollPane = setupEquipmentScrollPane();

        HBox statsPanel = new HBox(20);

        // add item
        Button addBtn = new Button("Add Item");
        addBtn.getStyleClass().add("btn");
        addBtn.setPrefWidth(150);
        addBtn.setOnAction(event -> {
            // TODO
        });
        VBox.setMargin(addBtn, new Insets(15, 0, 40, 30));


        Region HSpacer = new Region();
        HBox.setHgrow(HSpacer, Priority.ALWAYS);

        // TODO actual data / value
        statsPanel.getChildren().addAll(
                buildStat("Available:", "124", Color.GREEN),
                buildStat("Checked Out:", "15", Color.YELLOW),
                buildStat("Pending Requests:", "5", Color.ORANGERED),
                buildStat("Overdue Items:", "2", Color.RED),
                HSpacer,
                addBtn
        );

        Region VSpacer = new Region();
        VBox.setVgrow(VSpacer, Priority.ALWAYS);

        VBox content = new VBox(30, searchBox, scrollPane, VSpacer, statsPanel);

        mainContent.setCenter(content);
        // TODO the equipments inventory list

//        return new StackPane(overlay, mainContent);
        return this;
    }

    private HBox buildSearchBox() {
        TextField searchField = new TextField();
        searchField.setPromptText("Search for Equipment");
        searchField.setPrefWidth(380);
        searchField.setMaxWidth(380);
        searchField.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");

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

        ComboBox<String> sortBy = new ComboBox<>();
        sortBy.setPrefWidth(400);
        sortBy.setPrefWidth(400);
        sortBy.getItems().addAll(STATUS);
        sortBy.setPromptText("Sort By");

        sortBy.setOnAction(event -> {
            // TODO
        });

        return searchBox;
    }

    private ScrollPane setupEquipmentScrollPane() {
        TilePane equipmentGrid = new TilePane();
        equipmentGrid.setPrefColumns(4);
        equipmentGrid.setHgap(20);
        equipmentGrid.setVgap(20);
        equipmentGrid.setPadding(new Insets(20));
        equipmentGrid.setAlignment(Pos.TOP_LEFT);

        for (int i = 0; i < 20; i++) {
            // TODO database
            String name = "Sony Mavica";
            String owner = "Yow momma";
            String status = "AVAILABLE";

            VBox item = getItem(status, name, owner);

            equipmentGrid.getChildren().add(item);
        }

        ScrollPane scrollPane = new ScrollPane(equipmentGrid);
        scrollPane.setStyle("-fx-background-color: transparent;");
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

    // TODO get the image from database
    private VBox getItem(String status, String name, String owner) {
        VBox item = new VBox(10);
        item.getStyleClass().add("equipment-item");

        // TODO fetch image | add skeleton
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/camera_1.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        imageView.setPreserveRatio(true);

        HBox imageCenter = new HBox(imageView);
        imageCenter.setAlignment(Pos.CENTER_LEFT);

        Text nameText = new Text(name);
        Text ownerText = new Text("Owner: " + owner);

        VBox nameBox = new VBox(nameText, ownerText);
        nameBox.setAlignment(Pos.CENTER_LEFT);

        item.getChildren().addAll(imageCenter, nameBox);

        item.setCursor(Cursor.HAND);

        item.setOnMouseClicked(e -> {
            Popup popup = new Popup();
            VBox popupContent = new VBox();

            Label text = new Label("Details for " + name);
            text.setTextFill(Color.WHITE);

            popupContent.getChildren().add(text);

            popupContent.setOnMouseExited(event -> popup.hide());

            popupContent.setStyle("-fx-background-color: rgba(0,0,0,0.6); -fx-padding: 40;");
            popup.getContent().add(popupContent);

            popup.setAutoHide(true);
            popup.show(Main.STAGE, e.getScreenX() + 10, e.getScreenY());
        });
        return item;
    }
}
