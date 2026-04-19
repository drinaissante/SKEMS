package dev.drinaissante.tabs;

import dev.drinaissante.Main;
import dev.drinaissante.util.CalendarView;
import dev.drinaissante.util.Fonts;
import dev.drinaissante.util.ImageItem;
import dev.drinaissante.util.ImageSlider;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.List;
import java.util.Objects;

public class DashboardTab implements SKTab {
    private final VBox mainContent = new VBox(20);

    @Override
    public Node getNode() {
        return mainContent;
    }

    @Override
    public SKTab build() {
        mainContent.setStyle("-fx-background-color: linear-gradient(to left, #292832 0.000%, #272530 8.333%, #24222e 16.667%, #201f2b 25.000%, #1c1b27 33.333%, #181624 41.667%, #141220 50.000%, #100e1c 58.333%, #0c0a18 66.667%, #080614 75.000%, #050311 83.333%, #02000e 91.667%, #00000b 100.000%);" +
                "-fx-padding: 10;");

        // TODO

        HBox box = setupTop();
        // posts

        HBox calendarToDo = setupCalendarToDo();

        mainContent.getChildren().addAll(box, calendarToDo);

        return this;
    }

    private HBox setupTop() {
        HBox top = new HBox(5);
        top.setStyle("-fx-background-color: #a6a6a650; -fx-background-radius: 8px; -fx-padding: 5;");
        top.setFillHeight(true);


        List<ImageItem> images = List.of(
                new ImageItem(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/gifs/sinekultura.gif"))), "Sine Kultura 2026"),
                new ImageItem(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/img1.jpg"))), "REGION III: THE UNDISPUTED CHAMPIONS OF THE 12TH NATIONAL CULTURE AND ARTS FESTIVAL WITH 176 POINTS!"),
                new ImageItem(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/img2.jpg"))), "HIGHER EDUCATION SUMMIT"),
                new ImageItem(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/img3.jpg"))), "MR. VINCE ADRIAN PUNZALAN (Videographer)"),
                new ImageItem(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/img4.jpg"))), "Sine Kultura Boiz")
        );


        ImageSlider slider = new ImageSlider(images, 0, 0, 5);

        VBox gifBoxWithText = new VBox(20, slider);
        gifBoxWithText.setMaxWidth(Double.MAX_VALUE);

        ImageView latestPost = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/latest_post.jpg"))));
        latestPost.setFitHeight(280);
        latestPost.setFitWidth(280);
        latestPost.setPreserveRatio(false);

        Rectangle clip = new Rectangle(280, 280);
        clip.setArcWidth(28);
        clip.setArcHeight(28);

        latestPost.setClip(clip);

        ImageView fbLogo = new ImageView(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/fb_logo.png"))));
        fbLogo.setFitHeight(40);
        fbLogo.setFitWidth(40);
        fbLogo.setPreserveRatio(false);

        Label newPostLbl = new Label("New Post");
        newPostLbl.setFont(Font.font(Fonts.COMIC_NEUE_BOLD, FontWeight.BOLD, 24));
        newPostLbl.setStyle("""
                -fx-text-fill: white;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 8, 0.5, 2, 2);
                """
        );

        // latest post
        StackPane stack = new StackPane(latestPost, fbLogo, newPostLbl);
        stack.setStyle("-fx-padding: 10;");
        StackPane.setAlignment(fbLogo, Pos.TOP_LEFT);
        StackPane.setAlignment(newPostLbl, Pos.BOTTOM_CENTER);
        stack.setMaxWidth(300);
        stack.setMinWidth(300);

        HBox.setHgrow(gifBoxWithText, Priority.ALWAYS);
        HBox.setHgrow(stack, Priority.NEVER);

        top.getChildren().addAll(gifBoxWithText, stack);

        return top;
    }

    private HBox setupCalendarToDo() {
        HBox box = new HBox(20);

        CalendarView calendarView = new CalendarView();
        calendarView.setAlignment(Pos.BOTTOM_LEFT);

        // TODO ToDos
        Label toDoLbl = new Label("To Do's:");
        toDoLbl.setFont(Font.font(Fonts.COMIC_NEUE_BOLD, FontWeight.BOLD, 50));
        toDoLbl.setStyle("""
                -fx-text-fill: white;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 8, 0.5, 2, 2);
                """);

        HBox first = createTodoItem("APRIL 20", "ROTC Graduation Photoshoot");
        HBox second = createTodoItem("APRIL 22", "InFOCUS Meeting");
        HBox third = createTodoItem("APRIL 25", "Sine Kultura General Assembly");
        HBox fourth = createTodoItem("APRIL 30", "End of Semester");

        VBox todoBox = new VBox(20, toDoLbl, first, second, third, fourth);
        todoBox.setMaxWidth(Double.MAX_VALUE);
        todoBox.setStyle("""
                -fx-background-color: #a6a6a6;
                -fx-padding: 20;
                -fx-background-radius: 12px;
                """);
        todoBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(todoBox, Priority.ALWAYS);
        todoBox.setFillWidth(true);

        box.getChildren().addAll(calendarView, todoBox);
        // TODO calendar
        return box;
    }

    private HBox createTodoItem(String date, String task) {
        Label dateLbl = createLabel(date);
        Label taskLbl = createLabel(task);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        return new HBox(10, dateLbl, spacer, taskLbl);
    }

    private Label createLabel(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font(Fonts.COMIC_NEUE_BOLD, FontWeight.BOLD, 24));
        lbl.setStyle("""
                -fx-text-fill: white;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 8, 0.5, 2, 2);
                """
        );
        return lbl;
    }
}
