package drawer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

import fileio.TextFileReader;
import count.TextCount;
import figure.FigureInfo;
import drawer.GraphDrawer.*;

public class MainGUI extends Application {

    private TextField folderPathField;
    private TextField targetNameField;
    private TextField aliasNameField;
    private ListView<String> nameListView;
    private TextArea resultTextArea;
    private BarChart<String, Number> occurrenceBarChart;
    private StackedBarChart<String, Number> stackSpanBarChart;
    private BarChartExt<String, Number> spanBarChart;
    private GraphDrawer graphDrawer;
    private ComboBox<String> nameComboBox;
    private List<FigureInfo> targetFigureList;
    private ObservableList<FigureInfo> data;
    private Button personButton;
    private HBox bottomHBox;
    private HBox personHBox;
    public BorderPane root;
    private Image occurrenceScatterPlotImage;
//    TableView<FigureRelation> closeTableView = new TableView<>();


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

        // set result text area
        resultTextArea = new TextArea();
        resultTextArea.setWrapText(true);
        resultTextArea.setEditable(false);

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

        TableView<FigureInfo> nameTableView = new TableView<>();

        this.data = FXCollections.observableArrayList();

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
        nameTableView.setItems(this.data);
        nameTableView.getColumns().addAll(nameColumn, aliasName1Column, aliasName2Column, aliasName3Column);


        // add grid and nameListView to container
        leftVBox.getChildren().addAll(grid, nameTableView);
        leftVBox.setMaxSize(500, 360);

        // draw bar chart

        graphDrawer = new GraphDrawer();
        occurrenceBarChart = graphDrawer.getOccurrenceBarChart();
        stackSpanBarChart = graphDrawer.getStackSpanBarChart();
        spanBarChart = graphDrawer.getSpanBarChart();

        bottomHBox.getChildren().addAll(occurrenceBarChart, stackSpanBarChart);


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
        data.clear();

        occurrenceBarChart.getData().clear();
        spanBarChart.getData().clear();
        stackSpanBarChart.getData().clear();

    }

    public void personAnalyze(String figureName)
    {
    }

    // TODO: tableview of close figures: public void update closeTable()

    private void initNames()
    {
        targetFigureList.clear();
        targetFigureList.add(new FigureInfo(("曹操")));
        targetFigureList.get(0).setAliasName("操");
        targetFigureList.get(0).setAliasName("孟德");
        targetFigureList.get(0).setAliasName("阿瞒");
        targetFigureList.get(0).setLabel(0);

        targetFigureList.add(new FigureInfo(("刘备")));
        targetFigureList.get(1).setAliasName("玄德");
        targetFigureList.get(1).setAliasName("皇叔");
        targetFigureList.get(1).setAliasName("使君");
        targetFigureList.get(1).setLabel(1);

        targetFigureList.add(new FigureInfo(("孙权")));
        targetFigureList.get(2).setAliasName("仲谋");
        targetFigureList.get(2).setAliasName("吴侯");
        targetFigureList.get(2).setLabel(2);

        targetFigureList.add(new FigureInfo(("关羽")));
        targetFigureList.get(3).setAliasName("云长");
        targetFigureList.get(3).setAliasName("关公");
        targetFigureList.get(3).setLabel(3);

        targetFigureList.add(new FigureInfo(("诸葛亮")));
        targetFigureList.get(4).setAliasName("孔明");
        targetFigureList.get(4).setAliasName("卧龙");
        targetFigureList.get(4).setLabel(4);

        targetFigureList.add(new FigureInfo(("郭嘉")));
        targetFigureList.get(5).setAliasName("奉孝");
        targetFigureList.get(5).setLabel(5);

        targetFigureList.add(new FigureInfo(("周瑜")));
        targetFigureList.get(6).setAliasName("公瑾");
        targetFigureList.get(6).setAliasName("周郎");
        targetFigureList.get(6).setLabel(6);

        targetFigureList.add(new FigureInfo(("赵云")));
        targetFigureList.get(7).setAliasName("子龙");
        targetFigureList.get(7).setLabel(7);

        targetFigureList.add(new FigureInfo(("吕布")));
        targetFigureList.get(8).setAliasName("奉先");
        targetFigureList.get(8).setAliasName("温侯");
        targetFigureList.get(8).setLabel(8);

        targetFigureList.add(new FigureInfo(("董卓")));
        targetFigureList.get(9).setAliasName("仲颖");
        targetFigureList.get(9).setLabel(9);

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

            this.data.add(figure);
        }
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

            this.data.add(figure);

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

            // TODO: 将map改为直接对targetFigureList操作
            Map<String, Map<String, Object>> nameOccurrencesMap = new HashMap<>(); // 记录每个人名的出现次数和位置
            for (FigureInfo figure : targetFigureList) {
                result.append("Occurrences of '").append(figure.getName()).append("': ")
                        .append(figure.getOccurrences()).append("\n");
                result.append("Positions of '").append(figure.getName()).append("': ")
                        .append(figure.getPosition()).append("\n\n");

                Map<String, Object> nameInfo = new HashMap<>();
                nameInfo.put("Occurrences", figure.getOccurrences());
                nameInfo.put("Positions", figure.getPosition());
                nameOccurrencesMap.put(figure.getName(), nameInfo);
            }
            resultTextArea.setText(result.toString());



            graphDrawer.updateOccurrenceBarChart(targetFigureList);
            graphDrawer.updateStackSpanChart(targetFigureList);
            graphDrawer.updateSpanBarChart(targetFigureList, totalCharacterCount);

            occurrenceScatterPlotImage = new Image("/scatter_plot.png");

        }
        else
        {
            resultTextArea.setText("No file contents found.");
        }
    }

    private void togglePersonView() {
        boolean isBottomVisible = bottomHBox.isVisible();

        if (isBottomVisible) {
            bottomHBox.setVisible(false);
            spanBarChart.setVisible(false);

            root.setBottom(personHBox);
            //TODO: root.setCenter()
            ImageView occurrenceScatterPlotImageView = new ImageView(occurrenceScatterPlotImage);
            root.setCenter(occurrenceScatterPlotImageView);

            personButton.setText("Back");
            personButton.setOnAction(e -> togglePersonView());
            // 还可以执行其他隐藏的逻辑，比如将 VBox 显示出来
            // vbox.setVisible(true);
        } else {
            root.getCenter().setVisible(false);
            bottomHBox.setVisible(true);
            spanBarChart.setVisible(true);

            root.setBottom(bottomHBox);
            root.setCenter(spanBarChart);
            //TODO: root.setCenter()

            personButton.setText("Person");
            personButton.setOnAction(e -> togglePersonView());
            // 反之，隐藏 VBox
            // vbox.setVisible(false);
        }
    }
}
