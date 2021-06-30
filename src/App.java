import chess.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class App {
    GUI startScreen;
    public static void main(String[] args) throws Exception {
        App app = new App();
        app.startScreen = new GUI();
        app.startScreen.setVisible(true);
    }
}

