package chess;

import chess.pieces.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PromotionPanel extends JFrame {
    public PromotionPanel(Pawn promotedPawn, Board board){


        JPanel promotionPanel = new JPanel();
        JLabel promotionLabel = new JLabel("Pawn Promotions");
        JButton rookPromos = new JButton("Rook");
        JButton bishopPromos = new JButton("Bishop");
        JButton knightPromos = new JButton("Knight");
        JButton queenPromos = new JButton("Queen");


        JFrame frame = new JFrame("Pawn Promotion");
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setAlwaysOnTop(true);
        // Without this, the window is draggable from any non transparent
        // point, including points  inside textboxes.
        frame.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);

        frame.getContentPane().setLayout(new java.awt.BorderLayout());
        frame.setVisible(true);

        promotionPanel.setBounds(115, 100, 300, 100);
        promotionPanel.add(promotionLabel);
        promotionPanel.add(bishopPromos);
        promotionPanel.add(knightPromos);
        promotionPanel.add(rookPromos);
        promotionPanel.add(queenPromos);
        frame.add(promotionPanel, BorderLayout.CENTER);
        frame.pack();



        ActionListener promotionListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                int ndx, x, y;

                if(promotedPawn.isWhite()){
                    ndx = board.White_Pieces.indexOf(promotedPawn);
                }else{
                    ndx = board.Black_Pieces.indexOf(promotedPawn);
                }

                x = promotedPawn.getX();
                y = promotedPawn.getY();

                frame.dispose();
                Object o = ae.getSource();
                if(o == bishopPromos){
                    if(promotedPawn.isWhite()){
                        board.White_Pieces.set(ndx, new Bishop(x, y, true, "Bishop.png", board));
                        board.drawBoard();
                    }else{
                        board.Black_Pieces.set(ndx, new Bishop(x, y, false, "Bishop.png", board));
                        board.drawBoard();
                    }

                }
                else if(o == knightPromos){
                    if(promotedPawn.isWhite()){
                        board.White_Pieces.set(ndx, new Knight(x, y, true, "Knight.png", board));
                        board.drawBoard();
                    }else{
                        board.Black_Pieces.set(ndx, new Knight(x, y, false, "Knight.png", board));
                        board.drawBoard();
                    }

                }
                else if(o == rookPromos){
                    if(promotedPawn.isWhite()){
                        board.White_Pieces.set(ndx, new Rook(x, y, true, "Rook.png", board));
                        board.drawBoard();
                    }else{
                        board.Black_Pieces.set(ndx, new Rook(x, y, false, "Rook.png", board));
                        board.drawBoard();
                    }
                    
                }
                else{
                    if(promotedPawn.isWhite()){
                        board.White_Pieces.set(ndx, new Queen(x, y, true, "Queen.png", board));
                        board.drawBoard();
                    }else{
                        board.Black_Pieces.set(ndx, new Queen(x, y, false, "Queen.png", board));
                        board.drawBoard();
                    }
                }
            }
        };

        bishopPromos.addActionListener(promotionListener);
        knightPromos.addActionListener(promotionListener);
        rookPromos.addActionListener(promotionListener);
        queenPromos.addActionListener(promotionListener);
    }
}