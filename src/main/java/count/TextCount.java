package count;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextCount
{
    public static void analyzeData(List<String> fileContents,List<FigureInfo>  targetFigureList)
    {

        for (FigureInfo figure : targetFigureList)
        {
            for (int i = 0; i < fileContents.size(); i++)
            {
                String content = fileContents.get(i);
                int index = content.indexOf(figure.getName());
                while (index != -1)
                {
                    figure.addPostion(index);
                    figure.incOccurrences();
                    index = content.indexOf(figure.getName(), index + figure.getName().length());
                }
            }

            Map<String, Map<String, Object>> nameOccurrencesMap = new HashMap<>(); // 记录每个人名的出现次数和位置
            Map<String, Object> nameInfo = new HashMap<>();
            nameInfo.put("Occurrences", figure.getOccurrences());
            nameInfo.put("Positions", figure.getPosition());
            nameOccurrencesMap.put(figure.getName(), nameInfo);
        }
    }
}
