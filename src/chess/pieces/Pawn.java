package chess.pieces;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import chess.Board;

public class Pawn extends Piece {

    private boolean has_moved;
    public char promotion;

    public Pawn(int x, int y, boolean is_white, String file_path, Board board)
    {
        super(x,y,is_white,file_path, board);
        has_moved = false;

    }
    
    public void setPromotion(char c) {
        this.promotion = c;
    }

    public char getPromotion() {
        return this.promotion;
    }

    public void setHasMoved(boolean has_moved)
    {
        this.has_moved = has_moved;
    }

    public boolean getHasMoved()
    {
        return has_moved;
    }

    /* WIP */
    // public char isPromotion(){

    //     char promotion;

    //     JPanel promotionPanel = new JPanel();
    //     JLabel promotionLabel = new JLabel("Pawn Promotions");
    //     JButton rookPromos = new JButton("Rook");
    //     JButton bishopPromos = new JButton("Bishop");
    //     JButton knightPromos = new JButton("Knight");
    //     JButton queenPromos = new JButton("Queen");

    //     ActionListener promotionListener = new ActionListener(){
    //         @Override
    //         public void actionPerformed(ActionEvent ae){

    //             Object o = ae.getSource();

    //             if(o == bishopPromos){
    //                 this.setPromotion('c');
    //             }
    //             else if(o == knightPromos){
    //                 promotion = 'k';
    //             }
    //             else if(o == rookPromos){
                    
    //             }
    //             else{

    //             }
    //         }
    //     };
    //     JFrame frame = new JFrame("Pawn Promotion");
    //     frame.setUndecorated(true);
    //     frame.setBackground(new Color(0, 0, 0, 0));
    //     frame.setAlwaysOnTop(true);
    //     // Without this, the window is draggable from any non transparent
    //     // point, including points  inside textboxes.
    //     frame.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);

    //     frame.getContentPane().setLayout(new java.awt.BorderLayout());
    //     frame.setVisible(true);

    //     frame.add(promotionPanel);
        
    //     promotionPanel.setBounds(115, 100, 300, 100);
    //     promotionPanel.add(promotionLabel);
    //     promotionPanel.add(bishopPromos);
    //     promotionPanel.add(knightPromos);
    //     promotionPanel.add(rookPromos);
    //     promotionPanel.add(queenPromos);
    //     frame.add(promotionPanel, BorderLayout.CENTER);
    //     frame.pack();

    //     bishopPromos.addActionListener(promotionListener);
    //     knightPromos.addActionListener(promotionListener);
    //     rookPromos.addActionListener(promotionListener);
    //     queenPromos.addActionListener(promotionListener);
        
    //     promotionPanel.setBounds(115, 100, 300, 100);
    //     promotionPanel.add(promotionLabel);
    //     promotionPanel.add(bishopPromos);
    //     promotionPanel.add(knightPromos);
    //     promotionPanel.add(rookPromos);
    //     promotionPanel.add(queenPromos);

    //     bishopPromos.addActionListener(promotionListener);
    //     knightPromos.addActionListener(promotionListener);
    //     rookPromos.addActionListener(promotionListener);
    //     queenPromos.addActionListener(promotionListener);

    //     if(this.isWhite()){
    //     }
    //     else{

    //     }
    //     return 'c';
    // }
    
    @Override
    public boolean canMove(int destination_x, int destination_y)
    {
        // Remember: A pawn may only move towards the oponent's side of the board.
        // If the pawn has not moved yet in the game, for its first move it can 
        // move two spaces forward. Otherwise, it may only move one space. 
        // When not attacking it may only move straight ahead.
        // When attacking it may only move space diagonally forward
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
            if(this.isWhite()){
                if(possiblepiece == null || possiblepiece.isWhite()){
                 return false;
                }
            }
            if(this.isBlack()){
                if(possiblepiece == null || possiblepiece.isBlack()){
                 return false;
                }
            }
        }
        return true;
    }

}
