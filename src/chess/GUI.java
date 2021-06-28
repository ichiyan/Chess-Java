package chess;
import java.awt.*;
import javax.swing.*;

public class GUI extends JFrame{
    public GUI(){
        
        JPanel panel = new JPanel();
        
        panel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        panel.setLayout(new GridLayout(0,1));
        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Test");
        this.pack();
        this.setVisible(true);
        panel.setBackground(new Color(37,13,84));
        panel.setPreferredSize(new Dimension(520, 520));
        panel.setMinimumSize(new Dimension(100, 100));
        panel.setMaximumSize(new Dimension(1000, 1000));
        
        
    }
}
