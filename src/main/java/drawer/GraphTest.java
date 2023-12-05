package drawer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import figure.ExampleFigureList;
import figure.FigureInfo;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GraphTest extends Application {

    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";

    /**
     * Barchart with a clear button
     */
    @Override
    public void start(Stage stage) {

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChartExt<String, Number> bc = new BarChartExt<String, Number>(xAxis, yAxis);

        bc.setTitle("Country Summary");
        xAxis.setLabel("Country");
        yAxis.setLabel("Value");
        yAxis.setAutoRanging(false); // 禁用自动范围调整
        yAxis.setTickUnit(100);
        yAxis.setUpperBound(1000);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");
        List<FigureInfo> targetFigureList = ExampleFigureList.getTargetFigureList();
        for (FigureInfo figure : targetFigureList)
        {
            System.out.println(figure.getEnd() - figure.getStart());
            series1.getData().add(new XYChart.Data(figure.getName(),(figure.getEnd() - figure.getStart()) / 1000));
        }


        bc.getData().addAll(series1);

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> {

            bc.getData().clear();

        });

        HBox toolbar = new HBox();
        toolbar.getChildren().addAll(clearButton);

        VBox root = new VBox();
        VBox.setVgrow(bc, Priority.ALWAYS);
        root.getChildren().addAll(toolbar, bc);

        Scene scene = new Scene(root, 800, 600);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Custom barchart with text on top of bars
     *
     * @param <X>
     * @param <Y>
     */
    private static class BarChartExt<X, Y> extends BarChart<X, Y> {

        /**
         * Registry for text nodes of the bars
         */
        Map<Node, Node> nodeMap = new HashMap<>();

        public BarChartExt(Axis xAxis, Axis yAxis) {
            super(xAxis, yAxis);
        }

        /**
         * Add text for bars
         */
        @Override
        protected void seriesAdded(Series<X, Y> series, int seriesIndex) {

            super.seriesAdded(series, seriesIndex);

            for (int j = 0; j < series.getData().size(); j++) {

                Data<X, Y> item = series.getData().get(j);

                Node text = new Text(String.valueOf(item.getYValue()));
                nodeMap.put(item.getNode(), text);
                getPlotChildren().add(text);

            }

        }

        /**
         * Remove text of bars
         */
        @Override
        protected void seriesRemoved(final Series<X, Y> series) {

            for (Node bar : nodeMap.keySet()) {

                Node text = nodeMap.get(bar);
                getPlotChildren().remove(text);

            }

            nodeMap.clear();

            super.seriesRemoved(series);
        }

        /**
         * Adjust text of bars, position them on top
         */
        @Override
        protected void layoutPlotChildren() {

            super.layoutPlotChildren();

            for (Node bar : nodeMap.keySet()) {

                Node text = nodeMap.get(bar);

                text.relocate(bar.getBoundsInParent().getMinX() + 15, bar.getBoundsInParent().getMinY() - 30);

            }

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}