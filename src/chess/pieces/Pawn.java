package chess.pieces;
import chess.Board;
import chess.Move;
import chess.PromotionPanel;

public class Pawn extends Piece {

    private boolean has_moved;
    private boolean isFirstMove;


    public Pawn(int x, int y, boolean is_white, String file_path, Board board)
    {
        super(x,y,is_white,file_path, board, 'p');
        has_moved = false;
        isFirstMove = false;

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


    /* WIP */
    public void isPromotion(Pawn promotedPawn){
        PromotionPanel promotionGUI = new PromotionPanel(promotedPawn, board);
    }

    @Override
    public boolean canMove(int destination_x, int destination_y)
    {

        Piece possiblepiece = board.getPiece(destination_x,destination_y);
        //Check if the move is backwards where it won't let it move.
        if(this.isWhite()){
            if(this.getY()<destination_y){
                return false;
            }
        } else if (this.isBlack()){
            if(this.getY()>destination_y){
                return false;
            }
        }
        
        //checks if there is a piece in front.
        if(this.getX()==destination_x){
            if(this.isWhite()){
                Piece frontwhite = board.getPiece(this.getX(), this.getY()-1);
                if(frontwhite!=null){
                    return false;
                }
            } else if(this.isBlack()){
                Piece frontblack = board.getPiece(this.getX(), this.getY()+1);
                if(frontblack!=null){
                    return false;
                }
            }
            //Pawn movement where it only allows the two square movement at the start and also checks if there is a piece two squares infront.
            if(Math.abs(destination_y-this.getY())>2){
                return false;
            } else if(Math.abs(destination_y-this.getY())==2){
                if(this.has_moved){
                    return false;
                }
                if(this.isWhite()){
                    Piece frontwhite1 = board.getPiece(this.getX(), this.getY()-2);
                    if(frontwhite1!=null){
                        return false;
                    }
                } else if (this.isBlack()){
                    Piece frontblack1= board.getPiece(this.getX(), this.getY()+2);
                    if(frontblack1!=null){
                        return false;
                    }
                }
            }
        }else{
            //Taking A piece.
            if(Math.abs(destination_x - this.getX())!= 1 || Math.abs(destination_y-this.getY())!=1){
               return false;
            }

            //en passant capture
            if(this.getY() == 3 || this.getY() == 4){
                Move lastMoved = board.Moves.get(board.Moves.size()-1);
                Piece movedPiece = lastMoved.getMovedPiece();
                if(lastMoved.isEnPassant(board, lastMoved) && movedPiece.isWhite() != this.isWhite()){
                    if (lastMoved.getFinalSpot().getY() - lastMoved.getInitialSpot().getY() > 0) {
                        return (destination_y == lastMoved.getFinalSpot().getY()-1 && destination_x == movedPiece.getX());
                    }else{
                        return (destination_y == lastMoved.getFinalSpot().getY()+1 && destination_x == movedPiece.getX());
                    }
                }

            }


            if(this.isWhite()){
                if((possiblepiece == null) || possiblepiece.isWhite()){
                 return false;
                }
            }
            if(this.isBlack()){
                if(possiblepiece == null || possiblepiece.isBlack()){
                 return false;
                }
            }

        }
        return canMoveChecked(destination_x, destination_y);
    }
}
