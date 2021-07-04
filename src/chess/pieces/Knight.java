package chess.pieces;

import chess.Board;

public class Knight extends Piece {

    public Knight(int x, int y, boolean is_white, String file_path, Board board)
    {
        super(x,y,is_white,file_path, board, 'n');
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
        //checks if the piece is moving properly
        if(Math.abs(destination_x-this.getX())==2){ //L-movement of knight
            if(Math.abs(destination_y-this.getY())!=1){
                return false;
            }
        }else if(Math.abs(destination_y-this.getY())==2){
            if(Math.abs(destination_x-this.getX())!=1){
                return false;
            }
        }else{
            return false;
        }
        //no need to check for pieces cuz knight can jump over pieces

        return canMoveChecked(destination_x, destination_y);
    }
}
