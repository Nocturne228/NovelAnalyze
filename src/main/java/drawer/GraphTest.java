package drawer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GraphTest extends Application {

    @Override
    public void start(Stage stage) {
        // 创建一个Pane容器
        Pane pane = new Pane();

        // 加载图像
        Image image = new Image("/scatter_plot.png");
        // 创建ImageView来显示图像
        ImageView imageView = new ImageView(image);

        // 将ImageView添加到Pane中
        pane.getChildren().add(imageView);

        // 创建Scene并将Pane添加到Scene中
        Scene scene = new Scene(pane, 400, 300);

        // 设置Stage的标题和Scene
        stage.setTitle("Image in Pane Example");
        stage.setScene(scene);

        // 显示Stage
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
