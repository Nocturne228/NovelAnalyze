package drawer;


import figure.ExampleFigureList;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.text.Font;

import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import figure.FigureInfo;
import javafx.scene.text.Text;

public class GraphDrawer
{
    private BarChart<String, Number> OccurrenceBarChart;
    private CategoryAxis occurrenceX = new CategoryAxis();
    private NumberAxis occurrenceY = new NumberAxis();
    private CategoryAxis spanX = new CategoryAxis();
    private NumberAxis spanY = new NumberAxis();
    private CategoryAxis barChartSpanX = new CategoryAxis();
    private NumberAxis barChartSpanY = new NumberAxis();
    private XYChart.Series<String, Number> seriesStart = new XYChart.Series<>();
    private XYChart.Series<String, Number> seriesEnd = new XYChart.Series<>();
    private StackedBarChart<String, Number> stackSpanBarChart;
    private BarChartExt<String, Number> spanBarChart;
    private ScatterChart<Number, Number> positionScatterChart;
    private NumberAxis scatterX = new NumberAxis();
    private NumberAxis scatterY = new NumberAxis();
    private CategoryAxis spanWithNumberX = new CategoryAxis();
    private NumberAxis spanWithNumberY = new NumberAxis();
    private BarChartSpan spanBarChartWithNumber;

    public GraphDrawer() {

        spanWithNumberX.setLabel("Name");
        spanWithNumberX.setGapStartAndEnd(true);
        spanWithNumberX.setAnimated(false);
        spanWithNumberY.setLabel("Span");
        spanWithNumberY.setAutoRanging(false);
        spanWithNumberY.setTickUnit(100);
        spanWithNumberY.setUpperBound(700);

        scatterX.setLabel("Occurrence Positions");
        scatterY.setLabel("Occurrence Times");

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
        spanWithNumberX.setTickLabelFont(chineseFont);


        positionScatterChart = new ScatterChart<>(scatterX, scatterY);
        positionScatterChart.setMinSize(550, 400);
        positionScatterChart.setTitle("Occurrence Position Chart");

        OccurrenceBarChart = new BarChart<>(occurrenceX, occurrenceY);
        OccurrenceBarChart.setTitle("Name Occurrences");

        stackSpanBarChart = new StackedBarChart<>(spanX, spanY);
        stackSpanBarChart.setTitle("Position Span of Names");

        spanBarChart = new BarChartExt<>(barChartSpanX, barChartSpanY);
        spanBarChart.setMaxSize(500, 400);
        spanBarChart.setTitle("Percentage");

        spanBarChartWithNumber = new BarChartSpan(spanWithNumberX, spanWithNumberY);
        spanBarChartWithNumber.setMaxSize(500, 400);
        spanBarChartWithNumber.setTitle("Span Chart");
    }

    public ScatterChart<Number, Number> getPositionScatterChart()
    {
        return positionScatterChart;
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

    public BarChartSpan getSpanBarChartWithNumber()
    {
        return spanBarChartWithNumber;
    }

    public void updateSpanBarChartWithNumber(List<FigureInfo> targetFigureList)
    {
        XYChart.Series series = new XYChart.Series();
        series.setName("Figure Span");

        List<FigureInfo> sortedFigureList = targetFigureList.stream()
                .sorted(Comparator.comparingInt(FigureInfo::getSpan))
                .toList();

        for (FigureInfo figure : sortedFigureList)
        {
            series.getData().add(new XYChart.Data(figure.getName(),(figure.getEnd() - figure.getStart()) / 1000));
        }

        spanBarChartWithNumber.getData().clear();
        spanBarChartWithNumber.getData().setAll(series);

    }

    public void updatePositionScatterChart(List<FigureInfo> targetFigureList)
    {
        positionScatterChart.getData().clear();
        List<List<Integer>> positionLists = new ArrayList<>();
        for (FigureInfo figure :  targetFigureList)
        {
            positionLists.add(figure.getPosition());
        }

//        List<Integer> list0 = targetFigureList.get(0).getPosition();
//        List<Integer> list1 = targetFigureList.get(1).getPosition();
//        List<Integer> list2 = targetFigureList.get(2).getPosition();
//        List<Integer> list3 = targetFigureList.get(3).getPosition();
//        List<Integer> list4 = targetFigureList.get(4).getPosition();
//        List<Integer> list5 = targetFigureList.get(5).getPosition();
//        List<Integer> list6 = targetFigureList.get(6).getPosition();
//        List<Integer> list7 = targetFigureList.get(7).getPosition();
//        List<Integer> list8 = targetFigureList.get(8).getPosition();
//        List<Integer> list9 = targetFigureList.get(9).getPosition();
//
//        List<List<Integer>> IntegerLists =
//                Arrays.asList(list0, list1, list2, list3, list4, list5, list6, list7, list8, list9);

        for (int i = 0; i < positionLists.size(); i++) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(targetFigureList.get(i).getName());

            List<Integer> integerList = positionLists.get(i);

            for (int j = 0; j < integerList.size(); j++) {
                series.getData().add(new XYChart.Data<>(integerList.get(j), j));
            }

            positionScatterChart.getData().add(series);
        }

        positionScatterChart.setStyle("-fx-font-family: 'STKaiti';");
    }

    public void switchScatterChart(List<FigureInfo> targetFigureList)
    {
        positionScatterChart.getData().clear();
        List<List<Integer>> positionLists = new ArrayList<>();
        for (FigureInfo figure :  targetFigureList)
        {
            positionLists.add(figure.getPosition());
        }

        for (int i = 0; i < positionLists.size(); i++) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(targetFigureList.get(i).getName());

            List<Integer> integerList = positionLists.get(i);

            for (int j = 0; j < integerList.size(); j++) {
                series.getData().add(new XYChart.Data<>(integerList.get(j), i + 1));
            }

            positionScatterChart.getData().add(series);
        }

        positionScatterChart.setStyle("-fx-font-family: 'STKaiti';");
    }

    public void updateSpanBarChart(List<FigureInfo> targetFigureList, int totalTextCount)
    {
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        series1.setName("Start");
        series2.setName("End");
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

        List<FigureInfo> sortedFigureList = targetFigureList.stream()
                .sorted(Comparator.comparingInt(FigureInfo::getOccurrences))
                .toList();

        series.getData().clear();
        for (FigureInfo figure : sortedFigureList)
        {
            series.getData().add(new XYChart.Data<>(figure.getName(), figure.getOccurrences()));
        }

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
        stackSpanBarChart.getData().setAll(seriesStart, seriesEnd);
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


    // ------------------------------------------------------------------------


    static class BarChartSpan<X, Y> extends BarChart<X, Y> {

        /**
         * Registry for text nodes of the bars
         */
        Map<Node, Node> nodeMap = new HashMap<>();

        public BarChartSpan(Axis xAxis, Axis yAxis) {
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
                DecimalFormat percentageFormat = new DecimalFormat("000");
                String formattedPercentage = percentageFormat.format(originalValue) + "000";

                Node text = new Text(formattedPercentage);

                text.setStyle("-fx-font-size: 11;");

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

                text.relocate(bar.getBoundsInParent().getMinX() - 10, bar.getBoundsInParent().getMinY() - 20);

            }

        }
    }
}

