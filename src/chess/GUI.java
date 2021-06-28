package chess;
import java.awt.*;
import javax.swing.*;

public class GUI extends JFrame{
    Component component;
    public GUI(){
        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Java Chess");
        this.setResizable(false);
        component = new Board();
        this.add(component, BorderLayout.CENTER);
        
        this.setLocation(200, 50);
        this.pack();
        this.setVisible(true);
        
        
    }
}
