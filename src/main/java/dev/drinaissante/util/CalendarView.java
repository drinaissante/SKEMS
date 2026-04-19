package dev.drinaissante.util;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.time.YearMonth;

public class CalendarView extends VBox {

    private final GridPane calendarGrid = new GridPane();
    private final Label monthLabel = new Label();

    private YearMonth currentYearMonth = YearMonth.now();

    public CalendarView() {
        setSpacing(10);
        setAlignment(Pos.CENTER);

        setStyle("-fx-background-color: #a6a6a6; -fx-background-radius: 12px; -fx-text-fill: white;");

        monthLabel.setFont(Font.font(18));

        // Navigation buttons
        Button prevBtn = new Button("<");
        Button nextBtn = new Button(">");

        prevBtn.setOnAction(e -> previousMonth());
        nextBtn.setOnAction(e -> nextMonth());

        HBox navBar = new HBox(10, prevBtn, monthLabel, nextBtn);
        navBar.setAlignment(Pos.CENTER);

        getChildren().addAll(navBar, calendarGrid);

        drawCalendar();
    }

    private void drawCalendar() {
        calendarGrid.getChildren().clear();

        LocalDate today = LocalDate.now();
        monthLabel.setText(currentYearMonth.getMonth() + " " + currentYearMonth.getYear());

        // Days of week
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < days.length; i++) {
            Label lbl = new Label(days[i]);
            lbl.setFont(Font.font(14));
            calendarGrid.add(lbl, i, 0);
        }

        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7; // make Sunday = 0

        int daysInMonth = currentYearMonth.lengthOfMonth();

        int col = dayOfWeek;
        int row = 1;

        for (int day = 1; day <= daysInMonth; day++) {
            StackPane cell = createDayCell(day, today);

            calendarGrid.add(cell, col, row);

            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }
    }

    private StackPane createDayCell(int day, LocalDate today) {
        StackPane pane = new StackPane();
        pane.setPrefSize(40, 40);

        Rectangle bg = new Rectangle(40, 40);
        bg.setStroke(Color.LIGHTGRAY);

        if (today.getYear() == currentYearMonth.getYear()
                && today.getMonth() == currentYearMonth.getMonth()
                && today.getDayOfMonth() == day) {
            bg.setFill(Color.valueOf("#c59000"));
        } else {
            bg.setFill(Color.valueOf("#a6a6a6"));
        }

        Label lbl = new Label(String.valueOf(day));

        pane.getChildren().addAll(bg, lbl);
        return pane;
    }

    // Optional: allow switching months
    public void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        drawCalendar();
    }

    public void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        drawCalendar();
    }
}