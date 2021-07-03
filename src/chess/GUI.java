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
    JButton playAgainstEngineBtn;
    StartBtnHandler startBtnHandler;
    PlayAgainstEngineHandler playAgainstEngineHandler;

    public GUI() {

        panel = new ImagePanel(new ImageIcon("images/background2.jpg").getImage().getScaledInstance(560, 650, Image.SCALE_SMOOTH));

        titlePanel = new JPanel();
        titlePanel.setBounds(130, 100, 300, 100);
        titlePanel.setBackground(Color.BLACK);

        titleLabel = new JLabel("CHESS");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 72));

        titlePanel.add(titleLabel);

        startBtn = new JButton("Start Game");
        startBtn.setBounds(190, 300, 200, 50);
        startBtn.setFocusable(false);
        startBtn.setBackground(Color.BLACK);
        startBtn.setForeground(Color.WHITE);

        startBtnHandler = new StartBtnHandler();
        startBtn.addActionListener(startBtnHandler);

        playAgainstEngineBtn = new JButton("Play Against Engine");
        playAgainstEngineBtn.setBounds(190, 400, 200, 50);
        playAgainstEngineBtn.setFocusable(false);
        playAgainstEngineBtn.setBackground(Color.BLACK);
        playAgainstEngineBtn.setForeground(Color.WHITE);

        playAgainstEngineHandler = new PlayAgainstEngineHandler();
        playAgainstEngineBtn.addActionListener(playAgainstEngineHandler);

        panel.add(titlePanel);
        panel.add(startBtn);
        panel.add(playAgainstEngineBtn);

        imageIcon = new ImageIcon("images/app_icon.png");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Java Chess");
        this.setIconImage(imageIcon.getImage());
        this.setResizable(false);
        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
    }

    public void createGameScreen(boolean isAgainstEngine) {
        panel.setVisible(false);
        component = new Board(isAgainstEngine);
        this.add(component, BorderLayout.CENTER);
    }


    class StartBtnHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createGameScreen(false);
        }
    }

    class PlayAgainstEngineHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            createGameScreen(true);
        }
    }
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