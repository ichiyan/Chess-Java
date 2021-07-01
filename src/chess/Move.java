package chess;

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
}
