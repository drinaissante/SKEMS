package dev.drinaissante;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);

        Image icon = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sk_icon.jpg")));
        stage.getIcons().add(icon);


        stage.setTitle("Hello World");
        stage.setScene(scene);
        stage.show();

    }
}