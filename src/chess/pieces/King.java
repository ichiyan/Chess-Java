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
    

    if(Math.abs( destination_x-this.getX()) > 1 || Math.abs( destination_y-this.getY())  > 1 ){
        
        if(Math.abs(destination_x-this.getX())==2 && destination_y==this.getY()){
            if(!this.has_moved){
                return canCastle();
            }
        }else{
            return false;
        }
        
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
    public boolean canCastle(){
        
        int i;
        for(i=this.getX();i<=this.getX()+2 && willHit(i, this.getY());i++){
            if(board.getPiece(i, this.getY())!=null){
                return false;
            }
        }
       //can castle queen side or king side --not yet working
        
              
                     
        return true;
    }
    public boolean willHit(int x, int y){
        Spot move;
        if(this.isWhite()){
            move = new Spot(x,y);
            for(Piece piece: board.Black_Pieces){
                for(Spot spot: piece.availableMoves()){
                    if(move.getX() == spot.getX() && move.getY() == spot.getY()){
                        System.out.println("true");
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
