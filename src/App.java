import chess.GUI;

public class App {
    GUI board;
    public static void main(String[] args) throws Exception {
        App app = new App();
        app.board = new GUI();
        app.board.setVisible(true);
    }
}
