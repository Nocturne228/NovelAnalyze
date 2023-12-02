package figure;


import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FigureRelation
{
    private List<FigureInfo> targetFigureList;
    private String figureName;
    private String figure1;
    private String figure2;
    private String figure3;
    private String figure4;
    private String figure5;
    private String figure6;
    private String figure7;
    private String figure8;
    private String figure9;
    private List<String> nameList;

    public FigureRelation(String figureName)
    {
        this.figureName = figureName;
        nameList = new ArrayList<>();
    }
    public void addName(String name)
    {
        nameList.add(name);
    }

    public void setFigrues()
    {
        setFigure1(nameList.get(0));
        setFigure2(nameList.get(1));
        setFigure3(nameList.get(2));
        setFigure4(nameList.get(3));
        setFigure5(nameList.get(4));
        setFigure6(nameList.get(5));
        setFigure7(nameList.get(6));
        setFigure8(nameList.get(7));
        setFigure9(nameList.get(8));
    }

    public String getFigure1()
    {
        return figure1;
    }

    public String getFigure2()
    {
        return figure2;
    }

    public String getFigure3()
    {
        return figure3;
    }

    public String getFigure4()
    {
        return figure4;
    }

    public String getFigure5()
    {
        return figure5;
    }

    public String getFigure6()
    {
        return figure6;
    }

    public String getFigure7()
    {
        return figure7;
    }

    public String getFigure8()
    {
        return figure8;
    }

    public String getFigure9()
    {
        return figure9;
    }

    public String getFigureName()
    {
        return figureName;
    }

    public void setFigure1(String figure1)
    {
        this.figure1 = figure1;
    }

    public void setFigure2(String figure2)
    {
        this.figure2 = figure2;
    }

    public void setFigure3(String figure3)
    {
        this.figure3 = figure3;
    }

    public void setFigure4(String figure4)
    {
        this.figure4 = figure4;
    }

    public void setFigure5(String figure5)
    {
        this.figure5 = figure5;
    }

    public void setFigure6(String figure6)
    {
        this.figure6 = figure6;
    }

    public void setFigure7(String figure7)
    {
        this.figure7 = figure7;
    }

    public void setFigure8(String figure8)
    {
        this.figure8 = figure8;
    }

    public void setFigure9(String figure9)
    {
        this.figure9 = figure9;
    }
}
