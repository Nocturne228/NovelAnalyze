package figure;

import fileio.CSVWriter;

import java.util.*;

public class FigureListInfo
{
    private List<FigureInfo> targetFigureList;

    private List<List<Integer>> RelationMatrix;
    private int threshold = 1000;
    private int listLength;


    public FigureListInfo(List<FigureInfo> targetFigureList)
    {
        this.targetFigureList = targetFigureList;
        this.RelationMatrix = new ArrayList<>();
        getRelationMatrix();
        listLength = targetFigureList.size();
    }

    public void setTargetFigureList(List<FigureInfo> targetFigureList)
    {
        getRelationMatrix();
        listLength = targetFigureList.size();
        this.targetFigureList = targetFigureList;
    }

    public int getListLength()
    {
        return listLength;
    }

    public Map<Integer, String> getLabelToNameMap()
    {
        Map<Integer, String> labelToNameMap = new HashMap<>();
        for (FigureInfo figure : targetFigureList) {
            labelToNameMap.put(figure.getLabel(), figure.getName());
        }
        return labelToNameMap;
    }

    public int getCountOfCloseElements(List<Integer> list1, List<Integer> list2, int threshold)
    {

        int count = 0;
        int pointer1 = 0;
        int pointer2 = 0;

        while (pointer1 < list1.size() && pointer2 < list2.size())
        {
            int diff = Math.abs(list1.get(pointer1) - list2.get(pointer2));
            if (diff <= threshold)
            {
                count++;
                pointer1++;
                pointer2++;
            }
            else if (list1.get(pointer1) < list2.get(pointer2))
            {
                pointer1++;
            }
            else
            {
                pointer2++;
            }
        }

        return count;
    }

    public int[][] getExampleDataMatix()
    {
        int[][] closeCount = new int[getListLength()][getListLength()];
        for (FigureInfo character : targetFigureList)
        {
            for (FigureInfo figure : targetFigureList)
            {
                if (character.getName().equals(figure.getName()))
                {
                    continue;
                }

                int count = getCountOfCloseElements(character.getPosition(), figure.getPosition(), threshold);
                closeCount[character.getLabel()][figure.getLabel()] = count;
            }
        }

        String csvFilePath = "/Users/nocturne/Downloads/Project/Java/NovelAnalyze/src/main/resources/matrix.csv";
        CSVWriter.writeMatrixCSV(csvFilePath, closeCount);
        return closeCount;
    }

    public List<List<Integer>> getRelationMatrix()
    {

        for (FigureInfo character : targetFigureList)
        {
            List<Integer> array = new ArrayList<>();
            for (FigureInfo figure : targetFigureList)
            {
                if (character.getName().equals(figure.getName()))
                {
                    array.add(0);
                }
                else
                {
                    array.add(getCountOfCloseElements(figure.getPosition(), character.getPosition(), threshold));
                }
            }
            RelationMatrix.add(array);
        }
        return RelationMatrix;
    }

    public int[][] getCorrespondenceRelationDataTable() {
        int numRows = listLength;
        int numCols = listLength;

        int[][] RelationTable = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = RelationMatrix.get(i);
            List<IndexedValue> indexedValues = new ArrayList<>();

            for (int j = 0; j < row.size(); j++) {
                indexedValues.add(new IndexedValue(j, row.get(j)));
            }

            // 对关系值进行降序排序
            Collections.sort(indexedValues, Comparator.comparing(IndexedValue::getValue).reversed());

            Map<Integer, String> labelToNameMap = getLabelToNameMap();
            RelationTable[i][0] = i;

            // 将人名按关系值降序排列
            int n = 1;
            for (IndexedValue indexedValue : indexedValues) {
                if (n >= numCols) { break; }
                RelationTable[i][n] = indexedValue.getValue();
                n++;
            }
        }
        return RelationTable;
    }

    public String[][] getRelationTable() {
        int numRows = listLength;
        int numCols = listLength;

        String[][] RelationTable = new String[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = RelationMatrix.get(i);
            List<IndexedValue> indexedValues = new ArrayList<>();

            for (int j = 0; j < row.size(); j++) {
                indexedValues.add(new IndexedValue(j, row.get(j)));
            }

            // 对关系值进行降序排序
            Collections.sort(indexedValues, Comparator.comparing(IndexedValue::getValue).reversed());

            Map<Integer, String> labelToNameMap = getLabelToNameMap();
            RelationTable[i][0] = labelToNameMap.get(i);

            // 将人名按关系值降序排列
            int n = 1;
            for (IndexedValue indexedValue : indexedValues) {
                if (n >= numCols) { break; }
                RelationTable[i][n] = labelToNameMap.get(indexedValue.getIndex());
                n++;
            }
        }
        return RelationTable;
    }




    public void printMatrix(List<List<Integer>> matrix)
    {
        for (List<Integer> row : matrix)
        {
            for (Integer integer : row)
            {
                System.out.print(integer + " ");
            }
            System.out.println();
        }
    }

    public void printMatrix(String[][] matrix) {
        for (int i = 0; i < listLength; i++) {
            for (int j = 0; j < listLength; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }



        public String[] getRelationName()
    {
        String[] columnName = new String[listLength];
        columnName[0] = "姓名";
        for (int i = 1; i < listLength; i++)
        {
            columnName[i] = "亲密" + i;
        }
        return columnName;
    }




    private static class IndexedValue {
        private final int index;
        private final int value;

        public IndexedValue(int index, int value) {
            this.index = index;
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public int getValue() {
            return value;
        }
    }

    @Override
    public String toString()
    {
        for (FigureInfo figure : targetFigureList)
        {
            System.out.print(figure.getName());
        }
        System.out.println();
        return "";
    }
}
