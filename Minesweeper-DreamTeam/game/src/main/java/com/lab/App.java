package com.lab;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class App extends Application {
    private Minesweeper game;
    private Button[][] buttons;
    private int gridSize;

    @Override
    public void start(Stage primaryStage) {
        List<String> choices = Arrays.asList("5x5", "9x9", "15x15", "Load from file");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("9x9", choices);
        dialog.setTitle("Select Minefield Size");
        dialog.setHeaderText("Choose a minefield size:");
        dialog.setContentText("Size:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            switch (result.get()) {
                case "5x5":
                    game = new Minesweeper(5, 5);
                    game.setMineCell(1, 1);
                    game.setMineCell(2, 3);
                    game.setMineCell(4, 0);
                    gridSize = 5;
                    break;
                case "9x9":
                    game = new Minesweeper(9, 9);
                    game.setMineCell(0, 1);
                    game.setMineCell(1, 5);
                    game.setMineCell(1, 8);
                    game.setMineCell(2, 4);
                    game.setMineCell(3, 6);
                    game.setMineCell(4, 2);
                    game.setMineCell(5, 4);
                    game.setMineCell(6, 2);
                    game.setMineCell(7, 2);
                    game.setMineCell(8, 6);
                    gridSize = 9;
                    break;
                case "15x15":
                    game = new Minesweeper(15, 15);
                    game.setMineCell(3, 2);
                    game.setMineCell(5, 7);
                    game.setMineCell(8, 10);
                    game.setMineCell(12, 4);
                    game.setMineCell(14, 14);
                    gridSize = 15;
                    break;
                case "Load from file":
                    game = new Minesweeper("minefield.txt"); // Default file name
                    gridSize = game.fieldX;
                    break;
                default:
                    game = new Minesweeper(9, 9);
                    gridSize = 9;
            }
        } else {
            game = new Minesweeper(9, 9);
            gridSize = 9;
        }

        GridPane grid = new GridPane();
        buttons = new Button[gridSize][gridSize];
        
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Button button = new Button(" ");
                button.setMinSize(40, 40);
                int x = i, y = j;
                button.setOnAction(e -> revealCell(x, y));
                buttons[i][j] = button;
                grid.add(button, j, i);
            }
        }
        
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem saveGame = new MenuItem("Save Game");
        saveGame.setOnAction(e -> saveGameToFile("saved_game.txt"));
        fileMenu.getItems().add(saveGame);
        menuBar.getMenus().add(fileMenu);
        
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(grid);
        
        Scene scene = new Scene(root, gridSize * 40, gridSize * 40 + 30);
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void revealCell(int x, int y) {
        if (game.cells[x][y] == Minesweeper.IS_MINE) {
            buttons[x][y].setText("X");
            buttons[x][y].setStyle("-fx-background-color: red;");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("You hit a mine! Game Over.");
            alert.showAndWait();
        } else {
            buttons[x][y].setText(".");
            buttons[x][y].setStyle("-fx-background-color: lightgray;");
        }
    }

    private void saveGameToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(gridSize + "\n");
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    writer.write(game.cells[i][j] == Minesweeper.IS_MINE ? 'X' : '.');
                }
                writer.write("\n");
            }
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Game Saved");
            alert.setHeaderText(null);
            alert.setContentText("Game has been saved successfully!");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
