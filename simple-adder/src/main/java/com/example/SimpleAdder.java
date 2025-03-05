package com.example;

import java.util.Random;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class SimpleAdder extends Application {
    private static Random random = new Random();

    private TextField textFieldA, textFieldB;
    private Label labelA, labelB, outputLabel, warningLabel;
    private Node outputRow;
    private ComboBox<String> operationSelector;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        var scene = new Scene(createMainView(), 500, 250);
        stage.setScene(scene);
        stage.setTitle("Simple Calculator");
        stage.show();
    }

    private Region createMainView() {
        var view = new BorderPane();
        view.getStylesheets().add(getClass().getResource("/css/simple-adder.css").toExternalForm());
        view.setTop(createHeading());
        view.setCenter(createCenterContent());
        view.setBottom(createButtonRow());
        return view;
    }

    private Node createHeading() {
        var heading = new Label("Simple Calculator");
        heading.getStyleClass().add("heading-label");
        HBox.setHgrow(heading, Priority.ALWAYS);
        heading.setMaxWidth(Double.MAX_VALUE);
        heading.setAlignment(Pos.CENTER);
        return heading;
    }

    private Node createCenterContent() {
        var inputRow = createInputRow();
        var outputPane = createOutputPane();

        var centerContent = new VBox(20, inputRow, outputPane);
        centerContent.setPadding(new Insets(20));
        centerContent.setAlignment(Pos.CENTER);
        return centerContent;
    }

    private Node createInputRow() {
        textFieldA = new TextField("0");
        textFieldA.getStyleClass().add("text-field");

        textFieldB = new TextField("0");
        textFieldB.getStyleClass().add("text-field");

        operationSelector = new ComboBox<>();
        operationSelector.getItems().addAll("+", "-", "×", "/");
        operationSelector.setValue("+");

        var inputRow = new HBox(10, new Label("A:"), textFieldA, operationSelector, new Label("B:"), textFieldB);
        inputRow.setAlignment(Pos.CENTER);
        return inputRow;
    }

    private Node createOutputPane() {
        outputRow = createOutputRow();
        warningLabel = createWarningLabel();
        warningLabel.setVisible(false);
        var outputPane = new StackPane(outputRow, warningLabel);
        return outputPane;
    }

    private Node createOutputRow() {
        labelA = new Label("0");
        labelB = new Label("0");
        outputLabel = new Label("0");
        outputLabel.getStyleClass().add("output-label");

        var outputRow = new HBox(10, labelA, new Label(" "), labelB, new Label("="), outputLabel);
        outputRow.setAlignment(Pos.CENTER);
        return outputRow;
    }

    private Label createWarningLabel() {
        warningLabel = new Label("Invalid input or division by zero.");
        warningLabel.getStyleClass().add("warning");
        return warningLabel;
    }

    private Node createButtonRow() {
        var buttonRow = new HBox(20, createRandomizeButton(), createCalculateButton());
        buttonRow.setPadding(new Insets(0, 0, 20, 0));
        buttonRow.setAlignment(Pos.CENTER);
        return buttonRow;
    }

    private Node createRandomizeButton() {
        var randomizeButton = new Button("Randomize");
        randomizeButton.getStyleClass().add("randomize-button");
        randomizeButton.setOnAction(evt -> {
            textFieldA.setText(String.valueOf(rangeRandomInt(-1000, 1000)));
            textFieldB.setText(String.valueOf(rangeRandomInt(-1000, 1000)));
        });
        return randomizeButton;
    }

    private Node createCalculateButton() {
        var calculateButton = new Button("Calculate");
        calculateButton.getStyleClass().add("calculate-button");
        calculateButton.setOnAction(evt -> calculateResult());
        return calculateButton;
    }

    private void calculateResult() {
        String valueA = textFieldA.getText();
        String valueB = textFieldB.getText();
        String operation = operationSelector.getValue();

        try {
            int a = Integer.parseInt(valueA);
            int b = Integer.parseInt(valueB);
            int result = 0;

            switch (operation) {
                case "+":
                    result = a + b;
                    break;
                case "-":
                    result = a - b;
                    break;
                case "×":
                    result = a * b;
                    break;
                case "/":
                    if (b == 0) {
                        showWarning("Division by zero.");
                        return;
                    }
                    result = a / b;
                    break;
            }

            labelA.setText(valueA);
            labelB.setText(valueB);
            outputLabel.setText(String.valueOf(result));
            showOutput();
        } catch (NumberFormatException e) {
            showWarning("Invalid input format.");
        }
    }

    private void showOutput() {
        outputRow.setVisible(true);
        warningLabel.setVisible(false);
    }

    private void showWarning(String message) {
        warningLabel.setText(message);
        outputRow.setVisible(false);
        warningLabel.setVisible(true);
    }

    private int rangeRandomInt(int start, int end) {
        return random.nextInt(end - start) + start;
    }
}
