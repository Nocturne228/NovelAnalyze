package drawer;

import figure.ExampleFigureList;
import figure.FigureInfo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScatterDrawer {

    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    private ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
    List<List<Integer>> integerLists = new ArrayList<>();


    public ScatterDrawer() {

        xAxis.setLabel("Occurrence Positions");
        yAxis.setLabel("Occurrence Times");
        scatterChart.setTitle("Occurrence Position Chart");
    }

    public ScatterChart<Number, Number> getScatterChart()
    {
        return scatterChart;
    }

    private List<List<Integer>> generateIntegerLists(List<FigureInfo> targetFigureList) {
        // 生成十个整数列表（假设列表中的元素均不相等）
        List<Integer> list0 = targetFigureList.get(0).getPosition();
        List<Integer> list1 = targetFigureList.get(1).getPosition();
        List<Integer> list2 = targetFigureList.get(2).getPosition();
        List<Integer> list3 = targetFigureList.get(3).getPosition();
        List<Integer> list4 = targetFigureList.get(4).getPosition();
        List<Integer> list5 = targetFigureList.get(5).getPosition();
        List<Integer> list6 = targetFigureList.get(6).getPosition();
        List<Integer> list7 = targetFigureList.get(7).getPosition();
        List<Integer> list8 = targetFigureList.get(8).getPosition();
        List<Integer> list9 = targetFigureList.get(9).getPosition();

        return Arrays.asList(list0, list1, list2, list3, list4, list5, list6, list7, list8, list9);
    }
    public void updateScatterDrawer(List<FigureInfo> targetFigureList)
    {
        integerLists = generateIntegerLists(targetFigureList);
        for (int i = 0; i < integerLists.size(); i++) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName("List " + (i + 1));

            List<Integer> integerList = integerLists.get(i);

            for (int j = 0; j < integerList.size(); j++) {
                series.getData().add(new XYChart.Data<>(integerList.get(j), j));
            }

            scatterChart.getData().add(series);
        }
    }

}
