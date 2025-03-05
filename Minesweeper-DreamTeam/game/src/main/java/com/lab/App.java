package com.lab;

import java.util.Scanner;

public class App {
    static Minesweeper initMineField() {
        Minesweeper game = new Minesweeper(9, 9);
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
        return game;
    }

    static Minesweeper initMineFieldFromFile(String minefieldFile) {
        return new Minesweeper(minefieldFile);
    }
    
    static Minesweeper initMineFieldSmall() {
        Minesweeper game = new Minesweeper(5, 5);
        game.setMineCell(1, 1);
        game.setMineCell(2, 3);
        game.setMineCell(4, 0);
        return game;
    }
    static Minesweeper initMineFieldLarge() {
        Minesweeper game = new Minesweeper(15, 15);
        game.setMineCell(3, 2);
        game.setMineCell(5, 7);
        game.setMineCell(8, 10);
        game.setMineCell(12, 4);
        game.setMineCell(14, 14);
        game.setMineCell(0, 1);
        game.setMineCell(2, 4);
        game.setMineCell(5, 12);
        game.setMineCell(11, 10);
        game.setMineCell(10, 14);
        return game;
    }

    static Minesweeper createCustomMineField(Scanner scanner) {
        System.out.print("Enter width: ");
        int width = scanner.nextInt();
        System.out.print("Enter height: ");
        int height = scanner.nextInt();
        Minesweeper game = new Minesweeper(width, height);
        System.out.print("Enter number of mines: ");
        int mineCount = scanner.nextInt();
        for (int i = 0; i < mineCount; i++) {
            System.out.print("Enter mine coordinates (x y): ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            game.setMineCell(x, y);
        }
        return game;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select Minefield Size:");
        System.out.println("1. 5x5");
        System.out.println("2. 9x9 (Default)");
        System.out.println("3. 15x15");
        System.out.println("4. Load from file");
        System.out.println("5. Load Game");
        System.out.println("6. Create Custom Minefield");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();

        Minesweeper game;
        switch (choice) {
            case 1:
                game = initMineFieldSmall();
                break;
            case 2:
                game = initMineField();
                break;
            case 3:
                game = initMineFieldLarge();
                break;
            case 4:
                System.out.print("Enter file name: ");
                scanner.nextLine(); // Consume newline
                String filename = scanner.nextLine();
                game = initMineFieldFromFile(filename);
                break;
            case 5:
                System.out.print("Enter save file name: ");
                scanner.nextLine();
                String saveFile = scanner.nextLine();
                game = new Minesweeper(9, 9);
                game.loadGame(saveFile);
                break;
            case 6:
                game = createCustomMineField(scanner);
                break;
            default:
                System.out.println("Invalid choice! Defaulting to 9x9.");
                game = initMineField();
        }

        game.displayField();
        scanner.nextLine(); // Consume newline
        System.out.print("Save game? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.print("Enter save file name: ");
            String saveFile = scanner.nextLine();
            game.saveGame(saveFile);
        }
        scanner.close();
    }
}
