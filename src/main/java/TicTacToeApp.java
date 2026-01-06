package com.example.tictactoe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Random;

public class TicTacToeApp extends Application {

    private final int[][] board = new int[3][3]; // 0=O, 1=X
    private Image xImage;
    private Image oImage;
    private final ImageView[][] cells = new ImageView[3][3];
    private final Label resultLabel = new Label("Welcome!");


    public void start(Stage stage) {
        // Safe image loading
        try {
            var xStream = getClass().getResourceAsStream("/X.png");
            var oStream = getClass().getResourceAsStream("/O.png");
            if (xStream != null) xImage = new Image(xStream);
            if (oStream != null) oImage = new Image(oStream);
        } catch (Exception e) {
            System.out.println("Resource error");
        }

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        // Dark background helps pixel art stand out
        grid.setStyle("-fx-background-color: #2c3e50; -fx-padding: 20;");

        // Init board cells
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = new ImageView();
                cells[i][j].setFitWidth(100);
                cells[i][j].setFitHeight(100);
                cells[i][j].setPreserveRatio(true);
                grid.add(cells[i][j], j, i);
            }
        }

        Button newGameBtn = new Button("New Game");
        newGameBtn.setOnAction(e -> newGame());
        resultLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18px;");

        VBox topBox = new VBox(resultLabel);
        topBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setCenter(grid);
        root.setTop(topBox);
        root.setBottom(new VBox(newGameBtn));
        ((VBox)root.getBottom()).setAlignment(Pos.CENTER);
        ((VBox)root.getBottom()).setStyle("-fx-padding: 20;");

        Scene scene = new Scene(root, 400, 500);
        stage.setTitle("Tic-Tac-Toe Random");
        stage.setScene(scene);
        stage.show();

        // RUN NEW GAME AUTOMATICALLY ON START
        newGame();
    }

    private void newGame() {
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = rand.nextInt(2);
                cells[i][j].setImage(board[i][j] == 0 ? oImage : xImage);
            }
        }
        checkWinner();
    }

    private void checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                showWinner(board[i][0]);
                return;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                showWinner(board[0][i]);
                return;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            showWinner(board[0][0]);
            return;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            showWinner(board[0][2]);
            return;
        }
        resultLabel.setText("It's a tie!");
    }

    private void showWinner(int player) {
        resultLabel.setText(player == 1 ? "X wins!" : "O wins!");
    }
}