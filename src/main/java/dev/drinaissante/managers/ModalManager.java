package dev.drinaissante.managers;

import dev.drinaissante.scenes.SKScene;
import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class ModalManager {

    public static void createModal(SKScene skScene, double duration, String text) {
        VBox toastContainer = skScene.getToastContainer();

        // 🧠 TEXT
        Label modalText = new Label(text);
        modalText.setTextFill(Color.WHITE);
        modalText.setFont(Font.font("SANS_SERIF", FontWeight.BOLD, 14));

        // 📦 TOAST
        StackPane toast = new StackPane(modalText);
        toast.setPrefWidth(300); // 👈 smaller width
        modalText.maxWidthProperty().bind(toast.widthProperty().subtract(20));

        toast.setStyle("""
                    -fx-background-color: rgba(40, 40, 40, 0.95);
                    -fx-padding: 12 16 12 16;
                    -fx-background-radius: 12;
                    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0.3, 0, 3);
                """);

        toast.setOpacity(0);
        toast.setTranslateY(30); // 👈 start below for bottom-left style

        toastContainer.getChildren().add(toast);

        // 🎬 ANIMATIONS
        TranslateTransition slideIn = new TranslateTransition(Duration.seconds(0.25), toast);
        slideIn.setFromY(30); // slide from below
        slideIn.setToY(0);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.25), toast);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        PauseTransition stay = new PauseTransition(Duration.seconds(duration));

        TranslateTransition slideOut = new TranslateTransition(Duration.seconds(0.25), toast);
        slideOut.setToY(30); // slide back down

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.25), toast);
        fadeOut.setToValue(0);

        ParallelTransition show = new ParallelTransition(slideIn, fadeIn);
        ParallelTransition hide = new ParallelTransition(slideOut, fadeOut);

        SequentialTransition sequence = new SequentialTransition(show, stay, hide);

        // 🧹 Remove after animation
        sequence.setOnFinished(e -> toastContainer.getChildren().remove(toast));

        // ▶️ Play
        sequence.play();

        // --------------------------------------------------
        // 🖱️ CLICK TO DISMISS
        // --------------------------------------------------
        toast.setOnMouseClicked(e -> {
            sequence.stop(); // stop current animation

            FadeTransition fadeOutFast = new FadeTransition(Duration.seconds(0.15), toast);
            fadeOutFast.setToValue(0);

            TranslateTransition slideOutFast = new TranslateTransition(Duration.seconds(0.15), toast);
            slideOutFast.setToY(30); // slide down

            ParallelTransition instantHide = new ParallelTransition(fadeOutFast, slideOutFast);

            instantHide.setOnFinished(ev -> toastContainer.getChildren().remove(toast));
            instantHide.play();
        });

        // --------------------------------------------------
        // 🧠 PAUSE ON HOVER (VERY NICE UX)
        // --------------------------------------------------
        toast.setOnMouseEntered(e -> sequence.pause());
        toast.setOnMouseExited(e -> sequence.play());
    }
}