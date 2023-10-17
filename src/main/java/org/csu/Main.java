package org.csu;

import readtxt.TextFileReader;
import count.Analyzer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String folderPath = "resource/test";
        List<String> targetNames = new ArrayList<>();
        targetNames.add("曹操");
        targetNames.add("刘备");
        targetNames.add("孙权");
        targetNames.add("关羽");
        targetNames.add("张飞");
        targetNames.add("赵云");
        targetNames.add("黄忠");
        targetNames.add("诸葛亮");
        targetNames.add("周瑜");
        targetNames.add("陆逊");

        TextFileReader reader = new TextFileReader(folderPath);
        reader.readFilesInFolder();
        List<String> fileContents = reader.getFileContents();

        if (fileContents != null) {
            Map<String, Map<String, Object>> nameOccurrencesMap = Analyzer.analyzeData(fileContents, targetNames);

            // 打印每个人名在不同章节的出现次数和位置
            for (String name : targetNames) {
                System.out.println("Occurrences of '" + name + "': " + nameOccurrencesMap.get(name).get("Occurrences"));
                System.out.println("Positions of '" + name + "': " + nameOccurrencesMap.get(name).get("Positions"));
            }
        } else {
            System.out.println("No file contents found.");
        }
    }
}
