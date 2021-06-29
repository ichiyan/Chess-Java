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
    

    if(Math.abs(destination_x-this.getX()) > 1 || Math.abs(destination_y-this.getY())  > 1 ){
        
        if(destination_x-this.getX()==2 && destination_y==this.getY()){

            if(this.has_moved){
                return false;
            }else{
                return canCastleQueenSide();
            }
        }else if(destination_x-this.getX()==-2 && destination_y==this.getY()){

            if(this.has_moved){
                return false;
            }else{
                return canCastleKingSide();
            }
        }
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
    public boolean canCastleQueenSide(){
        
        for(int i = this.getX();i<=this.getX()+2;i++){
            if(willHit(i, this.getY())){
                return false;
            }
        }
        return true;
    }
    public boolean canCastleKingSide(){
        for(int i = this.getX();i>=this.getX()-2;i--){
            if(willHit(i, this.getY())){
                return false;
            }
        }
        return true;
        
        
    }
    public boolean willHit(int x, int y){

        
        if(this.isWhite()){
            for(Piece piece: board.Black_Pieces){
                for(Spot move: piece.availableMoves()){
                    if(move.getX() == x && move.getY() == y){
                        return true;
                    }
                }
            }
        }
        else if(this.isBlack()){
            for(Piece piece: board.White_Pieces){
                for(Spot move: piece.availableMoves()){
                    if(move.getX() == x && move.getY() == y){
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
