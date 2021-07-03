package chess;

public class Spot {
    
    private int x;
    private int y;
    private char xLabel;
    private int yLabel;
    public Spot(int x, int y){
        this.setX(x);
        this.setY(y);
        this.xLabel = (char)(this.x + 'a');
        this.yLabel = 8 - this.y;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }

    public char getXLabel() {
        return xLabel;
    }

    public void setXLabel(char xLabel) {
        this.xLabel = xLabel;
    }

    public int getYLabel() {
        return yLabel;
    }

    public void setYLabel(int yLabel) {
        this.yLabel = yLabel;
    }


}
