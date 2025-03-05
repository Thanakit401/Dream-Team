package com.lab;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import java.util.HashMap;
import java.util.Map;

public class minesweeperfxgl extends GameApplication {
    private static final int TILE_SIZE = 40;
    private static final int WIDTH = 9;
    private static final int HEIGHT = 9;
    private Minesweeper game;
    private Map<String, StackPane> tileMap = new HashMap<>();
    
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(WIDTH * TILE_SIZE);
        settings.setHeight(HEIGHT * TILE_SIZE);
        settings.setTitle("Minesweeper FXGL");
    }

    @Override
    protected void initGame() {
        game = App.initMineField();

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                int fx = x, fy = y;
                StackPane tile = createTile(x, y);
                tile.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        revealTile(fx, fy);
                    }
                });
                tileMap.put(x + "," + y, tile);
                FXGL.addUINode(tile, x * TILE_SIZE, y * TILE_SIZE);
            }
        }
    }
    
    private StackPane createTile(int x, int y) {
        Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE, Color.LIGHTGRAY);
        rect.setStroke(Color.BLACK);
        return new StackPane(rect);
    }
    
    private void revealTile(int x, int y) {
        StackPane tile = tileMap.get(x + "," + y);
        if (tile == null) return;
        
        tile.getChildren().clear();
        Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE, Color.WHITE);
        rect.setStroke(Color.BLACK);
        
        int cellValue = game.getCells()[x][y];
    
        if (cellValue == Minesweeper.IS_MINE) {
            Text mineText = new Text("X");
            mineText.setFill(Color.RED);
            tile.getChildren().addAll(rect, mineText);
    
            Text gameOverText = new Text("Game Over!");
            gameOverText.setFill(Color.RED);
            gameOverText.setTranslateX(WIDTH * TILE_SIZE / 4);
            gameOverText.setTranslateY(HEIGHT * TILE_SIZE / 2);
            FXGL.addUINode(gameOverText);
            FXGL.getGameController().exit(); 
            
        } else {
            Text numberText = new Text(String.valueOf(cellValue));
            numberText.setFill(Color.BLACK);
            tile.getChildren().addAll(rect, numberText);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
