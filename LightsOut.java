//  CS 112 – Homework #10 – Lights Out Puzzle
//  Minimal single‑file implementation (≈130 lines)
//  Author: <your names>

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LightsOut extends Application {

    // --- reusable backgrounds for lights ----------------------------------
    private static final Background ON_BG  = new Background(new BackgroundFill(Color.YELLOW, new CornerRadii(10), Insets.EMPTY));
    private static final Background OFF_BG = new Background(new BackgroundFill(Color.BLACK,  new CornerRadii(10), Insets.EMPTY));

    // entry‑point -----------------------------------------------------------
    public static void main(String[] args) { launch(args); }

    @Override public void start(Stage primary) {
        primary.setTitle("Lights Out Size");

        Label prompt = new Label("Please select a size:");

        ToggleGroup tg = new ToggleGroup();
        VBox radios = new VBox(5);
        for (int i = 3; i <= 9; i++) {
            RadioButton rb = new RadioButton(String.valueOf(i));
            rb.setToggleGroup(tg);
            if (i == 5) rb.setSelected(true);
            radios.getChildren().add(rb);
        }

        Button make = new Button("Create Puzzle");
        make.setOnAction(e -> {
            int size = 5; // default
            for (Toggle t : tg.getToggles()) if (t.isSelected()) size = Integer.parseInt(((RadioButton) t).getText());
            new Puzzle(size);        // launch game stage
            primary.close();         // close size chooser
        });

        VBox root = new VBox(10, prompt, radios, make);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        primary.setScene(new Scene(root));
        primary.setResizable(false);
        primary.show();
    }

    // ----------------------------------------------------------------------
    private static class Puzzle {
        private final int n;
        private final Button[][] lightBtn;
        private final boolean[][] on;

        Puzzle(int size) {
            this.n = size;
            this.lightBtn = new Button[n][n];
            this.on = new boolean[n][n];

            BorderPane bp = new BorderPane();

            // ---- grid of lights -----------------------------------------
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setBackground(new Background(new BackgroundFill(Color.web("#555555"), CornerRadii.EMPTY, Insets.EMPTY)));

            for (int r = 0; r < n; r++)
                for (int c = 0; c < n; c++) {
                    Button b = new Button();
                    b.setPrefSize(50, 50);
                    b.setBackground(OFF_BG);
                    final int row = r, col = c; // effectively‑final for lambda
                    b.setOnAction(e -> press(row, col));
                    lightBtn[r][c] = b;
                    grid.add(b, c, r);
                }

            // ---- control buttons ---------------------------------------
            Button rand   = new Button("Randomize");
            rand.setOnAction(e -> randomize());
            Button chase  = new Button("Chase Lights");
            chase.setOnAction(e -> chase());
            HBox controls = new HBox(20, rand, chase);
            controls.setAlignment(Pos.CENTER);
            controls.setPrefHeight(60);

            bp.setCenter(grid);
            bp.setBottom(controls);

            Stage stage = new Stage();
            stage.setTitle("Lights Out");
            stage.setResizable(false);
            stage.setMinWidth(250);
            stage.setScene(new Scene(bp, 60 * n, 60 * n + 60));
            stage.show();

            randomize(); // generate initial solvable puzzle
        }

        // --- helper methods ---------------------------------------------
        private void press(int r, int c) {
            toggle(r, c);
            toggle(r - 1, c);
            toggle(r + 1, c);
            toggle(r, c - 1);
            toggle(r, c + 1);
        }

        private void toggle(int r, int c) {
            if (r < 0 || c < 0 || r >= n || c >= n) return;
            on[r][c] = !on[r][c];
            lightBtn[r][c].setBackground(on[r][c] ? ON_BG : OFF_BG);
        }

        private boolean isOn(int r, int c) { return on[r][c]; }

        private void randomize() {
            for (int r = 0; r < n; r++)
                for (int c = 0; c < n; c++)
                    if (Math.random() < 0.5) press(r, c);
        }

        private void chase() {
            for (int r = 0; r < n - 1; r++)
                for (int c = 0; c < n; c++)
                    if (isOn(r, c)) press(r + 1, c);
        }
    }
}
