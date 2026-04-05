package dev.drinaissante.tabs;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class DashboardTab implements SKTab {
    private final VBox equipment = new VBox(20);

    @Override
    public Node getNode() {
        return equipment;
    }

    @Override
    public SKTab build() {
        equipment.setStyle("-fx-background-color: linear-gradient(to left, #292832 0.000%, #272530 8.333%, #24222e 16.667%, #201f2b 25.000%, #1c1b27 33.333%, #181624 41.667%, #141220 50.000%, #100e1c 58.333%, #0c0a18 66.667%, #080614 75.000%, #050311 83.333%, #02000e 91.667%, #00000b 100.000%);" +
                "-fx-padding: 20;");

        // TODO show all reserved and borrowed items

        return this;
    }
}
