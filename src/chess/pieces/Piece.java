package chess.pieces;

import java.util.ArrayList;

import chess.Board;

public abstract class Piece {
    private int x;
    private int y;
    final private boolean is_white;
    private String file_path;
    public Board board;
    public ArrayList<Spot> moves;
    public Piece(int x, int y, boolean is_white, String file_path, Board board)
    {
        this.is_white = is_white;
        this.x = x;
        this.y = y;
        this.file_path = file_path;
        this.board = board;
        moves = new ArrayList<Spot>();
        moves.clear();
    }
    
    public String getFilePath()
    {
        return file_path;
    }
    
    public void setFilePath(String path)
    {
        this.file_path = path;
    }
    
    public boolean isWhite()
    {
        return is_white;
    }
    
    public boolean isBlack()
    {
        return !is_white;
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
    
    public abstract boolean canMove(int destination_x, int destination_y);

    public ArrayList<Spot> availableMoves(){
        this.moves.clear();
        int x;
        int y;
        for(y=0;y<8;y++){
            for(x=0;x<8;x++){
                if(canMove(x, y)){
                    if(!this.moves.contains(new Spot(x,y))){
                        this.moves.add(new Spot(x,y));
                    }
                }
            }
        }
        return this.moves;
    }
    
}

