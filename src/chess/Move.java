package chess;

import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Piece;

public class Move {
    private Board board;
    private Piece movedPiece;
    private Piece capturedPiece;
    private Spot initialSpot;
    private Spot finalSpot;
    private String notation;

    public Move(Piece movedPiece, Piece capturedPiece, Spot initialSpot, Spot finalSpot, Board board) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.initialSpot = initialSpot;
        this.finalSpot = finalSpot;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public void setMovedPiece(Piece movedPiece) {
        this.movedPiece = movedPiece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public void setCapturedPiece(Piece capturedPiece) {
        this.capturedPiece = capturedPiece;
    }

    public Spot getInitialSpot() {
        return initialSpot;
    }

    public void setInitialSpot(Spot initialSpot) {
        this.initialSpot = initialSpot;
    }

    public Spot getFinalSpot() {
        return finalSpot;
    }

    public void setFinalSpot(Spot finalSpot) {
        this.finalSpot = finalSpot;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public boolean isEnPassant(Board board, Move move){
        Piece movedPiece = move.getMovedPiece();
        return (movedPiece.getClass().equals(Pawn.class) && Math.abs(move.getFinalSpot().getY() - move.getInitialSpot().getY()) == 2 );
    }

    public boolean isEnPassantCapture(Board board){
        if(board.Moves.size() > 1) {
            Move lastMove = board.Moves.get(board.Moves.size() - 1);
            Piece movedPiece = lastMove.getMovedPiece();
            Move prevMoved = board.Moves.get(board.Moves.size() - 2);
            Piece prevMovedPiece = prevMoved.getMovedPiece();
            if (prevMoved.isEnPassant(board, prevMoved) && prevMovedPiece.isWhite() != movedPiece.isWhite()
                    && movedPiece.getX() == prevMovedPiece.getX() && movedPiece.getY() != 3 && movedPiece.getY() != 4
                    && lastMove.getInitialSpot().getX() != lastMove.getFinalSpot().getX()){
                return true;
            }
        }
        return false;
    }

    public String convertToAlgebraicNotation (){

        StringBuffer notation = new StringBuffer();

        if(movedPiece.getClass().equals(Pawn.class)){
            if(capturedPiece != null){
                notation.append(initialSpot.getXLabel()).append('x');
            }
            notation.append(finalSpot.getXLabel()).append(finalSpot.getYLabel());
        }else if (movedPiece.getClass().equals(King.class) && ( Math.abs(initialSpot.getX() - finalSpot.getX()) == 2 )){
           //castling
            if(board.getIsWhitePerspective()){
                notation.append(initialSpot.getX() > finalSpot.getX() ? "0-0-0" : "0-0");
            }else{
                notation.append(initialSpot.getX() > finalSpot.getX() ? "0-0" : "0-0-0");
            }
        }else{
            //notation = Character.toString(Character.toUpperCase(movedPiece.getAbbrev()));
            notation.append(movedPiece.isWhite() ? movedPiece.getUnicodeWhite() : movedPiece.getUnicodeBlack());
            if (capturedPiece != null){
               notation.append('x');
            }
            notation.append(finalSpot.getXLabel()).append(finalSpot.getYLabel());
        }

        if( (board.whiteKing.isCheck() && !board.whiteKing.isCheckmate()) || (board.blackKing.isCheck() && !board.blackKing.isCheckmate())){
            notation.append('+');
        }else if (board.whiteKing.isCheckmate() || board.blackKing.isCheckmate()){
            notation.append('#');
        }

        return notation.toString();
    }
}
