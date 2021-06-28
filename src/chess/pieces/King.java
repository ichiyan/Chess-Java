package chess.pieces;

import chess.Board;

public class King extends Piece {

    private boolean has_moved;

    public King(int x, int y, boolean is_white, String file_path, Board board)
    {
        super(x,y,is_white,file_path, board);
        has_moved = false;

    }
    public void setHasMoved(boolean has_moved)
    {
        this.has_moved = has_moved;
    }
    
    public boolean getHasMoved()
    {
        return has_moved;
    }
    @Override
    public boolean canMove(int destination_x, int destination_y)
    {
        Piece testPiece = board.getPiece(destination_x, destination_y);
        int i;
        if(testPiece!=null){
            if(testPiece.isWhite()&&this.isWhite()){
                return false;
            }else if(testPiece.isBlack()&&this.isBlack()){
                return false;
            }
        }

        if(destination_y==this.getY()&&destination_x-this.getX()==Math.abs(2)){
               if(destination_x-this.getX()>0){
                   for(i=this.getX()+1;i<destination_x;i++){
                       
                   }
               }      
        }
        
        
        return true;
    }
}
