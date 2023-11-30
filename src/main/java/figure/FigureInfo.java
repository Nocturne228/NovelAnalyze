package figure;
import java.text.DecimalFormat;
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
    private int occurrences;
    private int start;
    private int end;
    private int span;

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
            this.aliasNameNumber = 1;
        }
        else if (aliasName2 == null)
        {
            aliasName2 = aliasName;
            this.aliasNameNumber = 2;
        }
        else
        {
            aliasName3 = aliasName;
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

    public void refreshData()
    {
        this.setOccurrences(0);
        this.clearPosition();
    }

    public int getStart()
    {
        start = this.position.get(0);
        return start;
    }

    public int getEnd()
    {
        end = this.position.get(this.position.size() - 1);
        return end;
    }

    public int getSpan()
    {
        this.span = end - start;
        return span;
    }

    public double spanPercentage(int totalCharacterCount)
    {
        double percentage = (double) this.span / totalCharacterCount;
        return percentage;
    }

    public String calcSpanPercent(int totalCharacterCount)
    {
        double percentage = (double) this.span / totalCharacterCount;
        DecimalFormat df = new DecimalFormat("0.00");
        String formattedPercentage = df.format(percentage);
        return (formattedPercentage + "%");
    }
}
