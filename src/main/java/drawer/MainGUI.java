package drawer;

import figure.ExampleFigureList;
import figure.FigureListInfo;
import figure.FigureRelation;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

import fileio.TextFileReader;
import count.TextCount;
import figure.FigureInfo;
import drawer.GraphDrawer.*;

import louvain.algotest.ApplyAlgorithm;

public class MainGUI extends Application {

    private TextField folderPathField;
    private TextField targetNameField;
    private TextField aliasNameField;
    private ListView<String> nameListView;
    private TextArea resultTextArea;
    private BarChart<String, Number> occurrenceBarChart;
//    private StackedBarChart<String, Number> stackSpanBarChart;
    private BarChartSpan<String, Number> spanBarChartWithNumber;
    private BarChartExt<String, Number> spanBarChart;
    private ScatterChart<Number, Number> positionScatterChart;
    private GraphDrawer graphDrawer;
    private ComboBox<String> nameComboBox;
    private List<FigureInfo> targetFigureList;
    private ObservableList<FigureInfo> nameData;
    private ObservableList<FigureRelation> relationData;
    private Button personButton;

    private HBox bottomHBox;
    private HBox personHBox;
    private HBox algorithmButtonHBox;
    private VBox listRelationVBox;
    public BorderPane root;
    private FigureListInfo figureListInfo;
    private TableView<FigureInfo> nameTableView;
    private TableView<FigureRelation> closeTableView;
    private boolean noClick = true;


    public void show(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Text Analyzer");

//        Font chineseFont = Font.font("STKaiti");

        targetFigureList = new ArrayList<>();

        // Create primary container
        root = new BorderPane();

        // Create vertical alignment container to contain grid and tableview
        VBox leftVBox = new VBox(10);
        leftVBox.setPadding(new Insets(10, 10, 10, 10));

        // Create horizon alignment container to contain two graphs
        bottomHBox = new HBox(10);
        bottomHBox.setPadding(new Insets(10, 10, 10, 10));

        personHBox = new HBox(10);
        personHBox.setPadding(new Insets(10, 10, 10, 10));

        algorithmButtonHBox = new HBox(20);
        algorithmButtonHBox.setPadding(new Insets(10, 20, 10, 20));

        listRelationVBox = new VBox(10);
        listRelationVBox.setPadding(new Insets(10, 10, 10, 10));

        // Create Grid alignment container
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 0, 10, 10));

        // Create folder path input
        Label folderLabel = new Label("Folder Path:");
        folderPathField = new TextField("resources");

        // Create target name input
        Label targetNameLabel = new Label("Target Names:");
        targetNameField = new TextField();
        targetNameField.setPromptText("Enter a name");
        nameComboBox = new ComboBox<>();


        // Create alias name addition
        Label aliasNameLabel = new Label("Alias Names:");
        aliasNameField = new TextField();
        aliasNameField.setPromptText("Add alias name");

        // Create buttons
        Button addButton = new Button("Add Name");
        Button analyzeButton = new Button("Analyze");
        Button addAliasButton = new Button("Add Alias");
        Button initButton = new Button("Initialize");
        Button refreshButton = new Button("Reset");
        personButton = new Button("Person");
        Button selectButton = new Button("Select Person");
        Button algoButton1 = new Button("Group Algo1");
        Button algoButton2 = new Button("Group Algo2");


        refreshButton.setPrefWidth(150);
        addAliasButton.setPrefWidth(150);
        addButton.setPrefWidth(150);
        initButton.setPrefWidth(150);
        analyzeButton.setMaxWidth(150);
        personButton.setPrefWidth(150);
        selectButton.setPrefWidth(150);


        // Create nameListView
        nameListView = new ListView<>();
        nameListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        nameListView.setStyle("-fx-font-family: 'STKaiti';");

        // set result text area
        resultTextArea = new TextArea();
        resultTextArea.setWrapText(true);
        resultTextArea.setEditable(false);
        resultTextArea.setFocusTraversable(false);
        resultTextArea.setMouseTransparent(true);
        resultTextArea.setStyle("-fx-font-family: 'STKaiti';");

        // Link Button with function
        addButton.setOnAction(e -> addName());
        addAliasButton.setOnAction(e -> {
            String selectedName = nameListView.getSelectionModel().getSelectedItem();
            if (selectedName != null)
            {
                System.out.println(selectedName);
                addAliasName(selectedName);
            }
        });
        analyzeButton.setOnAction(e -> analyzeText());
        initButton.setOnAction(e -> initNames());
        refreshButton.setOnAction(e -> refreshData());
        personButton.setOnAction(e -> togglePersonView());
        selectButton.setOnAction(e -> {
            String selectedName = nameListView.getSelectionModel().getSelectedItem();
            if (selectedName != null)
            {
                personAnalyze(selectedName);
            }

        });
        // TODO applyAlgo1, 2
        algoButton1.setOnAction(e -> applyAlgo1());
        algoButton2.setOnAction(e -> applyAlgo2());


        // Create column constraint for GridPane
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(30);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(20);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth(30);
        // Create row constraint for GridPane
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(25);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(25);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(25);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(25);
        grid.getColumnConstraints().addAll(column1, column2, column3, column4);
        grid.getRowConstraints().addAll(row1, row2, row3, row4);
        grid.setMaxWidth(500);

        // add elements to grid pane
        grid.add(folderLabel, 0, 0);
        grid.add(folderPathField, 1, 0);
        grid.add(targetNameLabel, 0, 1);
        grid.add(targetNameField, 1, 1);
        grid.add(aliasNameLabel, 0, 2);
        grid.add(aliasNameField, 1, 2);
        grid.add(analyzeButton, 1, 3);
        grid.add(refreshButton, 0, 3);
        grid.add(nameListView, 3, 1);
        grid.add(selectButton, 3, 0);

