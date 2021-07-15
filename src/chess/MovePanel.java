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
    private StringBuffer moves = new StringBuffer();
    
    public MovePanel(){
        
        super(new GridBagLayout());

        title = new JLabel("Moves");
        title.setFont(new Font(null, Font.BOLD, 16));
        title.setOpaque(false);
        title.setPreferredSize(new Dimension(100, 50));

        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(title, c);
 
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    }
    
    public void updateMove(String move){
        
        moves.append(move);

        textArea.append(move);
        
        
    }
    public void undoMove(){
        
    }
 
}

