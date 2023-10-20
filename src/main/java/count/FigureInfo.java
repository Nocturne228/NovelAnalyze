package count;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FigureInfo
{
    private String name;
    private List<String> aliasName;
    private List<Integer> position;
    private int occurrences;

    public FigureInfo(String name)
    {
        this.name = name;
        this.position = new ArrayList<Integer>();
        this.occurrences = 0;
    }

    public void addAliasName(String alias)
    {
        this.aliasName.add(alias);
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
        return aliasName;
    }

    public List<Integer> getPosition()
    {
        return position;
    }

    public int getOccurrences()
    {
        return occurrences;
    }
}
