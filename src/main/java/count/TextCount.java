package count;

import figure.FigureInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextCount
{
    public static void analyzeData(List<String> fileContents, List<FigureInfo>  targetFigureList)
    {

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

            Map<String, Map<String, Object>> nameOccurrencesMap = new HashMap<>(); // 记录每个人名的出现次数和位置
            Map<String, Object> nameInfo = new HashMap<>();
            nameInfo.put("Occurrences", figure.getOccurrences());
            nameInfo.put("Positions", figure.getPosition());
            nameOccurrencesMap.put(figure.getName(), nameInfo);
        }
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
