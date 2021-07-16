package chess;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.font.*;

public class MovePanel extends JPanel{
    private JLabel title;
    protected JTextArea textArea;
    private final static String newline = "\n";
    private Stack<String> moveList = new Stack();
    
    public MovePanel(){
        
        super(new GridBagLayout());

        moveList.clear();

        title = new JLabel("      Moves");
        title.setFont(new Font(null, Font.BOLD, 16));
        title.setOpaque(false);
        title.setPreferredSize(new Dimension(100, 50));

        textArea = new JTextArea(5, 20);
        textArea.setFont(new Font(null, Font.BOLD, 12));
        textArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.CENTER;
        add(title, c);
 
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }
    
    public void updateMove(String move,int fullMoveCounter,int turnCounter){
        StringBuffer fullMove = new StringBuffer();
        boolean isWhitesTurn = !(turnCounter % 2 != 1);
        String template="",toAppend="";
        

        if(isWhitesTurn){
            template = "%d.) %-15s";
            toAppend=String.format(template, fullMoveCounter,move);
            fullMove.append(toAppend);
            
        }else{
            
            template = "%s%n";
            toAppend=String.format(template, move);
            fullMove.append(toAppend);
            
        }
        
            
        moveList.add(fullMove.toString());
        textArea.append(fullMove.toString());
        
        for (String string : moveList) {
            System.out.println(string);
        }
    }
    
    public void undoMove(){
        StringBuffer moves = new StringBuffer();
        moveList.pop();
        for (String string : moveList) {
            moves.append(string);
        }
        textArea.setText(null);
        textArea.append(moves.toString());
        
    }
 
}

