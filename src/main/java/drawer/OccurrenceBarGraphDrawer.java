package drawer;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Font;

import java.util.Map;
import java.util.stream.Collectors;

public class OccurrenceBarGraphDrawer
{
    private final BarChart<String, Number> barChart;
    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();

    public OccurrenceBarGraphDrawer() {
        xAxis.setLabel("Names");
        yAxis.setLabel("Occurrences");
        xAxis.setGapStartAndEnd(true); // 使标签在起始和结束处有间隔
        xAxis.setAnimated(false);
        Font chineseFont = Font.font("STKaiti");
        xAxis.setTickLabelFont(chineseFont);


        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Name Occurrences");

        // Add the BarChart to a layout (e.g., VBox)
    }

    public BarChart<String, Number> getBarChart() {
        return barChart;
    }

    public void updateBarGraph(Map<String, Map<String, Object>> nameOccurrencesMap) {
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

        barChart.getData().setAll(series);
    }
}
