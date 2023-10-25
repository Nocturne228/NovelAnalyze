package drawer;

import figure.FigureInfo;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import jdk.jfr.Category;

import java.util.Collections;
import java.util.List;

public class ScopeBarChartDrawer
{
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private BarChart<String, Number> positionBarChart;

    public ScopeBarChartDrawer()
    {
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        xAxis.setLabel("Names");
        yAxis.setLabel("Position Span");

         positionBarChart = new BarChart<>(xAxis, yAxis);
        positionBarChart.setTitle("Position Span of Names");
    }

    public void updateGraph(List<FigureInfo> targetFigureList)
    {
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();

        for (FigureInfo figure : targetFigureList)
        {
            dataSeries.getData().add(new XYChart.Data<>(figure.getName(), calculatePositionSpan(figure)));
        }
        positionBarChart.getData().add(dataSeries);

        positionBarChart.setBarGap(3);
    }

    private Number calculatePositionSpan(FigureInfo figure) {
        List<Integer> positions = figure.getPosition();
        if (positions.isEmpty()) {
            return 0;
        } else {
            int minPosition = Collections.min(positions);
            int maxPosition = Collections.max(positions);
            return maxPosition - minPosition + 1;
        }
    }

}
