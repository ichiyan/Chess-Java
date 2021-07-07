package chess;

import chess.pieces.Pawn;
import chess.pieces.Piece;

public class Move {
    private Piece movedPiece;
    private Piece capturedPiece;
    private Spot initialSpot;
    private Spot finalSpot;

    public Move(Piece movedPiece, Piece capturedPiece, Spot initialSpot, Spot finalSpot) {
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
}
