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
        Piece testPiece;
        int i;
    // Remember: a king can move one square up, right, left, or down, or
    // diagonally, but he can never put himself in danger of an oposing 
    // piece attacking him on the next turn. He cannot attack his own pieces.
        
    //check if a same-color piece is on the destination

    if(Math.abs( destination_x-this.getX()) > 1 || Math.abs( destination_y-this.getY())  > 1 ){
        return false;
    }else{
        testPiece = board.getPiece(destination_x, destination_y);
        if(testPiece!=null){
            if(testPiece.isWhite()&&this.isWhite()){
                return false;
            }else if(testPiece.isBlack()&&this.isBlack()){
                return false;
            }
        }
    }
  
    return true;
    }
    public boolean canCastle(int destination_x, int destination_y){
        
        int i;
        Spot move;
        
        if(destination_x-this.getX()>0){
           for( i=this.getX();i<destination_x;i++){
              if(this.isWhite()){
                  move = new Spot(i,destination_y);
                  for(Piece piece: board.Black_Pieces){
                    if(piece.availableMoves().contains(move)){
                        return false;
                    }
                  }
              }
           }
        } 
        return true;
    }
    
}
