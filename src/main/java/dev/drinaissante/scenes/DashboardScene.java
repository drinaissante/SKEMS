package dev.drinaissante.scenes;

import dev.drinaissante.managers.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class DashboardScene extends SKScene {
    private final BorderPane root = new BorderPane();

    public DashboardScene(SceneManager sceneManager) {
        super(sceneManager, "Dashboard");
    }

    @Override
    public void setup() {
        // contents
        BorderPane titleBar = sceneManager.getTitleBar().getRoot();
        root.setTop(titleBar);

        // side panel
        VBox sidePanel = setupSidePanel();
        root.setLeft(sidePanel);

        // main content (dashboard)
        VBox dashboard = setupMainContent();
        root.setCenter(dashboard);
    }
//    // linear-gradient(to right, #292832 0.000%, #272530 8.333%, #24222e 16.667%, #201f2b 25.000%, #1c1b27 33.333%, #181624 41.667%, #141220 50.000%, #100e1c 58.333%, #0c0a18 66.667%, #080614 75.000%, #050311 83.333%, #02000e 91.667%, #00000b 100.000%);

    private VBox setupSidePanel() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(180);
        sidebar.setStyle("-fx-background-color: #1E1E1E;");
        sidebar.setSpacing(15);

        // tabs
        ToggleButton dashboardBtn = new ToggleButton("Dashboard");
        ToggleButton equipmentBtn = new ToggleButton("Equipment");
        ToggleButton borrowBtn = new ToggleButton("Borrow Requests");

        ToggleGroup group = new ToggleGroup();
        dashboardBtn.setToggleGroup(group);
        equipmentBtn.setToggleGroup(group);
        borrowBtn.setToggleGroup(group);

        dashboardBtn.setSelected(true);

        // TODO add other tabs

        for (ToggleButton btn : List.of(dashboardBtn, equipmentBtn, borrowBtn)) {
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(Pos.CENTER_RIGHT);
            btn.setCursor(Cursor.HAND);

            btn.getStyleClass().add("sidebar-toggle");
        }

        sidebar.getChildren().addAll(dashboardBtn, equipmentBtn, borrowBtn);

        return sidebar;
    }

    private VBox setupMainContent() {
        final VBox mainContent = new VBox();

        mainContent.setStyle("-fx-background-color: linear-gradient(to right, #292832 0.000%, #272530 8.333%, #24222e 16.667%, #201f2b 25.000%, #1c1b27 33.333%, #181624 41.667%, #141220 50.000%, #100e1c 58.333%, #0c0a18 66.667%, #080614 75.000%, #050311 83.333%, #02000e 91.667%, #00000b 100.000%);" +
                "-fx-padding: 20;");
        mainContent.setSpacing(20);

        Label statsTitle = new Label("Equipment Stats");
        statsTitle.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        HBox statsPanel = new HBox(20);
        statsPanel.setAlignment(Pos.CENTER_LEFT);

        Label available = new Label("Available: 124");
        Label checkedOut = new Label("Checked Out: 18");
        Label pending = new Label("Pending: 5");
        Label overdue = new Label("Overdue: 2");

        for (Label stat : List.of(available, checkedOut, pending, overdue)) {
            stat.setStyle("-fx-background-color: linear-gradient(to right, #292832 0.000%, #141220 50.000%, #00000b 100.000%); " +
                    "-fx-padding: 20; -fx-text-fill: white;");
        }

        statsPanel.getChildren().addAll(available, checkedOut, pending, overdue);

        mainContent.getChildren().addAll(statsTitle, statsPanel);

        return mainContent;
    }
}
