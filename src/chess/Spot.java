package chess;

public class Spot {
    
    private int x;
    private int y;
    public Spot(int x, int y){
        this.setX(x);
        this.setY(y);
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
    
}
