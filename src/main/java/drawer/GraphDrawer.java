package drawer;

import fileio.CSVWriter;
import fileio.CallPythonScript;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import figure.FigureInfo;
import javafx.scene.text.Text;

public class GraphDrawer
{
    private BarChart<String, Number> OccurrenceBarChart;
    private final CategoryAxis occurrenceX = new CategoryAxis();
    private final NumberAxis occurrenceY = new NumberAxis();
    private CategoryAxis spanX = new CategoryAxis();
    private NumberAxis spanY = new NumberAxis();
    private CategoryAxis barChartSpanX = new CategoryAxis();
    private NumberAxis barChartSpanY = new NumberAxis();
    private
    XYChart.Series<String, Number> seriesStart = new XYChart.Series<>();
    XYChart.Series<String, Number> seriesEnd = new XYChart.Series<>();
    private StackedBarChart<String, Number> stackSpanBarChart;
    private BarChartExt<String, Number> spanBarChart;
    private Image occurrenceScatterPlotImage;

    public GraphDrawer() {

        // set occurrences axis attribute
        occurrenceX.setLabel("Names");
        occurrenceY.setLabel("Occurrences");
        occurrenceX.setGapStartAndEnd(true);
        occurrenceX.setAnimated(false);

        spanX.setLabel("Names");
        spanY.setLabel("Position Span");
        spanX.setGapStartAndEnd(true);
        spanX.setAnimated(false);
        seriesStart.setName("Start");
        seriesEnd.setName("End");

        barChartSpanX.setLabel("Names");
        barChartSpanY.setLabel("Span Percentage");
        barChartSpanY.setAutoRanging(true);
        barChartSpanX.setGapStartAndEnd(true);
        barChartSpanX.setAnimated(false);


        Font chineseFont = Font.font("STKaiti");
        occurrenceX.setTickLabelFont(chineseFont);
        spanX.setTickLabelFont(chineseFont);
        barChartSpanX.setTickLabelFont(chineseFont);


        OccurrenceBarChart = new BarChart<>(occurrenceX, occurrenceY);
        OccurrenceBarChart.setTitle("Name Occurrences");

//        spanBarChart = new BarChart<>(spanX, spanY);
//        spanBarChart.setTitle("Position Span of Names");
        stackSpanBarChart = new StackedBarChart<>(spanX, spanY);
        stackSpanBarChart.setTitle("Position Span of Names");

        spanBarChart = new BarChartExt<>(barChartSpanX, barChartSpanY);
        spanBarChart.setTitle("Percentage");

        occurrenceScatterPlotImage = new Image("/scatter_plot.png");

    }

    public BarChart<String, Number> getOccurrenceBarChart()
    {
        return OccurrenceBarChart;
    }

    public StackedBarChart<String, Number> getStackSpanBarChart()
    {
        return stackSpanBarChart;
    }

    public BarChartExt<String, Number> getSpanBarChart()
    {
        return spanBarChart;
    }

    public ImageView getOccurrenceScatterPlotImageView()
    {
        ImageView occurrenceScatterPlotImageView = new ImageView(occurrenceScatterPlotImage);
        return occurrenceScatterPlotImageView;
    }

    public void updateOccurrenceScaterPlotImage(List<FigureInfo> targetFigureLIst)
    {
//        String csvDataFilePath = "/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/resources/data.csv";
//        CSVWriter csvWriter = new CSVWriter();
//        csvWriter.writeDataCSV(csvDataFilePath, targetFigureLIst);
//        CallPythonScript callPythonScript = new CallPythonScript();
//        String pythonPlotScriptPath = "/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/java/fileio/DrawScatterPlot.py";
//        callPythonScript.callPython(pythonPlotScriptPath);

        this.occurrenceScatterPlotImage = new Image("/scatter_plot.png");
    }

    public void updateSpanBarChart(List<FigureInfo> targetFigureList, int totalTextCount)
    {
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        series1.setName("Start");
        series2.setName("End");
//        List<FigureInfo> sortedFigureList = targetFigureList.stream()
//                .sorted(Comparator.comparingInt(FigureInfo::getSpan))
//                .toList();
        for (FigureInfo figure : targetFigureList)
        {
            double startPercentage = (double)figure.getStart() / totalTextCount;
            double endPercentage = (double)figure.getEnd() / totalTextCount;

            series1.getData().add(new XYChart.Data(figure.getName(), startPercentage));
            series2.getData().add(new XYChart.Data(figure.getName(), endPercentage));

        }
        spanBarChart.getData().clear();
        spanBarChart.getData().setAll(series1, series2);
    }

    public void updateOccurrenceBarChart(List<FigureInfo> targetFigureList) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Occurrences");
        ObservableList<String> data = FXCollections.observableArrayList();

        List<FigureInfo> sortedFigureList = targetFigureList.stream()
                .sorted(Comparator.comparingInt(FigureInfo::getOccurrences))
                .toList();

        series.getData().clear();
        for (FigureInfo figure : sortedFigureList)
        {
            data.add(figure.getName());
            System.out.println(figure.getName() + figure.getOccurrences());
            series.getData().add(new XYChart.Data<>(figure.getName(), figure.getOccurrences()));
        }
        occurrenceX.setCategories(data);

        OccurrenceBarChart.getData().clear();
        OccurrenceBarChart.getData().setAll(series);

    }

    public void updateStackSpanChart(List<FigureInfo> targetFigureList) {

        seriesStart.getData().clear();
        seriesEnd.getData().clear();
        stackSpanBarChart.getData().clear();

        List<FigureInfo> sortedFigureList = targetFigureList.stream()
                .sorted(Comparator.comparingInt(FigureInfo::getSpan))
                .toList();

        for (FigureInfo figure : sortedFigureList)
        {
            seriesStart.getData().add(new XYChart.Data<>(figure.getName(), figure.getStart()));
            seriesEnd.getData().add(new XYChart.Data<>(figure.getName(), figure.getEnd()));
        }

        stackSpanBarChart.getData().clear();
        stackSpanBarChart.getData().addAll(seriesStart, seriesEnd);
    }

    /**
     * Custom barchart with text on top of bars
     *
     * @param <X>
     * @param <Y>
     */
    static class BarChartExt<X, Y> extends BarChart<X, Y> {

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

                String value = String.valueOf(item.getYValue());
                double originalValue = Double.parseDouble(value);
                DecimalFormat percentageFormat = new DecimalFormat("0.00%");
                String formattedPercentage = percentageFormat.format(originalValue);

                Node text = new Text(formattedPercentage);
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
                text.toFront();
                text.relocate(bar.getBoundsInParent().getMinX() - 10, bar.getBoundsInParent().getMinY() - 20);
            }
        }
    }



}

