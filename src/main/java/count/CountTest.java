package count;

import figure.FigureInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountTest
{
    public static List<FigureInfo> targetFigureList = new ArrayList<>();
    public CountTest()
    {
        initNames();
    }

    public static List<FigureInfo> getTargetFigureList()
    {
        return targetFigureList;
    }

    public static int getCountOfCloseElements(List<Integer> list1, List<Integer> list2, int threshold) {

        int count = 0;
        int pointer1 = 0;
        int pointer2 = 0;

        while (pointer1 < list1.size() && pointer2 < list2.size()) {
            int diff = Math.abs(list1.get(pointer1) - list2.get(pointer2));
            if (diff <= threshold) {
                count++;
                pointer1++;
                pointer2++;
            } else if (list1.get(pointer1) < list2.get(pointer2)) {
                pointer1++;
            } else {
                pointer2++;
            }
        }

        return count;
    }
    public static int[][] closeDataMatrix;

    public int[][] getCloseDataMatrix()
    {
        closeDataMatrix = new int[10][10];
        List<String> fileContents = getFileContents();


        for (FigureInfo figure : targetFigureList)
        {
            for (int i = 0; i < fileContents.size(); i++)
            {
                String content = fileContents.get(i);

                String regex = buildRegex(figure);

                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(content);

                while (matcher.find())
                {
                    int index = matcher.start();
                    figure.addPosition(index);
                    figure.incOccurrences();
                }
            }
        }


        for (FigureInfo character : targetFigureList)
        {
            for (FigureInfo figure : targetFigureList)
            {
                if (character.getName().equals(figure.getName()))
                {
                    continue;
                }

                int count = getCountOfCloseElements(character.getPosition(), figure.getPosition(), 500);
                closeDataMatrix[character.getLabel()][figure.getLabel()] = count;
            }
        }

        return closeDataMatrix;
    }

//    public static void main(String[] args)
//    {
//        List<String> fileContents = getFileContents();
//
//        initNames();
//
//        for (FigureInfo figure : targetFigureList)
//        {
//            for (int i = 0; i < fileContents.size(); i++)
//            {
//                String content = fileContents.get(i);
//
//                String regex = buildRegex(figure);
//
//                Pattern pattern = Pattern.compile(regex);
//                Matcher matcher = pattern.matcher(content);
//
//                while (matcher.find())
//                {
//                    int index = matcher.start();
//                    figure.addPosition(index);
//                    figure.incOccurrences();
//                }
//            }
//        }
//
//        int[][] closeCount = new int[10][10];
//
//        for (FigureInfo character : targetFigureList)
//        {
//            for (FigureInfo figure : targetFigureList)
//            {
//                if (character.getName().equals(figure.getName()))
//                {
//                    continue;
//                }
//
//                int count = getCountOfCloseElements(character.getPosition(), figure.getPosition(), 50);
//                closeCount[character.getLabel()][figure.getLabel()] = count;
//            }
//        }
//
//    }

    public static List<String> getFileContents()
    {
        String folderPath = "/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/resources";
        List<String> fileContents = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null)
        {
            for (File file : files)
            {
                if (file.isFile() && file.getName().endsWith(".txt"))
                {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file)))
                    {
                        StringBuilder content = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null)
                        {
                            content.append(line).append("\n");
                        }
                        fileContents.add(content.toString());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        return fileContents;
    }

    public static void initNames()
    {
        targetFigureList.add(new FigureInfo(("曹操")));
        targetFigureList.get(0).setAliasName("操");
        targetFigureList.get(0).setAliasName("孟德");
        targetFigureList.get(0).setAliasName("阿瞒");
        targetFigureList.get(0).setLabel(0);

        targetFigureList.add(new FigureInfo(("刘备")));
        targetFigureList.get(1).setAliasName("玄德");
        targetFigureList.get(1).setAliasName("皇叔");
        targetFigureList.get(1).setAliasName("使君");
        targetFigureList.get(1).setLabel(1);

        targetFigureList.add(new FigureInfo(("孙权")));
        targetFigureList.get(2).setAliasName("仲谋");
        targetFigureList.get(2).setAliasName("吴侯");
        targetFigureList.get(2).setLabel(2);

        targetFigureList.add(new FigureInfo(("关羽")));
        targetFigureList.get(3).setAliasName("云长");
        targetFigureList.get(3).setAliasName("关公");
        targetFigureList.get(3).setLabel(3);

        targetFigureList.add(new FigureInfo(("诸葛亮")));
        targetFigureList.get(4).setAliasName("孔明");
        targetFigureList.get(4).setAliasName("卧龙");
        targetFigureList.get(4).setLabel(4);

        targetFigureList.add(new FigureInfo(("郭嘉")));
        targetFigureList.get(5).setAliasName("奉孝");
        targetFigureList.get(5).setLabel(5);

        targetFigureList.add(new FigureInfo(("周瑜")));
        targetFigureList.get(6).setAliasName("公瑾");
        targetFigureList.get(6).setAliasName("周郎");
        targetFigureList.get(6).setLabel(6);

        targetFigureList.add(new FigureInfo(("赵云")));
        targetFigureList.get(7).setAliasName("子龙");
        targetFigureList.get(7).setLabel(7);

        targetFigureList.add(new FigureInfo(("鲁肃")));
        targetFigureList.get(8).setAliasName("子敬");
        targetFigureList.get(8).setLabel(8);

        targetFigureList.add(new FigureInfo(("张辽")));
        targetFigureList.get(9).setAliasName("文远");
        targetFigureList.get(9).setLabel(9);
    }

    public static String buildRegex(FigureInfo figure)
    {
        StringBuilder regexBuilder = new StringBuilder();
        regexBuilder.append(Pattern.quote(figure.getName()));

        if (figure.getAliasName1() != null) {
            regexBuilder.append("|").append(Pattern.quote(figure.getAliasName1()));
        }
        if (figure.getAliasName2() != null) {
            regexBuilder.append("|").append(Pattern.quote(figure.getAliasName2()));
        }
        if (figure.getAliasName3() != null) {
            regexBuilder.append("|").append(Pattern.quote(figure.getAliasName3()));
        }

        return regexBuilder.toString();
    }
}
