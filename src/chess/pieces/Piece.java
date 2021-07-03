package chess.pieces;

import java.util.ArrayList;

import chess.Board;
import chess.Spot;

public abstract class Piece {
    Spot spot;
    final private boolean is_white;
    private String file_path;
    public Board board;
    private char abbrev;
    public ArrayList<Spot> moves;
    public Piece(int x, int y, boolean is_white, String file_path, Board board, char abbrev)
    {
        
        this.is_white = is_white;
        spot = new Spot(x,y);
        this.file_path = file_path;
        this.board = board;
        this.abbrev = abbrev;
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
        this.spot.setX(x);
        
    }
    
    public void setY(int y)
    {
        this.spot.setY(y);
    }
    
    public int getX()
    {
        return this.spot.getX();
    }
    
    public int getY()
    {
        return this.spot.getY();
    }

    public char getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(char abbrev) {
        this.abbrev = abbrev;
    }

    public abstract boolean canMove(int destination_x, int destination_y);

    public ArrayList<Spot> availableMoves(int currX, int currY){
        this.moves.clear();
        int x;
        int y;
        Spot availSpot;
        Piece pieceAtAvailSpot;

        for(y=0;y<8;y++){
            for(x=0;x<8;x++){
                if(canMove(x, y)){
                    availSpot = new Spot(x,y);
                    if(!this.moves.contains(availSpot)){
                        if(board.whiteKing.isCheck() || board.blackKing.isCheck()){
                            pieceAtAvailSpot = board.getPiece(availSpot.getX(), availSpot.getY());
                            this.setX(availSpot.getX());
                            this.setY(availSpot.getY());
                            if(board.turnCounter % 2 != 1 ){
                                if( !board.whiteKing.isCheck() || ( pieceAtAvailSpot != null
                                        && pieceAtAvailSpot.canMove(board.whiteKing.getX(), board.whiteKing.getY()) ) ){
                                    this.moves.add(availSpot);
                                }
                            }else {
                                if (!board.blackKing.isCheck() || ( pieceAtAvailSpot != null
                                        && pieceAtAvailSpot.canMove(board.blackKing.getX(), board.blackKing.getY()) ) ) {
                                    this.moves.add(availSpot);
                                }
                            }
                        }else{
                            this.moves.add(availSpot);
                        }
                        this.setX(currX);
                        this.setY(currY);
                    }
                }
            }
        }
        return this.moves;
    }

//    public boolean isBlockMove(int x, int y){
//        int kingX, kingY;
//
//
//        if(this.isWhite()){
//            kingX = board.whiteKing.getX();
//            kingY = board.whiteKing.getY();
//            if(board.whiteKing.isCheck()){
//                for (Piece piece : board.Black_Pieces) {
//
//
//                    if(piece.canMove(kingX, kingY) && !piece.canMove(x, y)){
//                            return false;
//                    }
//                }
//            }
//        }
//        if(this.isBlack()){
//            kingX = board.blackKing.getX();
//            kingY = board.blackKing.getY();
//            if(board.blackKing.isCheck()){
//                for (Piece piece : board.White_Pieces) {
//                    if(piece.canMove(kingX, kingY) && !piece.canMove(x, y)){
//                        return false;
//                    }
//                }
//            }
//        }
//        return true;
//    }
}