//        GridPane.setRowSpan(nameListView, 4);
        GridPane.setRowSpan(nameListView, 3);
        grid.add(addAliasButton, 2, 2);
        grid.add(initButton, 2, 3);
        grid.add(addButton, 2, 1);
        grid.add(personButton, 2, 0);

        // Set resultTextArea growable
        VBox.setVgrow(resultTextArea, Priority.ALWAYS);
        resultTextArea.prefHeightProperty().bind(primaryStage.heightProperty());
        BorderPane.setMargin(resultTextArea, new Insets(10));


        // show names in format of table

        nameTableView = new TableView<>();
        setNameTableView();

        this.relationData = FXCollections.observableArrayList();

        figureListInfo = new FigureListInfo(ExampleFigureList.getTargetFigureList());

        closeTableView = new TableView<>();
        setCloseTableView();


        // add grid and nameListView to container
        leftVBox.getChildren().addAll(grid, nameTableView);
        leftVBox.setMaxSize(500, 360);

        // draw bar chart

        graphDrawer = new GraphDrawer();
        occurrenceBarChart = graphDrawer.getOccurrenceBarChart();
//        stackSpanBarChart = graphDrawer.getStackSpanBarChart();
        spanBarChart = graphDrawer.getSpanBarChart();
        positionScatterChart = graphDrawer.getPositionScatterChart();
        positionScatterChart.setOnMouseClicked(
                e -> {
                    if (noClick)
                        graphDrawer.switchScatterChart(targetFigureList);
                    else
                        graphDrawer.updatePositionScatterChart(targetFigureList);
                    noClick = !noClick;
                });
        spanBarChartWithNumber = graphDrawer.getSpanBarChartWithNumber();

