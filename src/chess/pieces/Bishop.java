package chess.pieces;

import chess.Board;

public class Bishop extends Piece {

    public Bishop(int x, int y, boolean is_white, String file_path, Board board)
    {
        super(x,y,is_white,file_path, board, 'b');
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
        //checks if the piece is moving diagonally
        if(this.getX()==destination_x||this.getY()==destination_y){ //must have diff x and y
            return false;
        }
        if(Math.abs(destination_x-this.getX())!= Math.abs(destination_y-this.getY())){ //must have same x and y diff to be diagonal
            return false;
        }
        //making sure there are no pieces in the way
        int i;
        int spaces = Math.abs(destination_x-this.getX()); //gets number of spaces for trav
        
        if(destination_x>this.getX() && destination_y>this.getY()){ 
            for( i=1;i<spaces;i++){
                testPiece = board.getPiece(this.getX()+i, this.getY()+i);
                if(testPiece!=null){
                    return false;
                }
            }
        }else if(destination_x>this.getX() && destination_y<this.getY()){ 
            for(i=1;i<spaces;i++){
                testPiece = board.getPiece(this.getX()+i, this.getY()-i);
                if(testPiece!=null){
                    return false;
                }
            }
        }else if(destination_x<this.getX() && destination_y<this.getY()){
            for(i=1;i<spaces;i++){
                testPiece = board.getPiece(this.getX()-i, this.getY()-i);
                if(testPiece!=null){
                    return false;
                }
            }
        }else if(destination_x<this.getX() && destination_y>this.getY()){
            for(i=1;i<spaces;i++){
                testPiece = board.getPiece(this.getX()-i, this.getY()+i);
                if(testPiece!=null){
                    return false;
                }
            }
        }
        return isBlockMove(destination_x, destination_y);
    }
}
