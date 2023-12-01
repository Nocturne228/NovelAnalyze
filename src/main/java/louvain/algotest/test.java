package louvain.algotest;

import count.CountTest;
import count.RelationCount;
import fileio.CSVWriter;
import louvain.algorithm.CommunityInfo;
import louvain.algorithm.LouvainCalculator;
import louvain.entity.Graph;
import louvain.entity.Link;
import fileio.CallPythonScript;

import java.util.Arrays;
import java.util.List;

public class test
{
    public static void main(String[] args)
    {
        Graph g = new Graph();
//        // 0->1->2->0
//        g.addLinks(Arrays.asList(new Link(0, 1, 1.0)));
//        g.addLinks(Arrays.asList(new Link(1, 2, 1.0)));
//        g.addLinks(Arrays.asList(new Link(2, 0, 1.0)));
//        // 3->4->5->3
//        g.addLinks(Arrays.asList(new Link(3, 4, 1.0)));
//        g.addLinks(Arrays.asList(new Link(4, 5, 1.0)));
//        g.addLinks(Arrays.asList(new Link(5, 3, 1.0)));
//        // 构造计算器
//        LouvainCalculator louvainCalculator = new LouvainCalculator(g);
//        // 执行划分
//        CommunityInfo communityInfo = louvainCalculator.findCommunitiesSingleLevel();
//        // 输出结果
//        System.out.println(communityInfo);
        CountTest closeCount = new CountTest();
        int[][] adjacencyMatrix = closeCount.getCloseDataMatrix();
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
//                if (i == j) {continue;}
//                System.out.print(adjacencyMatrix[i][j] + " ");
                g.addLinks(Arrays.asList(new Link(i, j, adjacencyMatrix[i][j])));
            }
//            System.out.println();
        }
        LouvainCalculator louvainCalculator = new LouvainCalculator(g);
        louvainCalculator.setStickingK(0.65);
        CommunityInfo communityInfo = louvainCalculator.findCommunitiesSingleLevel();

        System.out.println(communityInfo);

        RelationCount relationCount = new RelationCount();

        String csvMatrixFilePath = "/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/resources/matrix.csv";
        String csvDataFilePath = "/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/resources/data.csv";
        List<List<Integer>> relationMatrix = relationCount.getRelationMatrix(CountTest.targetFigureList, 50);
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeMatrixCSV(csvMatrixFilePath, relationMatrix);
        relationCount.printMatrix(relationMatrix);


        csvWriter.writeDataCSV(csvDataFilePath, CountTest.targetFigureList);

        CallPythonScript callPythonScript = new CallPythonScript();
        String pythonCommunityScriptPath = "/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/java/fileio/GreedyModularityCommunity.py";
        callPythonScript.callPython(pythonCommunityScriptPath);
        String pythonPlotScriptPath = "/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/java/fileio/DrawScatterPlot.py";
        callPythonScript.callPython(pythonPlotScriptPath);

    }
}
