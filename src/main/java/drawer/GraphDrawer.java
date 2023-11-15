package drawer;

import javafx.collections.FXCollections;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import figure.FigureInfo;
import javafx.scene.text.Text;

public class GraphDrawer
{
    private BarChart<String, Number> OccurrenceBarChart;
    private final CategoryAxis occurrenceX = new CategoryAxis();
    private final NumberAxis occurrenceY = new NumberAxis();
    private BarChart<String, Number> spanBarChart;
    private CategoryAxis spanX = new CategoryAxis();
    private NumberAxis spanY = new NumberAxis();
    XYChart.Series<String, Number> seriesStart = new XYChart.Series<>();
    XYChart.Series<String, Number> seriesEnd = new XYChart.Series<>();
    private StackedBarChart<String, Number> stackSpanBarChart;

    public GraphDrawer() {

        // set occurrences axis attribute
        occurrenceX.setLabel("Names");
        occurrenceY.setLabel("Occurrences");
        occurrenceX.setGapStartAndEnd(true);
        occurrenceX.setAnimated(false);

        spanX.setLabel("Names");
        spanY.setLabel("Position Span");
        spanY.setForceZeroInRange(false);
        spanX.setGapStartAndEnd(true);
        spanX.setAnimated(false);
        seriesStart.setName("Start");
        seriesEnd.setName("End");

        Font chineseFont = Font.font("STKaiti");
        occurrenceX.setTickLabelFont(chineseFont);
        spanX.setTickLabelFont(chineseFont);


        OccurrenceBarChart = new BarChart<>(occurrenceX, occurrenceY);
        OccurrenceBarChart.setTitle("Name Occurrences");

//        spanBarChart = new BarChart<>(spanX, spanY);
//        spanBarChart.setTitle("Position Span of Names");
        stackSpanBarChart = new StackedBarChart<>(spanX, spanY);
        stackSpanBarChart.setTitle("Position Span of Names");

        // Add the BarChart to a layout (e.g., VBox)
    }

    public BarChart<String, Number> getOccurrenceBarChart()
    {
        return OccurrenceBarChart;
    }

    public StackedBarChart<String, Number> getSpanBarChart()
    {
        return stackSpanBarChart;
    }

    public void updateOccurrenceBarGraph(Map<String, Map<String, Object>> nameOccurrencesMap) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Occurrences");

        // 获取人名和出现次数的映射
        Map<String, Integer> nameOccurrences = nameOccurrencesMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> (int) entry.getValue().get("Occurrences")));

        // 按出现次数从低到高排序
        nameOccurrences.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> {
                    String name = entry.getKey();
                    int occurrences = entry.getValue();
                    series.getData().add(new XYChart.Data<>(name, occurrences));
                });

        OccurrenceBarChart.getData().setAll(series);
    }

    public void updateSpanGraph(List<FigureInfo> targetFigureList, int totalTextLength) {

        seriesStart.getData().clear();
        seriesEnd.getData().clear();
        stackSpanBarChart.getData().clear();

        for (FigureInfo figure : targetFigureList)
        {
            List<Integer> position = figure.getPosition();
            seriesStart.getData().add(new XYChart.Data<>(figure.getName(), position.get(0)));
            seriesEnd.getData().add(new XYChart.Data<>(figure.getName(), position.get(position.size() - 1)));
        }

        stackSpanBarChart.getData().addAll(seriesStart, seriesEnd);
    }


}

