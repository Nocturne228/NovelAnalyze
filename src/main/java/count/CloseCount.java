package count;

import figure.FigureInfo;
import figure.FigureRelation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CloseCount
{
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

    public void setSortedCloseRelationList(List<FigureInfo> targetFigureList, List<FigureRelation> RelationList, int threshold)
    {
        for (FigureInfo character : targetFigureList)
        {
            for (FigureInfo figure : targetFigureList)
            {
                if (figure.getName().equals(character.getName()))
                {
                    continue;
                }
                int count = getCountOfCloseElements(figure.getPosition(), character.getPosition(), threshold);
                FigureRelation fr = new FigureRelation(character.getName());
            }
        }
    }

    public List<Map<String, Integer>> getCloseCountOfFigureMap(List<FigureInfo> targetFigureList, List<FigureRelation> RelationList, int threshold)
    {
        List<Map<String, Integer>> mapList = new ArrayList<>();
        for (FigureInfo character : targetFigureList)
        {
            Map<String, Integer> nameMap = new HashMap<>();
            for (FigureInfo figure : targetFigureList)
            {
                if (figure.getName().equals(character.getName()))
                {
                    continue;
                }
                int count = getCountOfCloseElements(figure.getPosition(), character.getPosition(), threshold);
                nameMap.put(figure.getName(), count);
            }
            mapList.add(nameMap);
            List<Map.Entry<String, Integer>> list = new ArrayList<>(nameMap.entrySet());
            Collections.sort(list, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

            System.out.println(character.getName());
            for (Map.Entry<String, Integer> entry : list)
            {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
        return mapList;
    }

    public void main(String[] args)
    {
        List<FigureInfo> targetFigureList = new ArrayList<>();
        List<String> fileContents = this.getFileContents();


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


//                int index = content.indexOf(figure.getName());
//                while (index != -1)
//                {
//                    figure.addPosition(index);
//                    figure.incOccurrences();
//                    index = content.indexOf(figure.getName(), index + figure.getName().length());
//                }
            }

            Map<String, Map<String, Object>> nameOccurrencesMap = new HashMap<>(); // 记录每个人名的出现次数和位置
            Map<String, Object> nameInfo = new HashMap<>();
            nameInfo.put("Occurrences", figure.getOccurrences());
            nameInfo.put("Positions", figure.getPosition());
            nameOccurrencesMap.put(figure.getName(), nameInfo);
        }
    }

    public List<String> getFileContents()
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

    public List<FigureInfo> initNames()
    {
        List<FigureInfo> targetFigureList = new ArrayList<>();
        targetFigureList.add(new FigureInfo(("曹操")));
        targetFigureList.get(0).setAliasName("操");
        targetFigureList.get(0).setAliasName("孟德");
        targetFigureList.get(0).setAliasName("阿瞒");

        targetFigureList.add(new FigureInfo(("刘备")));
        targetFigureList.get(1).setAliasName("玄德");
        targetFigureList.get(1).setAliasName("皇叔");
        targetFigureList.get(1).setAliasName("使君");

        targetFigureList.add(new FigureInfo(("孙权")));
        targetFigureList.get(2).setAliasName("仲谋");
        targetFigureList.get(2).setAliasName("吴侯");

        targetFigureList.add(new FigureInfo(("关羽")));
        targetFigureList.get(3).setAliasName("云长");
        targetFigureList.get(3).setAliasName("关公");

        targetFigureList.add(new FigureInfo(("诸葛亮")));
        targetFigureList.get(4).setAliasName("孔明");
        targetFigureList.get(4).setAliasName("卧龙");

        targetFigureList.add(new FigureInfo(("郭嘉")));
        targetFigureList.get(5).setAliasName("奉孝");

        targetFigureList.add(new FigureInfo(("周瑜")));
        targetFigureList.get(6).setAliasName("公瑾");
        targetFigureList.get(6).setAliasName("周郎");

        targetFigureList.add(new FigureInfo(("赵云")));
        targetFigureList.get(7).setAliasName("子龙");

        targetFigureList.add(new FigureInfo(("吕布")));
        targetFigureList.get(8).setAliasName("奉先");
        targetFigureList.get(8).setAliasName("温侯");

        targetFigureList.add(new FigureInfo(("董卓")));
        targetFigureList.get(9).setAliasName("仲颖");
        return targetFigureList;
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
