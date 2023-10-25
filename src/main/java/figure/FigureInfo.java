package figure;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FigureInfo
{
    private String name;
    private String aliasName1;
    private String aliasName2;
    private String aliasName3;
    private List<Integer> position;
    private List<String> aliasNameList;
    private int occurrences;

    public FigureInfo(String name)
    {
        this.name = name;
        this.position = new ArrayList<Integer>();
        this.occurrences = 0;
        List<String> aliasNameList = new ArrayList<>();
        aliasNameList.add(aliasName1);
        aliasNameList.add(aliasName2);
        aliasNameList.add(aliasName3);
    }

    public void setAliasName(String aliasName)
    {
        if (aliasName1 == null)
        {
            aliasName1 = aliasName;
        }
        else if (aliasName2 == null)
        {
            aliasName2 = aliasName;
        }
        else
        {
            aliasName3 = aliasName;
        }

    }

    public void addPostion(int newPosition)
    {
        this.position.add(newPosition);
    }

    public void incOccurrences()
    {
        this.occurrences++;
    }

    public String getName()
    {
        return name;
    }

    public List<String> getAliasName()
    {
        return aliasNameList;
    }

    public String getAliasName1()
    {
        return aliasName1;
    }

    public String getAliasName2()
    {
        return aliasName2;
    }

    public String getAliasName3()
    {
        return aliasName3;
    }

    public List<Integer> getPosition()
    {
        return position;
    }

    public int getOccurrences()
    {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    public void clearPosition() {
        this.position.clear();
    }

    public void refreshData()
    {
        this.setOccurrences(0);
        this.clearPosition();
    }

    public void clearName()
    {
        this.name = "";
    }
}
