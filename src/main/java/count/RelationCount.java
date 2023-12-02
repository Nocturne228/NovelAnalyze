package count;

import figure.FigureInfo;
import figure.FigureListInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RelationCount
{
    public int getCountOfCloseElements(List<Integer> list1, List<Integer> list2, int threshold) {

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

    public List<List<Integer>> getRelationMatrix(List<FigureInfo> targetFigureList, int threshold)
    {
        List<List<Integer>> RelationMatrix = new ArrayList<>();
        for (FigureInfo character : targetFigureList)
        {
            List<Integer> array = new ArrayList<>();
            for (FigureInfo figure : targetFigureList)
            {
                if (character.getName().equals(figure.getName()))   {array.add(0);}
                else
                {
                    array.add(getCountOfCloseElements(figure.getPosition(), character.getPosition(), threshold));
                }
            }
            RelationMatrix.add(array);
        }

        for (int i = 0; i < RelationMatrix.size(); i++) {
            List<Integer> row = RelationMatrix.get(i);
            List<IndexedValue> indexedValues = new ArrayList<>();

            for (int j = 0; j < row.size(); j++) {
                indexedValues.add(new IndexedValue(j, row.get(j)));
            }

            // 根据亲密度从高到低排序
            Collections.sort(indexedValues, Comparator.comparing(IndexedValue::getValue).reversed());

            // 输出排序后的人物序号
            System.out.print("Person " + i + ": ");
            for (IndexedValue indexedValue : indexedValues) {
                System.out.print(indexedValue.getIndex() + " ");
            }
            System.out.println();
        }

        return RelationMatrix;
    }

    public static class IndexedValue {
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


}
