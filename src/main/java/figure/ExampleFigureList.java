package figure;

import java.util.ArrayList;
import java.util.List;

public class ExampleFigureList
{
    private static List<FigureInfo> targetFigureList;

    public static List<FigureInfo> getTargetFigureList()
    {
        targetFigureList = new ArrayList<>();
        targetFigureList.add(new FigureInfo(("曹操")));
        targetFigureList.get(0).setAliasName("操");
        targetFigureList.get(0).setAliasName("孟德");
        targetFigureList.get(0).setAliasName("阿瞒");
        targetFigureList.get(0).setLabel(0);

        targetFigureList.add(new FigureInfo(("刘备")));
        targetFigureList.get(1).setAliasName("玄德");
        targetFigureList.get(1).setAliasName("皇叔");
        targetFigureList.get(1).setAliasName("使君");
        targetFigureList.get(1).setLabel(1);

        targetFigureList.add(new FigureInfo(("孙权")));
        targetFigureList.get(2).setAliasName("仲谋");
        targetFigureList.get(2).setAliasName("吴侯");
        targetFigureList.get(2).setLabel(2);

        targetFigureList.add(new FigureInfo(("关羽")));
        targetFigureList.get(3).setAliasName("云长");
        targetFigureList.get(3).setAliasName("关公");
        targetFigureList.get(3).setLabel(3);

        targetFigureList.add(new FigureInfo(("诸葛亮")));
        targetFigureList.get(4).setAliasName("孔明");
        targetFigureList.get(4).setAliasName("卧龙");
        targetFigureList.get(4).setLabel(4);

        targetFigureList.add(new FigureInfo(("郭嘉")));
        targetFigureList.get(5).setAliasName("奉孝");
        targetFigureList.get(5).setLabel(5);

        targetFigureList.add(new FigureInfo(("周瑜")));
        targetFigureList.get(6).setAliasName("公瑾");
        targetFigureList.get(6).setAliasName("周郎");
        targetFigureList.get(6).setLabel(6);

        targetFigureList.add(new FigureInfo(("赵云")));
        targetFigureList.get(7).setAliasName("子龙");
        targetFigureList.get(7).setLabel(7);

        targetFigureList.add(new FigureInfo(("鲁肃")));
        targetFigureList.get(8).setAliasName("子敬");
        targetFigureList.get(8).setLabel(8);

        targetFigureList.add(new FigureInfo(("张辽")));
        targetFigureList.get(9).setAliasName("文远");
        targetFigureList.get(9).setLabel(9);

        return targetFigureList;
    }
}
