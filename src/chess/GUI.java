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
    JButton loadBtn;
    JButton loadEngineBtn;
    StartBtnHandler startBtnHandler;
    PlayAgainstEngineHandler playAgainstEngineHandler;
    LoadBtnHandler loadBtnHandler;
    LoadEngineBtnHandler loadEngineBtnHandler;

    public GUI() {

        panel = new ImagePanel(new ImageIcon("images/background2.jpg").getImage().getScaledInstance(800, 650, Image.SCALE_SMOOTH));

        titlePanel = new JPanel();
        titlePanel.setBounds(250, 100, 300, 100);
        titlePanel.setBackground(Color.BLACK);

        titleLabel = new JLabel("CHESS");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 72));

        titlePanel.add(titleLabel);

        startBtn = new JButton("Start New Game");
        startBtn.setBounds(300, 300, 200, 50);
        startBtn.setFocusable(false);
        startBtn.setBackground(Color.BLACK);
        startBtn.setForeground(Color.WHITE);

        startBtnHandler = new StartBtnHandler();
        startBtn.addActionListener(startBtnHandler);

        loadBtn = new JButton("Load Previous Game");
        loadBtn.setBounds(170, 550, 200, 50);
        loadBtn.setFocusable(false);
        loadBtn.setBackground(Color.black);
        loadBtn.setForeground(Color.white);

        loadBtnHandler = new LoadBtnHandler();
        loadBtn.addActionListener(loadBtnHandler);

        playAgainstEngineBtn = new JButton("Play Against Engine");
        playAgainstEngineBtn.setBounds(300, 400, 200, 50);
        playAgainstEngineBtn.setFocusable(false);
        playAgainstEngineBtn.setBackground(Color.BLACK);
        playAgainstEngineBtn.setForeground(Color.WHITE);

        playAgainstEngineHandler = new PlayAgainstEngineHandler();
        playAgainstEngineBtn.addActionListener(playAgainstEngineHandler);

        loadEngineBtn = new JButton("Load Previous Engine Game");
        loadEngineBtn.setBounds(420, 550, 200, 50);
        loadEngineBtn.setFocusable(false);
        loadEngineBtn.setBackground(Color.black);
        loadEngineBtn.setForeground(Color.white);

        loadEngineBtnHandler = new LoadEngineBtnHandler();
        loadEngineBtn.addActionListener(loadEngineBtnHandler);

        panel.add(titlePanel);
        panel.add(startBtn);
        panel.add(loadBtn);
        panel.add(playAgainstEngineBtn);
        panel.add(loadEngineBtn);

        imageIcon = new ImageIcon("images/app_icon.png");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Java Chess");
        this.setIconImage(imageIcon.getImage());
        this.setResizable(false);
        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
    }

    public void createGameScreen(boolean isAgainstEngine, boolean isWhitePerspective) {
        panel.setVisible(false);
        MovePanel mp = new MovePanel();
        mp.setMinimumSize(new Dimension(350, 560));

        component = new Board(isAgainstEngine,false, isWhitePerspective,mp, panel);
        component.setMinimumSize(new Dimension(560, 560));


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, component, mp);
        this.add(splitPane, BorderLayout.CENTER);

//        component = new Board(isAgainstEngine, isWhitePerspective, panel);
//        this.add(component, BorderLayout.CENTER);
    }

    public void loadGameScreen(boolean isAgainstEngine){
        panel.setVisible(false);
        Board board = new Board(isAgainstEngine, true, true, null, panel);
        component = board;
        this.add(component, BorderLayout.CENTER);
    }

    class LoadBtnHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            loadGameScreen(false);
        }
    }
    class LoadEngineBtnHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            loadGameScreen(true);
        }
    }
    class StartBtnHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createGameScreen(false, true);
            
        }
    }

    class PlayAgainstEngineHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Object[] options = {"White", "Black",};
            int n = JOptionPane.showOptionDialog(panel,
                    "Play as: ",
                    "Choose Side",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    null);
            if (n == 0){
                createGameScreen(true, true);
            }else if (n == 1){
                createGameScreen(true, false);
            }
        }
    }
}

class ImagePanel extends JPanel {
    private Image img;

    public ImagePanel(Image img){
        this.img = img;
        //Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        this.setPreferredSize(new Dimension(800, 650));
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