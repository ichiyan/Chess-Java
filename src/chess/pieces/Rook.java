package chess.pieces;

import chess.Board;


public class Rook extends Piece {

    private boolean has_moved;
    private boolean isFirstMove;

    public Rook(int x, int y, boolean is_white, String file_path, Board board)
    {
        super(x,y,is_white,file_path, board);
        has_moved = false;
        isFirstMove = false;
    }
    public void setHasMoved(boolean has_moved)
    {
        this.has_moved = has_moved;
    }
    public boolean getHasMoved(){return has_moved;}

    public boolean getIsFirstMove() {
        return isFirstMove;
    }

    public void setIsFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    @Override
    public boolean canMove(int destination_x, int destination_y)
    {
       
        //checks if a same-color piece is on the destination 
        
        Piece testPiece = board.getPiece(destination_x, destination_y);
        if(testPiece!=null){
            if(testPiece.isWhite()&&this.isWhite()){
                return false;
            }else if(testPiece.isBlack()&&this.isBlack()){
                return false;
            }
        }
        //checks if the piece is moving on a straight line
        if(this.getX()!=destination_x&&this.getY()!=destination_y){
            return false;
        }
        //making sure there are no pieces in the way 
        int Yspaces = Math.abs(destination_y-this.getY()); //gets number of Y spaces for trav
        int Xspaces = Math.abs(destination_x-this.getX()); //gets number of X spaces for trav
        if(destination_y>this.getY()){ //moving north (white perspective)
            for(int i=1;i<Yspaces;i++){
                testPiece = board.getPiece(this.getX(), this.getY()+i);
                if(testPiece!=null){
                    return false;
                }
            }
        
        }else if(destination_y<this.getY()){ //moving south (white perspective)
            for(int i=1;i<Yspaces;i++){
                testPiece = board.getPiece(this.getX(), this.getY()-i);
                if(testPiece!=null){
                    return false;
                }
            }
        }else if(destination_x>this.getX()){ //moving west (white perspective)
            for(int i=1;i<Xspaces;i++){
                testPiece = board.getPiece(this.getX()+i, this.getY());
                if(testPiece!=null){
                    return false;
                }
            }
        }else if(destination_x<this.getX()){ //moving east (white perspective)
            for(int i=1;i<Xspaces;i++){
               testPiece = board.getPiece(this.getX()-i, this.getY());
                if(testPiece!=null){
                    return false;
                }
            }
        }
        
        return true;
    }
    
}
