package drawer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import readtxt.TextFileReader;
import count.Analyzer;
import count.TextCount;
import count.FigureInfo;

public class MainGUI extends Application {

    private TextField folderPathField;
    private TextField targetNameField;
    private TextField aliasNameField;
    private ListView<String> nameListView;
    private TextArea resultTextArea;
    private BarChart<String, Number> barChart;
    private GraphDrawer graphDrawer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Text Analyzer");

        BorderPane root = new BorderPane();

        VBox leftVBox = new VBox(10);
        leftVBox.setPadding(new Insets(10, 10, 10, 10));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label folderLabel = new Label("Folder Path:");
        folderPathField = new TextField();
        Label targetNameLabel = new Label("Target Names:");
        targetNameField = new TextField();
        aliasNameField = new TextField();

        Button addButton = new Button("Add Name");
        Button analyzeButton = new Button("Analyze");
        nameListView = new ListView<>();
        resultTextArea = new TextArea();
        resultTextArea.setWrapText(true);
        resultTextArea.setEditable(false);

        addButton.setOnAction(e -> addName());
        analyzeButton.setOnAction(e -> analyzeText());

        grid.add(folderLabel, 0, 0);
        grid.add(folderPathField, 1, 0);
        grid.add(targetNameLabel, 0, 1);
        grid.add(targetNameField, 1, 1);
        grid.add(aliasNameField, 2, 1); // 别名输入框
        grid.add(addButton, 1, 2);
        grid.add(analyzeButton, 0, 2);

        VBox.setVgrow(resultTextArea, Priority.ALWAYS);

        leftVBox.getChildren().addAll(grid, nameListView);

        graphDrawer = new GraphDrawer();
        barChart = graphDrawer.getBarChart();

        resultTextArea.prefHeightProperty().bind(primaryStage.heightProperty());

        BorderPane.setMargin(resultTextArea, new Insets(10));

        root.setLeft(leftVBox);
        root.setCenter(resultTextArea);
        root.setBottom(barChart);


        Scene scene = new Scene(root, 800, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addName() {
        String targetName = targetNameField.getText().trim();
        String aliasName = aliasNameField.getText().trim();
        if (!targetName.isEmpty())
        {
            if (!nameListView.getItems().contains(targetName))
            {
                nameListView.getItems().add(targetName);
            }

            if (!aliasName.isEmpty())
            {
                nameListView.getItems().add(aliasName);
            }

            targetNameField.clear();
            aliasNameField.clear();
        }
    }

    private void analyzeText() {
        String folderPath = folderPathField.getText();
        List<String> targetNames = nameListView.getItems();//.stream().collect(Collectors.toList());
        List<FigureInfo> targetFigureList = new ArrayList<>();

        for (String name : targetNames)
        {
            FigureInfo figure = new FigureInfo(name);
            targetFigureList.add(figure);
        }

        TextFileReader reader = new TextFileReader(folderPath);
        reader.readFilesInFolder();
        List<String> fileContents = reader.getFileContents();

        if (fileContents != null) {
//            Map<String, Map<String, Object>> nameOccurrencesMap = Analyzer.analyzeData(fileContents, targetNames);
            TextCount.analyzeData(fileContents, targetFigureList);

            StringBuilder result = new StringBuilder();
            Map<String, Map<String, Object>> nameOccurrencesMap = new HashMap<>(); // 记录每个人名的出现次数和位置
            for (FigureInfo figure : targetFigureList) {
                result.append("Occurrences of '").append(figure.getName()).append("': ")
                        .append(figure.getOccurrences()).append("\n");
//                result.append("Positions of '").append(name).append("': ")
//                        .append(nameOccurrencesMap.get(name).get("Positions")).append("\n\n");

                Map<String, Object> nameInfo = new HashMap<>();
                nameInfo.put("Occurrences", figure.getOccurrences());
                nameInfo.put("Positions", figure.getPosition());
                nameOccurrencesMap.put(figure.getName(), nameInfo);
            }
            resultTextArea.setText(result.toString());

            graphDrawer.updateGraph(nameOccurrencesMap);
        } else {
            resultTextArea.setText("No file contents found.");
        }
    }
}
