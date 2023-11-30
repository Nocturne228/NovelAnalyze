package figure;


import javafx.scene.control.TableView;

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

    public FigureRelation(String figureName)
    {
        this.figureName = figureName;
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
