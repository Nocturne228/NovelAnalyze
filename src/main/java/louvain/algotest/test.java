package louvain.algotest;

import figure.ExampleFigureList;
import figure.FigureListInfo;
import fileio.CallPythonScript;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test
{
    public static void main(String[] args)
    {
        int[] result = ApplyLouvain.pythonGreedyModularityCommunityAlgo();
        int[] answer = ApplyLouvain.Louvain();

        System.out.println("\n这是python的networx库");
        printNameByGroup(result);
        System.out.println("这是Java的Louvain实现");
        printNameByGroup(answer);

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

    public static void printNameByGroup(int[] result)
    {
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

            System.out.println("Group " + key + ": " + name);
        }
    }

}
