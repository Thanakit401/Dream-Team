package com.lab;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class Minesweeper {
    static char SAFE_CELL = '.';
    static char MINE_CELL = 'X' ;
    static int IS_SAFE = 0;
    static int IS_MINE = 1;
    int fieldX, fieldY;
    int[][] cells;
    String fieldFileName;

    public Minesweeper(String fieldFile) {
        this.fieldFileName = fieldFile;
        initFromFile(fieldFileName);
    }
    public Minesweeper(int fieldX, int fieldY) {
        this.fieldX = fieldX;
        this.fieldY = fieldY;
        this.cells = new int[fieldX][fieldY];
        for (int i=0; i<fieldX; i++) {
            for (int j=0; j<fieldY; j++) {
                cells[i][j] = IS_SAFE;
            }
        }
    }

    void displayField() {
    for (int i = 0; i < fieldX; i++) {
        for (int j = 0; j < fieldY; j++) {
            if (cells[i][j] == IS_MINE) {
                System.out.print(MINE_CELL);
            } else {
                System.out.print(SAFE_CELL);
            }
        }
        System.out.println();
    }
}

    void setMineCell(int x, int y) {
        cells[x][y] = IS_MINE;
    }

    void initFromFile(String mineFieldFile) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(mineFieldFile);
    
        try (Scanner scanner = new Scanner(is)) {
            fieldX = Integer.parseInt(scanner.nextLine());
            fieldY = Integer.parseInt(scanner.nextLine());
            
            cells = new int[fieldX][fieldY];
            
            for (int i = 0; i < fieldX; i++) {
                String line = scanner.nextLine();
                for (int j = 0; j < fieldY; j++) {
                    if (line.charAt(j) == MINE_CELL) {
                        cells[i][j] = IS_MINE;
                    } else {
                        cells[i][j] = IS_SAFE;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void saveGame(String saveFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            writer.write(fieldX + "\n");
            writer.write(fieldY + "\n");
            for (int i = 0; i < fieldX; i++) {
                for (int j = 0; j < fieldY; j++) {
                    writer.write(cells[i][j] == IS_MINE ? MINE_CELL : SAFE_CELL);
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadGame(String saveFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {
            fieldX = Integer.parseInt(reader.readLine());
            fieldY = Integer.parseInt(reader.readLine());
            cells = new int[fieldX][fieldY];
            for (int i = 0; i < fieldX; i++) {
                String line = reader.readLine();
                for (int j = 0; j < fieldY; j++) {
                    cells[i][j] = (line.charAt(j) == MINE_CELL) ? IS_MINE : IS_SAFE;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}