//        bottomHBox.getChildren().addAll(occurrenceBarChart, stackSpanBarChart);
        bottomHBox.getChildren().addAll(occurrenceBarChart, spanBarChartWithNumber);

        personHBox.setVisible(false);
        personHBox.getChildren().addAll(positionScatterChart, closeTableView);

        algorithmButtonHBox.getChildren().addAll(algoButton1, algoButton2);
        algorithmButtonHBox.setAlignment(Pos.CENTER);
        listRelationVBox.getChildren().addAll(algorithmButtonHBox, resultTextArea);


        // set root
        root.setLeft(leftVBox);
        root.setCenter(spanBarChart);
        root.setBottom(bottomHBox);

        Scene scene = new Scene(root, 1000, 800);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void refreshData()
    {
        targetFigureList.clear();
        resultTextArea.clear();
        nameListView.getItems().clear();
        nameComboBox.getItems().clear();
        nameData.clear();

        occurrenceBarChart.getData().clear();
        spanBarChart.getData().clear();
        spanBarChartWithNumber.getData().clear();
//        stackSpanBarChart.getData().clear();

    }

    public void personAnalyze(String figureName)
    {
        String[][] figures = figureListInfo.getRelationTable();
        List<List<Integer>> relationMatrix = figureListInfo.getRelationMatrix();
        int label = -1;
        for (int i = 0; i < targetFigureList.size(); i++)
        {
            if (figures[i][0].equals(figureName))
            {
                label = i;
                break;
            }
        }
        int[][] correspondenceRelationDataMatrix = figureListInfo.getCorrespondenceRelationDataTable();
        StringBuilder result = new StringBuilder();


        result.append(figureName).append("联系最紧密的九个人依次是:").append("\n");
        for (int i = 1; i < targetFigureList.size(); i++)
        {
//            if (i == label) {continue;}
            result.append(figures[label][i]).append(" (").append(correspondenceRelationDataMatrix[label][i]).append(") ");
            if (i != targetFigureList.size()-1)
            {
                result.append(" -> ");
            }
        }
        result.append("\n\n");
        result.append(resultTextArea.getText()).append("\n");


        resultTextArea.setText(result.toString());
    }


    private void initNames()
    {
        targetFigureList.clear();
        targetFigureList = ExampleFigureList.getTargetFigureList();

        for (FigureInfo figure : targetFigureList)
        {
            String targetName = figure.getName();
            nameListView.getItems().add(targetName);
            if (!nameListView.getItems().contains(targetName))
            {
                nameListView.getItems().add(targetName);
            }

            nameComboBox.getItems().add(targetName);
            if (!nameComboBox.getItems().contains(targetName))
            {
                nameComboBox.getItems().add(targetName);
            }

            this.nameData.add(figure);
        }

    }

    private void setCloseTableView()
    {

        String[][] relationTable = figureListInfo.getRelationTable();

        for (int i = 0; i < 10; i++)
        {
            FigureRelation figureRelation = new FigureRelation(relationTable[i][0]);
            for (int j = 1; j < 10; j++)
            {
                figureRelation.addName(relationTable[i][j]);
            }
            figureRelation.setFigrues();
            relationData.add(figureRelation);
        }

        TableColumn<FigureRelation, String> figureColumn = new TableColumn<>("Name");
        figureColumn.setCellValueFactory(
                new PropertyValueFactory<>("figureName")
        );
        TableColumn<FigureRelation, String> relationColumn1 = new TableColumn<>("Figure1");
        relationColumn1.setCellValueFactory(
                new PropertyValueFactory<>("figure1")
        );
        TableColumn<FigureRelation, String> relationColumn2 = new TableColumn<>("Figure2");
        relationColumn2.setCellValueFactory(
                new PropertyValueFactory<>("figure2")
        );
        TableColumn<FigureRelation, String> relationColumn3 = new TableColumn<>("Figure3");
        relationColumn3.setCellValueFactory(
                new PropertyValueFactory<>("figure3")
        );
        TableColumn<FigureRelation, String> relationColumn4 = new TableColumn<>("Figure4");
        relationColumn4.setCellValueFactory(
                new PropertyValueFactory<>("figure4")
        );
        TableColumn<FigureRelation, String> relationColumn5 = new TableColumn<>("Figure5");
        relationColumn5.setCellValueFactory(
                new PropertyValueFactory<>("figure5")
        );
        TableColumn<FigureRelation, String> relationColumn6 = new TableColumn<>("Figure6");
        relationColumn6.setCellValueFactory(
                new PropertyValueFactory<>("figure6")
        );
        TableColumn<FigureRelation, String> relationColumn7 = new TableColumn<>("Figure7");
        relationColumn7.setCellValueFactory(
                new PropertyValueFactory<>("figure7")
        );
        TableColumn<FigureRelation, String> relationColumn8 = new TableColumn<>("Figure8");
        relationColumn8.setCellValueFactory(
                new PropertyValueFactory<>("figure8")
        );
        TableColumn<FigureRelation, String> relationColumn9 = new TableColumn<>("Figure9");
        relationColumn9.setCellValueFactory(
                new PropertyValueFactory<>("figure9")
        );
        closeTableView.setItems(relationData);

        closeTableView.getColumns().addAll
                (
                        figureColumn, relationColumn1, relationColumn2, relationColumn3, relationColumn4,
                        relationColumn5, relationColumn6, relationColumn7, relationColumn8, relationColumn9
                );

        closeTableView.setStyle("-fx-font-family: 'STKaiti';");

    }

    private void setNameTableView()
    {
        this.nameData = FXCollections.observableArrayList();

        TableColumn<FigureInfo, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        TableColumn<FigureInfo, String> aliasName1Column = new TableColumn<>("Alias1");
        aliasName1Column.setCellValueFactory(
                new PropertyValueFactory<>("aliasName1")
        );
        TableColumn<FigureInfo, String> aliasName2Column = new TableColumn<>("Alias2");
        aliasName2Column.setCellValueFactory(
                new PropertyValueFactory<>("aliasName2")
        );
        TableColumn<FigureInfo, String> aliasName3Column = new TableColumn<>("Alias3");
        aliasName3Column.setCellValueFactory(
                new PropertyValueFactory<>("aliasName3")
        );
        nameTableView.setItems(this.nameData);
        nameTableView.getColumns().addAll(nameColumn, aliasName1Column, aliasName2Column, aliasName3Column);
        nameTableView.setStyle("-fx-font-family: 'STKaiti';");
    }

    private void addName()
    {
        String targetName = targetNameField.getText().trim();
        System.out.println(targetName);
        if (!targetName.isEmpty())
        {
            FigureInfo figure = new FigureInfo(targetName);
            targetFigureList.add(figure);

            nameComboBox.getItems().add(targetName);
            if (!nameComboBox.getItems().contains(targetName))
            {
                nameComboBox.getItems().add(targetName);
            }

            nameListView.getItems().add(targetName);
            if (!nameListView.getItems().contains(targetName))
            {
                nameListView.getItems().add(targetName);
            }

            int order = figureListInfo.getListLength() + 1;
            figure.setLabel(order);

            this.nameData.add(figure);

            targetNameField.clear();
        }
    }

    private void addAliasName(String targetName)
    {
        String alias = aliasNameField.getText().trim();
        System.out.println("alias name get:");
        System.out.println(alias);
        for (FigureInfo figure : targetFigureList)
        {
            if (targetName.equals(figure.getName()))
            {
                figure.setAliasName(alias);

                break;
            }
        }

        aliasNameField.clear();
        System.out.println("Table Refreshed");
    }

    private void analyzeText() {

        if (targetFigureList.isEmpty())
        {
            return;
        }

        for (FigureInfo figure : targetFigureList)
        {
            figure.refreshData();
        }

        String folderPath = "/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/" + folderPathField.getText();

        TextFileReader reader = new TextFileReader(folderPath);
        reader.readFilesInFolder();
        List<String> fileContents = reader.getFileContents();


        int totalCharacterCount = fileContents.stream()
                .mapToInt(String::length)
                .sum();

        System.out.println(totalCharacterCount);


        if (fileContents != null)
        {
            TextCount.analyzeData(fileContents, targetFigureList);

            StringBuilder result = new StringBuilder();

            Map<String, Map<String, Object>> nameOccurrencesMap = new HashMap<>(); // 记录每个人名的出现次数和位置
            for (FigureInfo figure : targetFigureList) {

                Map<String, Object> nameInfo = new HashMap<>();
                nameInfo.put("Occurrences", figure.getOccurrences());
                nameInfo.put("Positions", figure.getPosition());
                nameOccurrencesMap.put(figure.getName(), nameInfo);
            }



            graphDrawer.updateOccurrenceBarChart(targetFigureList);
//            graphDrawer.updateStackSpanChart(targetFigureList);
            graphDrawer.updateSpanBarChart(targetFigureList, totalCharacterCount);
            graphDrawer.updatePositionScatterChart(targetFigureList);
            graphDrawer.updateSpanBarChartWithNumber(targetFigureList);

        }
        else
        {
            System.out.println("No contents found");
        }
    }

    private void togglePersonView() {
        boolean isBottomVisible = bottomHBox.isVisible();

        if (isBottomVisible) {
            bottomHBox.setVisible(false);
            spanBarChart.setVisible(false);

            root.setCenter(listRelationVBox);
            root.setBottom(personHBox);

            listRelationVBox.setVisible(true);
            closeTableView.setVisible(true);
            personHBox.setVisible(true);

            personButton.setText("Back");
            personButton.setOnAction(e -> togglePersonView());
        } else {
            personHBox.setVisible(false);
            closeTableView.setVisible(false);
            listRelationVBox.setVisible(false);

            bottomHBox.setVisible(true);
            spanBarChart.setVisible(true);

            root.setBottom(bottomHBox);
            root.setCenter(spanBarChart);

            personButton.setText("Person");
            personButton.setOnAction(e -> togglePersonView());
        }
    }

    public void applyAlgo1()
    {
        int[] answer = ApplyAlgorithm.Louvain();
        StringBuilder stringBuilder = new StringBuilder();
        List<String> result = ApplyAlgorithm.printNameByGroup(answer);
        stringBuilder.append("应用算法1的团队划分结果为:\n");
        for (String string : result)
        {
            stringBuilder.append(string).append("\n");
        }
        stringBuilder.append("\n");
        stringBuilder.append(resultTextArea.getText());
        resultTextArea.setText(stringBuilder.toString());
    }

    public void applyAlgo2()
    {
        int[] answer = ApplyAlgorithm.pythonGreedyModularityCommunityAlgo();
        StringBuilder stringBuilder = new StringBuilder();
        List<String> result = ApplyAlgorithm.printNameByGroup(answer);
        stringBuilder.append("应用算法2的团队划分结果为:\n");
        for (String string : result)
        {
            stringBuilder.append(string).append("\n");
        }
        stringBuilder.append("\n");
        stringBuilder.append(resultTextArea.getText());
        resultTextArea.setText(stringBuilder.toString());
    }
}
