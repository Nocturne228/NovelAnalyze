package louvain.algotest;

import count.CountTest;
import louvain.entity.Graph;
import louvain.entity.Link;

import java.util.Arrays;

public class ApplyLouvain
{
    public void Louvain()
    {
        Graph g = new Graph();
        CountTest closeCount = new CountTest();
        int[][] adjancencyMatrix = closeCount.getCloseDataMatrix();
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if (i == j)     {continue;}
                g.addLinks(Arrays.asList(new Link(i, j, adjancencyMatrix[i][j])));
            }
        }
    }
}
