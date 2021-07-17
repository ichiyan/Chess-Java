import chess.GUI;


public class App {
    GUI startScreen;
    public static void main(String[] args) throws Exception {
        App app = new App();
        app.startScreen = new GUI();
        app.startScreen.setVisible(true);
    }
}

