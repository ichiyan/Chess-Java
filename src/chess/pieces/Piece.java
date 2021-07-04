package chess.pieces;

import java.awt.desktop.SystemEventListener;
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
    private int currX;
    private int currY;

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

        this.currX = currX;
        this.currY = currY;

        for(y=0;y<8;y++){
            for(x=0;x<8;x++){
                if(canMove(x, y)){
                    availSpot = new Spot(x,y);
                    if(!this.moves.contains(availSpot)){
                        this.moves.add(availSpot);
                    }
                }
            }
        }
        return this.moves;
    }


    public boolean canMoveChecked (int x, int y) {
        Piece pieceAtAvailSpot;
        boolean canMove = true;

        if( this.isWhite() == (board.turnCounter % 2 != 1)  ) {
            if (board.whiteKing.isCheck() || board.blackKing.isCheck()) {
                // if King is in check, set piece
                pieceAtAvailSpot = board.getPiece(x, y);
                this.setX(x);
                this.setY(y);
                if (board.turnCounter % 2 != 1) {
                    // if set piece results to King no longer in check (block path of attacking piece or capture attacking piece)
                    // and if King captures attacking piece and is no longer under attack
                    // then move is valid

                    canMove = !board.whiteKing.isCheck() || (pieceAtAvailSpot != null
                            && pieceAtAvailSpot.canMove(board.whiteKing.getX(), board.whiteKing.getY())
                            && !(this.getClass().equals(King.class) && board.whiteKing.isUnderAttack(x, y))
                            );

                } else {
                    canMove = !board.blackKing.isCheck() || (pieceAtAvailSpot != null
                            && pieceAtAvailSpot.canMove(board.blackKing.getX(), board.blackKing.getY())
                            // sumn wrong w/ last condition for black, attacking piece spot is still available even if
                            // capturing attacking piece results to king still in check, will fix later huhu
                            && !(this.getClass().equals(King.class) && board.blackKing.isUnderAttack(x, y))
                            );
                }
            } else {
                // King is not in check but if moving a piece results to ally King being in check
                // then move is not allowed
                this.setX(x);
                this.setY(y);
                if (board.turnCounter % 2 != 1) {
                    canMove = !board.whiteKing.isCheck();
                } else {
                    canMove = !board.blackKing.isCheck();
                }
            }
            this.setX(currX);
            this.setY(currY);
        }
        return canMove;
    }

}

