package chess.pieces;

import chess.Board;

public class Queen extends Piece {

    public Queen(int x, int y, boolean is_white, String file_path, Board board)
    {
        super(x,y,is_white,file_path, board, 'q');
    }
    
    @Override
    public boolean canMove(int destination_x, int destination_y)
    {
        Piece testPiece = board.getPiece(destination_x, destination_y);
        int i;
        int spaces = Math.abs(destination_x-this.getX());
        int Yspaces = Math.abs(destination_y-this.getY()); //gets number of Y spaces for trav
        int Xspaces = Math.abs(destination_x-this.getX()); //gets number of X spaces for trav
        if(testPiece!=null){
            if(testPiece.isWhite()&&this.isWhite()){
                return false;
            }else if(testPiece.isBlack()&&this.isBlack()){
                return false;
            }
        }
        
        if(Math.abs(destination_x-this.getX())!= Math.abs(destination_y-this.getY())){ 
            if(this.getX()!=destination_x&&this.getY()!=destination_y){
                return false;
            }
            
        }
        
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
        }else if(destination_y>this.getY()){ //moving north (white perspective)
            for( i=1;i<Yspaces;i++){
                testPiece = board.getPiece(this.getX(), this.getY()+i);
                if(testPiece!=null){
                    return false;
                }
            }
        
        }else if(destination_y<this.getY()){ //moving south (white perspective)
            for( i=1;i<Yspaces;i++){
                testPiece = board.getPiece(this.getX(), this.getY()-i);
                if(testPiece!=null){
                    return false;
                }
            }
        }else if(destination_x>this.getX()){ //moving west (white perspective)
            for( i=1;i<Xspaces;i++){
                testPiece = board.getPiece(this.getX()+i, this.getY());
                if(testPiece!=null){
                    return false;
                }
            }
        }else if(destination_x<this.getX()){ //moving east (white perspective)
            for( i=1;i<Xspaces;i++){
               testPiece = board.getPiece(this.getX()-i, this.getY());
                if(testPiece!=null){
                    return false;
                }
            }
        }
        
        return true;
    }
}
