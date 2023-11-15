package figure;
import java.util.ArrayList;
import java.util.List;

public class FigureInfo
{
    private String name;
    private String aliasName1;
    private String aliasName2;
    private String aliasName3;
    private int aliasNameNumber;
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
            System.out.println("alias1 added:");
            System.out.println(aliasName1);
            this.aliasNameNumber = 1;
        }
        else if (aliasName2 == null)
        {
            aliasName2 = aliasName;
            System.out.println("alias2 added:");
            System.out.println(aliasName2);
            this.aliasNameNumber = 2;
        }
        else
        {
            aliasName3 = aliasName;
            System.out.println("alias3 added:");
            System.out.println(aliasName3);
            this.aliasNameNumber = 3;
        }

    }

    public void addPosition(int newPosition)
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

    public int getAliasNameNumber()
    {
        return aliasNameNumber;
    }

    public void refreshData()
    {
        this.setOccurrences(0);
        this.clearPosition();
    }

}
