package count;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analyzer {
    public static Map<String, Map<String, Object>> analyzeData(List<String> fileContents, List<String> targetNames) {
        Map<String, Map<String, Object>> nameOccurrencesMap = new HashMap<>(); // 记录每个人名的出现次数和位置

        for (String name : targetNames) {
            Map<String, Object> info = new HashMap<>();
            info.put("Occurrences", 0);
            info.put("Positions", new ArrayList<Integer>());
            nameOccurrencesMap.put(name, info);
        }

        for (int i = 0; i < fileContents.size(); i++) {
            String content = fileContents.get(i);

            for (String name : targetNames) {
                List<Integer> namePositions = (List<Integer>) nameOccurrencesMap.get(name).get("Positions");
                int index = content.indexOf(name);
                while (index != -1) {
                    namePositions.add(index);
                    index = content.indexOf(name, index + name.length());
                }

                int nameOccurrences = namePositions.size();
                nameOccurrencesMap.get(name).put("Occurrences", nameOccurrences);
            }
        }

        return nameOccurrencesMap;
    }
}
