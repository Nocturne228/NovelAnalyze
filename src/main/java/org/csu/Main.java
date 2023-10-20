package org.csu;

import readtxt.TextFileReader;
import count.FigureInfo;
import count.Analyzer;
import count.TextCount;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String folderPath = "/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/resources";

        FigureInfo[] targetFigureList = {
                new FigureInfo("曹操"),
                new FigureInfo("刘备"),
                new FigureInfo("孙权"),
                new FigureInfo("关羽"),
                new FigureInfo("张飞"),
                new FigureInfo("赵云"),
                new FigureInfo("诸葛亮"),
                new FigureInfo("司马懿"),
                new FigureInfo("周瑜"),
                new FigureInfo("袁绍"),
                };

        List<String> targetNames = new ArrayList<>();

        for (FigureInfo target : targetFigureList)
        {
            targetNames.add(target.getName());
        }

        TextFileReader reader = new TextFileReader(folderPath);
        reader.readFilesInFolder();
        List<String> fileContents = reader.getFileContents();

//        if (fileContents != null) {
//            Map<String, Map<String, Object>> nameOccurrencesMap = Analyzer.analyzeData(fileContents, targetNames);
//
//            // 打印每个人名在不同章节的出现次数和位置
//            for (String name : targetNames) {
//                System.out.println("Occurrences of '" + name + "': " + nameOccurrencesMap.get(name).get("Occurrences"));
//                System.out.println("Positions of '" + name + "': " + nameOccurrencesMap.get(name).get("Positions"));
//            }
//        } else {
//            System.out.println("No file contents found.");
//        }

//        if (fileContents != null) {
//            TextCount.analyzeData(fileContents ,targetFigureList);
//
//            // 打印每个人名在不同章节的出现次数和位置
//            for (FigureInfo figure : targetFigureList) {
//                System.out.println("Occurrences of '" + figure.getName() + "': " + figure.getOccurrences());
//                System.out.println("Positions of '" + figure.getName() + "': " + figure.getPosition());
//            }
//        } else {
//            System.out.println("No file contents found.");
//        }
    }
}
