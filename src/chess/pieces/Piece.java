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
    private String unicodeWhite;
    private String unicodeBlack;
    public ArrayList<Spot> moves;
    private int currX;
    private int currY;

    public Piece(int x, int y, boolean is_white, String file_path, Board board, char abbrev, String unicodeWhite, String unicodeBlack)
    {
        
        this.is_white = is_white;
        spot = new Spot(x,y, board.getIsWhitePerspective());
        this.file_path = file_path;
        this.board = board;
        this.abbrev = abbrev;
        this.unicodeWhite = unicodeWhite;
        this.unicodeBlack = unicodeBlack;
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

    public String getUnicodeWhite() {
        return unicodeWhite;
    }

    public void setUnicodeWhite(String unicodeWhite) {
        this.unicodeWhite = unicodeWhite;
    }

    public String getUnicodeBlack() {
        return unicodeBlack;
    }

    public void setUnicodeBlack(String unicodeBlack) {
        this.unicodeBlack = unicodeBlack;
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
                    availSpot = new Spot(x,y, board.getIsWhitePerspective());
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
        boolean isWhitesTurn = board.turnCounter % 2 != 1;
        pieceAtAvailSpot = board.getPiece(x, y);
        if(x>7 || y>7){
            return false;
        }

        if( this.isWhite() == (isWhitesTurn)  ) {
            if (board.whiteKing.isCheck() || board.blackKing.isCheck()) {
                // if King is in check, set piece
                this.setX(x);
                this.setY(y);
                // if set piece results to King no longer in check (block path of attacking piece or capture attacking piece)
                // and if King captures attacking piece and is no longer under attack, then move is valid
                if (isWhitesTurn) {
                    canMove = !board.whiteKing.isCheck() || (pieceAtAvailSpot != null
                            && pieceAtAvailSpot.canMove(board.whiteKing.getX(), board.whiteKing.getY())
                            && !(this.getClass().equals(King.class) && board.whiteKing.isUnderAttack(x, y))
                            );
                } else {

                    if (pieceAtAvailSpot != null) {
                        board.White_Pieces.remove(pieceAtAvailSpot);
                        canMove = !board.blackKing.isCheck() || (pieceAtAvailSpot.canMove(board.blackKing.getX(), board.blackKing.getY())
                                && !(this.getClass().equals(King.class) && board.blackKing.isUnderAttack(x, y))
                        );
                        board.White_Pieces.add(pieceAtAvailSpot);
                    }else{
                        canMove = !board.blackKing.isCheck();
                    }

                }
            } else {
                // King is not in check but if moving a piece results to ally King being in check, then move is not allowed
                this.setX(x);
                this.setY(y);
                canMove = isWhitesTurn ? !board.whiteKing.isCheck() : !board.blackKing.isCheck();
                if(!canMove){
                    if(isWhitesTurn){
                        canMove=(pieceAtAvailSpot !=null && pieceAtAvailSpot.canMove(board.whiteKing.getX(),board.whiteKing.getY()));
                    }else{
                        canMove=(pieceAtAvailSpot !=null && pieceAtAvailSpot.canMove(board.blackKing.getX(),board.whiteKing.getY()));
                    }
                }
            }

            if(x == currX && y == currY ){
                canMove = false;
            }
            this.setX(currX);
            this.setY(currY);
        }
        
        return canMove;
    }

}

