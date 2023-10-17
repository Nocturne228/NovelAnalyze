package drawer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class GraphDrawer extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("散点图示例");

        // 创建x轴和y轴
        NumberAxis xAxis = new NumberAxis(0, 1, 0.1);
        NumberAxis yAxis = new NumberAxis(0, 1, 0.1);

        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setTitle("散点图示例");

        // 创建数据系列
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("数据点");

        // 添加数据点
        series.getData().add(new XYChart.Data<>(0.2, 0.8));
        series.getData().add(new XYChart.Data<>(0.4, 0.6));
        series.getData().add(new XYChart.Data<>(0.6, 0.4));
        series.getData().add(new XYChart.Data<>(0.8, 0.2));

        scatterChart.getData().add(series);

        Scene scene = new Scene(scatterChart, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

