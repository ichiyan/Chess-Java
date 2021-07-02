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
    public boolean canMove(int destination_x, int destination_y)
    {
        Piece testPiece;
        int i;

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
        if(testPiece!=null){
            if(testPiece.isWhite()&&this.isWhite()){
                return false;
            }else if(testPiece.isBlack()&&this.isBlack()){
                return false;
            }
        }else if(isUnderAttack(destination_x, destination_y)){return false;}
    }

    return true;

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

        board.Attackers.clear();
        if(this.isWhite()){
            for(Piece piece: board.Black_Pieces){
                if(piece.canMove(x,y)){
                    board.Attackers.add(piece);
                    return true;
                }
            }
        }
        else if(this.isBlack()){
            for(Piece piece: board.White_Pieces){
                if(piece.canMove(x,y)){
                    board.Attackers.add(piece);
                    return true;
                }
            }
        }
        return false;
    }
}
