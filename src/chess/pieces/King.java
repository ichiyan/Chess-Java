package chess.pieces;
import chess.Board;

public class King extends Piece {

    private boolean has_moved;
    private boolean isFirstMove;
    private boolean isCastleMove;

    public King(int x, int y, boolean is_white, String file_path, Board board)
    {
        super(x,y,is_white,file_path, board, 'k');
        has_moved = false;
        isFirstMove = false;
        isCastleMove = false;
    }

    public void setHasMoved(boolean has_moved)
    {
        this.has_moved = has_moved;
    }
    
    public boolean getHasMoved()
    {
        return has_moved;
    }
    public boolean getIsFirstMove() {
        return isFirstMove;
    }

    public void setIsFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    public boolean getIsCastleMove() {
        return isCastleMove;
    }

    public void setIsCastleMove(boolean castleMove) {
        isCastleMove = castleMove;
    }

    @Override
    public boolean canMove(int destination_x, int destination_y) {
        Piece testPiece;
        testPiece = board.getPiece(destination_x, destination_y);

        if(Math.abs(destination_x-this.getX()) > 1 || Math.abs(destination_y-this.getY())  > 1 ){
            if(destination_y == this.getY()) {
                if (destination_x - this.getX() == 2) {
                   return canCastleKingSide();
                } else if (destination_x - this.getX() == -2) {
                    return canCastleQueenSide();
                }
            }
            return false;
        }else{
            if(testPiece!=null && testPiece.isWhite() == this.isWhite()){
                return false;
            }else if(isUnderAttack(destination_x, destination_y)) {
                return false;
            }
        }

        return canMoveChecked(destination_x, destination_y);

    }



    public boolean canCastleKingSide(){
        Piece kingRook = board.getPiece(7, this.getY());
        if (this.has_moved || kingRook==null || ( kingRook.getClass().equals(Rook.class) && ((Rook)kingRook).getHasMoved() ) ) {
            return false;
        } else {
            for(int i = this.getX();i<=this.getX()+2;i++){
                if(isUnderAttack(i, this.getY()) || (i+1<7 && board.getPiece(i+1, this.getY()) != null)){
                    return false;
                }
            }
        }
        return true;
    }


    public boolean canCastleQueenSide(){
        Piece queenRook = board.getPiece(0, this.getY());
        if (this.has_moved || queenRook==null || ( queenRook.getClass().equals(Rook.class) && ((Rook)queenRook).getHasMoved() )) {
            return false;
        } else {
            for(int i = this.getX();i>=this.getX()-2;i--){
                if(isUnderAttack(i, this.getY()) || (board.getPiece(i-1, this.getY()) != null )){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean hasQueenSideCastlingRights(){
        Piece queenRook = board.getPiece(0, this.getY());
        if (this.has_moved || queenRook==null || ( queenRook.getClass().equals(Rook.class) && ((Rook)queenRook).getHasMoved() )) {
            return false;
        }
        return true;
    }

    public boolean hasKingSideCastlingRights(){
        Piece kingRook = board.getPiece(7, this.getY());
        if (this.has_moved || kingRook==null || ( kingRook.getClass().equals(Rook.class) && ((Rook)kingRook).getHasMoved() ) ) {
            return false;
        }
        return true;
    }


    public boolean isUnderAttack(int x, int y){

        if(this.isWhite()){
            for(Piece piece: board.Black_Pieces){
                if(piece.canMove(x,y) || (piece.getClass().equals(Pawn.class) && canPawnAttack(x,y, piece))  ){
                    return true;
                }
            }
        }
        else if(this.isBlack()){
            for(Piece piece: board.White_Pieces){
                if(piece.canMove(x,y) || (piece.getClass().equals(Pawn.class) && canPawnAttack(x,y, piece)) ){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canPawnAttack (int x, int y, Piece pawn){

        if (pawn.getX()+1 == x || pawn.getX()-1 == x) {
            if (board.getIsWhitePerspective() ) {
                if ( (pawn.isWhite() && pawn.getY()-1 == y) || (pawn.isBlack() && pawn.getY()+1 == y) ) {
                    return true;
                }
            }else{
                if ( (pawn.isWhite() && pawn.getY()+1 == y) || (pawn.isBlack() && pawn.getY()-1 == y) ) {
                    return true;
                }
            }
        }
        return false;
    }



    public boolean isCheck(){
        return isUnderAttack(this.getX(), this.getY());
    }
    public boolean isCheckmate(){
        
        boolean isWhitesTurn = board.turnCounter % 2 != 1;
        if(this.isCheck() && !isWhitesTurn){
            for (Piece piece : board.Black_Pieces) {
                if(!piece.availableMoves(piece.getX(), piece.getY()).isEmpty()){
                    return false;
                }
            }
            return true;
        }else if(this.isCheck() && isWhitesTurn){
            for (Piece piece : board.White_Pieces) {
                if(!piece.availableMoves(piece.getX(), piece.getY()).isEmpty()){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public boolean isDraw(){
        
        return false;
    }
}
