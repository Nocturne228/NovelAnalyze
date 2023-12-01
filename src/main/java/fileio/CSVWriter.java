package fileio;

import figure.FigureInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVWriter
{
    public void writeMatrixCSV(String csvFilePath, List<List<Integer>> matrix)
    {
        File file = new File(csvFilePath);
        if (file.exists())
        {
            file.delete();
        }

        try (FileWriter writer = new FileWriter(csvFilePath)) {
            // 遍历矩阵并写入 CSV 文件
            for (List<Integer> row : matrix)
            {
                for (int i = 0; i < row.size(); i++)
                {
                    writer.append(String.valueOf(row.get(i)));
                    if (i < row.size() - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }
            System.out.println("CSV successfully written" + csvFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataCSV(String csvFilePath, List<FigureInfo> targetFigureList)
    {
        List<Integer> dataList = new ArrayList<>();
        List<Integer> labelList = new ArrayList<>();
        for (FigureInfo figure : targetFigureList)
        {
            dataList.addAll(figure.getPosition());
            for (int i = 0; i < figure.getPosition().size(); i++)
            {
                labelList.add(figure.getLabel());
            }
        }

        int [][] dataSet = new int[dataList.size()][2];
        for (int i = 0; i < dataList.size(); i++)
        {
            dataSet[i][0] = dataList.get(i);
            dataSet[i][1] = labelList.get(i);
        }
        String dataCol = "data";
        String labelCol = "label";
        String nameCol = "name";

        try (FileWriter csvWriter = new FileWriter(csvFilePath)) {
            csvWriter.append(dataCol + "," + labelCol);
            csvWriter.append("\n");
            for (int[] row : dataSet) {
                for (int i = 0; i < row.length; i++) {
                    csvWriter.append(String.valueOf(row[i]));
                    if (i < row.length - 1) {
                        csvWriter.append(",");
                    }
                }
                csvWriter.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        int lastBackslashIndex = csvFilePath.lastIndexOf('/');

        // 判断是否找到了反斜杠
        if (lastBackslashIndex != -1)
        {
            // 截取字符串，保留最后一个反斜杠之前的部分
            String csvLabelFilePath = csvFilePath.substring(0, lastBackslashIndex);
            csvLabelFilePath += "/label.csv";
            try (FileWriter csvWriter = new FileWriter(csvLabelFilePath)) {
                csvWriter.append(labelCol + "," + nameCol);
                csvWriter.append("\n");
                for (int i = 0; i < 10; i++)
                {
                    FigureInfo figure = targetFigureList.get(i);
                    csvWriter.append(String.valueOf(figure.getLabel()) + "," + figure.getName() + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
