package louvain.algotest;

import figure.ExampleFigureList;
import figure.FigureInfo;
import figure.FigureListInfo;
import fileio.CallPythonScript;
import louvain.algorithm.CommunityInfo;
import louvain.algorithm.CommunityCalculator;
import louvain.entity.Graph;
import louvain.entity.Link;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplyAlgorithm
{
    public static int[] Louvain()
    {
        Graph g = new Graph();
        List<FigureInfo> targetFigureList = ExampleFigureList.getTargetFigureList();
        FigureListInfo figureListInfo = new FigureListInfo(targetFigureList);
        int length = targetFigureList.size();
        int[][] adjancencyMatrix = figureListInfo.getExampleDataMatix();
        for (int i = 0; i < length; i++)
        {
            for (int j = 0; j < length; j++)
            {
                if (i == j)     {continue;}
                g.addLinks(Arrays.asList(new Link(i, j, adjancencyMatrix[i][j])));
            }
        }

        CommunityCalculator louvainCalculator = new CommunityCalculator(g);
        louvainCalculator.setStickingK(0.60);
        CommunityInfo communityInfo = louvainCalculator.findCommunitiesSingleLevel();
        System.out.println(communityInfo);


        int[] result = communityInfo.getNodeCommunityNo();
        return result;
    }

    public static int[] pythonGreedyModularityCommunityAlgo()
    {

        CallPythonScript.callPython("/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/java/fileio/GreedyModularityCommunity.py");
        String pythonOutput = captureConsoleOutput();

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(pythonOutput);

        List<Integer> integerList = new ArrayList<>();
        while (matcher.find()) {
            int number = Integer.parseInt(matcher.group());
            integerList.add(number);
        }

        int[] integerArray = new int[integerList.size()];
        for (int i = 0; i < integerList.size(); i++) {
            integerArray[i] = integerList.get(i);
        }

        return integerArray;

    }

    public static List<String> printNameByGroup(int[] result)
    {
        List<String> resultGroupList = new ArrayList<>();
        FigureListInfo figureListInfo = new FigureListInfo(ExampleFigureList.getTargetFigureList());
        Map<Integer, String> nameMap = figureListInfo.getLabelToNameMap();
        Map<Integer, List<Integer>> groupedValues = groupByValues(result);

        // 输出每个分组的元素
        for (Map.Entry<Integer, List<Integer>> entry : groupedValues.entrySet()) {
            int key = entry.getKey();
            List<Integer> group = entry.getValue();
            List<String> name = new ArrayList<>();
            for (Integer integer : group)
            {
                name.add(nameMap.get(integer));
            }

            String string = "Group" + key + ":" + name;
            resultGroupList.add(string);
            System.out.println("Group " + key + ": " + name);
        }
        return resultGroupList;
    }
    private static Map<Integer, List<Integer>> groupByValues(int[] values) {
        Map<Integer, List<Integer>> groupedValues = new HashMap<>();

        for (int i = 0; i < values.length; i++) {
            int value = values[i];

            // 如果分组不存在，则创建新的分组
            groupedValues.putIfAbsent(value, new ArrayList<>());

            // 将当前值添加到相应的分组
            groupedValues.get(value).add(i);
        }

        return groupedValues;
    }

    public static String captureConsoleOutput()
    {
        // 保存原始的标准输出流
        PrintStream originalOut = System.out;

        // 创建一个字节数组输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String consoleOutput;

        try {
            // 将标准输出流重定向到自定义输出流
            PrintStream ps = new PrintStream(baos);
            System.setOut(ps);

            // 执行一些输出到控制台的操作

            CallPythonScript callPythonScript = new CallPythonScript();
            callPythonScript.callPython("/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/java/fileio/GreedyModularityCommunity.py");

            // 将自定义输出流中的内容转换成字符串
            consoleOutput = baos.toString();

            // 打印捕获到的控制台输出
            System.out.println("Captured Console Output:\n" + consoleOutput);
        } finally {
            // 在finally块中确保恢复原始的标准输出流
            System.setOut(originalOut);
        }

        // 执行一些其他操作，现在标准输出流已经恢复到原来的状态
        System.out.println("Back to the original standard output.");
        return consoleOutput;
    }
}
