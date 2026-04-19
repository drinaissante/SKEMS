package dev.drinaissante.util;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class ImageSlider extends StackPane {

    private final ImageView imageView = new ImageView();
    private final HBox progressBox = new HBox(5);
    private final List<Region> bars = new ArrayList<>();
    private final Label caption = new Label();
    private final List<ImageItem> images;
    private int currentIndex = 0;

    public ImageSlider(List<ImageItem> images, double width, double height, int secondsPerImage) {
        this.images = images;

        setMinSize(0, 0);
        setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        setAlignment(Pos.CENTER);

        imageView.fitWidthProperty().bind(widthProperty());
        imageView.fitHeightProperty().bind(heightProperty());
        imageView.setPreserveRatio(true);
        imageView.setCache(true);
        imageView.setSmooth(true);

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(widthProperty());
        clip.heightProperty().bind(heightProperty());

        setClip(clip);

        if (!images.isEmpty()) {
            imageView.setImage(images.getFirst().image());
            caption.setText(images.getFirst().caption());

            for (int i = 0; i < images.size(); i++) {
                Region bar = new Region();

                bar.setPrefHeight(3);
                bar.setMinHeight(3);
                bar.setMaxHeight(3);

                bar.setStyle("""
                            -fx-background-color: rgba(255,255,255,0.3);
                            -fx-background-radius: 5;
                        """);

                HBox.setHgrow(bar, Priority.ALWAYS);

                bar.setScaleX(0);

                bars.add(bar);
                progressBox.getChildren().add(bar);
            }
        }


        caption.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        caption.setTextAlignment(TextAlignment.CENTER);
        caption.setWrapText(true);
        caption.maxWidthProperty().bind(widthProperty().multiply(0.8));

        StackPane.setAlignment(caption, Pos.BOTTOM_CENTER);

        VBox container = new VBox(8, imageView, progressBox, caption);
        container.setFillWidth(true);

        getChildren().add(container);

        updateBars();
        animateProgress(0, secondsPerImage);
        startSlider(secondsPerImage);
    }

    private void updateBars() {
        for (int i = 0; i < bars.size(); i++) {
            Region bar = bars.get(i);

            if (i < currentIndex) {
                // past → full
                bar.setScaleX(1);
                bar.setStyle("-fx-background-color: white;");
            } else if (i == currentIndex) {
                // current → will animate
                bar.setScaleX(0);
                bar.setStyle("-fx-background-color: white;");
            } else {
                // future → empty
                bar.setScaleX(0);
                bar.setStyle("-fx-background-color: rgba(255,255,255,0.3);");
            }
        }
    }

    private void animateProgress(int index, int seconds) {
        Region bar = bars.get(index);

        bar.setStyle("""
                    -fx-background-color: white;
                    -fx-background-radius: 5;
                """);

        ScaleTransition fill = new ScaleTransition(Duration.seconds(seconds), bar);
        fill.setFromX(0);
        fill.setToX(1);
        fill.setInterpolator(Interpolator.LINEAR);

        fill.setOnFinished(e -> bar.setScaleX(1));

        fill.play();
    }

    private void startSlider(int seconds) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(seconds), e -> nextImage())
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void nextImage() {
        if (images.isEmpty()) return;

        FadeTransition fadeOutImg = new FadeTransition(Duration.millis(400), imageView);
        fadeOutImg.setToValue(0);

        FadeTransition fadeOutText = new FadeTransition(Duration.millis(200), caption);
        fadeOutText.setToValue(0);

        ParallelTransition fadeOut = new ParallelTransition(fadeOutImg, fadeOutText);

        fadeOut.setOnFinished(e -> {
            updateBars();

            currentIndex = (currentIndex + 1) % images.size();

            ImageItem item = images.get(currentIndex);
            imageView.setImage(item.image());
            caption.setText(item.caption());

            animateProgress(currentIndex, 5);

            FadeTransition fadeInImg = new FadeTransition(Duration.millis(400), imageView);
            fadeInImg.setFromValue(0);
            fadeInImg.setToValue(1);

            FadeTransition fadeInText = new FadeTransition(Duration.millis(600), caption);
            fadeInText.setFromValue(0);
            fadeInText.setToValue(1);

            new ParallelTransition(fadeInImg, fadeInText).play();
        });

        fadeOut.play();
    }
}

