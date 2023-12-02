package drawer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GraphTest extends JFrame {
    public GraphTest() {
        setTitle("Table to Image Example");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 创建表格数据
        Object[][] data = {
                {"John", 25, "Male"},
                {"Jane", 30, "Female"},
                {"Doe", 35, "Male"}
        };

        // 创建表格列名
        String[] columnNames = {"Name", "Age", "Gender"};

        // 创建 JTable
        JTable table = new JTable(data, columnNames);

        // 将表格放入滚动窗格中
        JScrollPane scrollPane = new JScrollPane(table);

        // 将滚动窗格添加到 JFrame
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // 设置 JFrame 大小并显示
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(false); // 设置为 false，不显示窗口

        // 调用保存图像的方法
        saveTableImage(table, "/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/resources/table.png");
    }

    private void saveTableImage(JTable table, String filePath) {
        SwingUtilities.invokeLater(() -> {
            try {
                int width = table.getWidth();
                int height = table.getHeight() + table.getTableHeader().getHeight(); // 添加表头的高度

                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = image.createGraphics();

                // 设置白色背景
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, width, height);

                // 绘制表格内容
                table.paint(g2d);
                table.getTableHeader().paint(g2d); // 绘制表头

                g2d.dispose();

                // 保存为 PNG 文件
                ImageIO.write(image, "png", new File(filePath));
                System.out.println("Table saved to: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GraphTest());
    }
}
