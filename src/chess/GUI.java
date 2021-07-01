package chess;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUI extends JFrame {
    ImageIcon imageIcon;
    Component component;
    ImagePanel panel;
    JPanel titlePanel;
    JLabel titleLabel;
    JButton startBtn;
    StartBtnHandler startBtnHandler;

    public GUI() {

        panel = new ImagePanel(new ImageIcon("images/background2.jpg").getImage().getScaledInstance(560, 650, Image.SCALE_SMOOTH));

        titlePanel = new JPanel();
        titlePanel.setBounds(120, 100, 300, 100);
        titlePanel.setBackground(Color.BLACK);

        titleLabel = new JLabel("CHESS");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 72));

        titlePanel.add(titleLabel);

        startBtn = new JButton("Start Game");
        startBtn.setBounds(220, 300, 100, 50);
        startBtn.setFocusable(false);
        startBtn.setBackground(Color.BLACK);
        startBtn.setForeground(Color.WHITE);

        startBtnHandler = new StartBtnHandler();
        startBtn.addActionListener(startBtnHandler);

        panel.add(titlePanel);
        panel.add(startBtn);

        imageIcon = new ImageIcon("images/app_icon.png");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Java Chess");
        this.setIconImage(imageIcon.getImage());
        this.setResizable(false);
        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
    }

    public void createGameScreen() {
        panel.setVisible(false);
        component = new Board();
        this.add(component, BorderLayout.CENTER);
    }


    class StartBtnHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createGameScreen();
        }
    }
}

class promotionPanel extends JPanel {
    
}

class ImagePanel extends JPanel {
    private Image img;

    public ImagePanel(Image img){
        this.img = img;
        //Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        this.setPreferredSize(new Dimension(560, 650));
        this.setMinimumSize(new Dimension(100, 100));
        this.setMaximumSize(new Dimension(1000, 1000));
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }

}