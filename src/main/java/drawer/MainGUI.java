package drawer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import readtxt.TextFileReader;
import count.Analyzer;

public class MainGUI extends Application {

    private TextField folderPathField;
    private TextField targetNameField;
    private TextArea resultTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Text Analyzer");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label folderLabel = new Label("Folder Path:");
        folderPathField = new TextField();
        Label targetNameLabel = new Label("Target Name:");
        targetNameField = new TextField();

        Button analyzeButton = new Button("Analyze");
        resultTextArea = new TextArea();
        resultTextArea.setWrapText(true);
        resultTextArea.setEditable(false);

        analyzeButton.setOnAction(e -> analyzeText());

        grid.add(folderLabel, 0, 0);
        grid.add(folderPathField, 1, 0);
        grid.add(targetNameLabel, 0, 1);
        grid.add(targetNameField, 1, 1);
        grid.add(analyzeButton, 0, 2, 2, 1);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(grid, resultTextArea);

        Scene scene = new Scene(vbox, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void analyzeText() {
        String folderPath = folderPathField.getText();
        List<String> targetNames = new ArrayList<>();
        targetNames.add(targetNameField.getText());

        TextFileReader reader = new TextFileReader(folderPath);
        reader.readFilesInFolder();
        List<String> fileContents = reader.getFileContents();

        if (fileContents != null) {
            Map<String, Map<String, Object>> nameOccurrencesMap = Analyzer.analyzeData(fileContents, targetNames);

            StringBuilder result = new StringBuilder();
            for (String name : nameOccurrencesMap.keySet()) {
                result.append("Occurrences of '").append(name).append("': ")
                        .append(nameOccurrencesMap.get(name).get("Occurrences")).append("\n");
                result.append("Positions of '").append(name).append("': ")
                        .append(nameOccurrencesMap.get(name).get("Positions")).append("\n\n");
            }

            resultTextArea.setText(result.toString());
        } else {
            resultTextArea.setText("No file contents found.");
        }
    }
}

