package chess;

import chess.pieces.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;
import java.time.Instant;

public class Board extends JComponent {

    private boolean isAgainstEngine;
    private boolean isWhitePerspective;
    private Stockfish stockfish;
    public int turnCounter = 0;
    public int fullMoveCounter = 1;
    public int halfMoveCounter = 0;
    public int prevHalfMoveCounter = 0;  //stores halfMoveCounter before reset in case of undo
    private static Image NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
    private boolean displayedMessage = false;
    private ImagePanel panel;
    private int skillLevel;

    private final int Square_Width = 65;
    public Board board = this;
    public ArrayList<Piece> White_Pieces;
    public ArrayList<Piece> Black_Pieces;

    public King whiteKing;
    public King blackKing;
    public ArrayList<Move> Moves;

    public ArrayList<DrawingShape> Static_Shapes;
    public ArrayList<DrawingShape> Piece_Graphics;

    private Piece Active_Piece;

    private final int rows = 8;
    private final int cols = 8;
    public Integer[][] BoardGrid;
    private String board_white_perspective_file_path = "images" + File.separator + "board3.png";
    private String board_black_perspective_file_path = "images" + File.separator + "board3_black_perspective.png";
    private String active_square_file_path = "images" + File.separator + "active_square.png";
    public MovePanel movePanel;
    JButton undoBtn;
    UndoBtnHandler undoBtnHandler;
    JButton saveBtn;
    SaveBtnHandler saveBtnHandler;

    //ChessClock lowerClock, upperClock;
    GameClock clock;

    public void loadGrid(boolean isAgainstEngine){

        System.out.println(White_Pieces.size());

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                BoardGrid[i][j] = 0;
            }
        }

        Moves = new ArrayList<>();
        loadGame(isAgainstEngine);
    }

    public boolean getIsWhitePerspective() {
        return isWhitePerspective;
    }

    public void setIsWhitePerspective(boolean whitePerspective) {
        isWhitePerspective = whitePerspective;
    }

    public void initGrid(boolean isWhitePerspective)
    {
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                BoardGrid[i][j] = 0;
            }
        }

        //Image white_piece = loadImage("images/white_pieces/" + piece_name + ".png");
        //Image black_piece = loadImage("images/black_pieces/" + piece_name + ".png");

        if (isWhitePerspective) {
            White_Pieces.add(new King(4, 7, true, "King.png", this));
            White_Pieces.add(new Queen(3, 7, true, "Queen.png", this));
            White_Pieces.add(new Bishop(2, 7, true, "Bishop.png", this));
            White_Pieces.add(new Bishop(5, 7, true, "Bishop.png", this));
            White_Pieces.add(new Knight(1, 7, true, "Knight.png", this));
            White_Pieces.add(new Knight(6, 7, true, "Knight.png", this));
            White_Pieces.add(new Rook(0, 7, true, "Rook.png", this));
            White_Pieces.add(new Rook(7, 7, true, "Rook.png", this));
            White_Pieces.add(new Pawn(0, 6, true, "Pawn.png", this));
            White_Pieces.add(new Pawn(1, 6, true, "Pawn.png", this));
            White_Pieces.add(new Pawn(2, 6, true, "Pawn.png", this));
            White_Pieces.add(new Pawn(3, 6, true, "Pawn.png", this));
            White_Pieces.add(new Pawn(4, 6, true, "Pawn.png", this));
            White_Pieces.add(new Pawn(5, 6, true, "Pawn.png", this));
            White_Pieces.add(new Pawn(6, 6, true, "Pawn.png", this));
            White_Pieces.add(new Pawn(7, 6, true, "Pawn.png", this));


            Black_Pieces.add(new King(4, 0, false, "King.png", this));
            Black_Pieces.add(new Queen(3, 0, false, "Queen.png", this));
            Black_Pieces.add(new Bishop(2, 0, false, "Bishop.png", this));
            Black_Pieces.add(new Bishop(5, 0, false, "Bishop.png", this));
            Black_Pieces.add(new Knight(1, 0, false, "Knight.png", this));
            Black_Pieces.add(new Knight(6, 0, false, "Knight.png", this));
            Black_Pieces.add(new Rook(0, 0, false, "Rook.png", this));
            Black_Pieces.add(new Rook(7, 0, false, "Rook.png", this));
            Black_Pieces.add(new Pawn(0, 1, false, "Pawn.png", this));
            Black_Pieces.add(new Pawn(1, 1, false, "Pawn.png", this));
            Black_Pieces.add(new Pawn(2, 1, false, "Pawn.png", this));
            Black_Pieces.add(new Pawn(3, 1, false, "Pawn.png", this));
            Black_Pieces.add(new Pawn(4, 1, false, "Pawn.png", this));
            Black_Pieces.add(new Pawn(5, 1, false, "Pawn.png", this));
            Black_Pieces.add(new Pawn(6, 1, false, "Pawn.png", this));
            Black_Pieces.add(new Pawn(7, 1, false, "Pawn.png", this));

            whiteKing = (King) getPiece(4, 7);
            blackKing = (King) getPiece(4, 0);
        }else{
            White_Pieces.add(new King(3, 0, true, "King.png", this));
            White_Pieces.add(new Queen(4, 0, true, "Queen.png", this));
            White_Pieces.add(new Bishop(2, 0, true, "Bishop.png", this));
            White_Pieces.add(new Bishop(5, 0, true, "Bishop.png", this));
            White_Pieces.add(new Knight(1, 0, true, "Knight.png", this));
            White_Pieces.add(new Knight(6, 0, true, "Knight.png", this));
            White_Pieces.add(new Rook(0, 0, true, "Rook.png", this));
            White_Pieces.add(new Rook(7, 0, true, "Rook.png", this));
            White_Pieces.add(new Pawn(0, 1, true, "Pawn.png", this));
            White_Pieces.add(new Pawn(1, 1, true, "Pawn.png", this));
            White_Pieces.add(new Pawn(2, 1, true, "Pawn.png", this));
            White_Pieces.add(new Pawn(3, 1, true, "Pawn.png", this));
            White_Pieces.add(new Pawn(4, 1, true, "Pawn.png", this));
            White_Pieces.add(new Pawn(5, 1, true, "Pawn.png", this));
            White_Pieces.add(new Pawn(6, 1, true, "Pawn.png", this));
            White_Pieces.add(new Pawn(7, 1, true, "Pawn.png", this));


            Black_Pieces.add(new King(3, 7, false, "King.png", this));
            Black_Pieces.add(new Queen(4, 7, false, "Queen.png", this));
            Black_Pieces.add(new Bishop(2, 7, false, "Bishop.png", this));
            Black_Pieces.add(new Bishop(5, 7, false, "Bishop.png", this));
            Black_Pieces.add(new Knight(1, 7, false, "Knight.png", this));
            Black_Pieces.add(new Knight(6, 7, false, "Knight.png", this));
            Black_Pieces.add(new Rook(0, 7, false, "Rook.png", this));
            Black_Pieces.add(new Rook(7, 7, false, "Rook.png", this));
            Black_Pieces.add(new Pawn(0, 6, false, "Pawn.png", this));
            Black_Pieces.add(new Pawn(1, 6, false, "Pawn.png", this));
            Black_Pieces.add(new Pawn(2, 6, false, "Pawn.png", this));
            Black_Pieces.add(new Pawn(3, 6, false, "Pawn.png", this));
            Black_Pieces.add(new Pawn(4, 6, false, "Pawn.png", this));
            Black_Pieces.add(new Pawn(5, 6, false, "Pawn.png", this));
            Black_Pieces.add(new Pawn(6, 6, false, "Pawn.png", this));
            Black_Pieces.add(new Pawn(7, 6, false, "Pawn.png", this));

            whiteKing = (King) getPiece(3, 0);
            blackKing = (King) getPiece(3, 7);
        }

        Moves = new ArrayList<>();
        fullMoveCounter = 1;
        halfMoveCounter = 0;
        prevHalfMoveCounter = 0;
    }

    public Board(boolean isAgainstEngine, boolean loadedGame, boolean isWhitePerspective){
        BoardGrid = new Integer[rows][cols];
        Static_Shapes = new ArrayList();
        Piece_Graphics = new ArrayList();
        White_Pieces = new ArrayList();
        Black_Pieces = new ArrayList();
        this.isAgainstEngine = isAgainstEngine;

        if(this.isAgainstEngine){
            loadGrid(true);
        }else{
            loadGrid(false);
        }


        this.setBackground(new Color(0x6495ed));
        this.setPreferredSize(new Dimension(560, 560));
        this.setMinimumSize(new Dimension(100, 100));
        this.setMaximumSize(new Dimension(1000, 1000));

        this.addMouseListener(mouseAdapter);
        this.addComponentListener(componentAdapter);
        this.addKeyListener(keyAdapter);


        this.setVisible(true);
        this.requestFocus();
        drawBoard();

//        lowerClock = new ChessClock();
//        lowerClock.setLocation(20, 580);
//        upperClock = new ChessClock();

//        this.add("UpperClock", upperClock);
//        this.add("LowerClock", lowerClock);

        clock = new GameClock(this);
        clock.setSize(new Dimension(200, 80));
        clock.setLocation(new Point(0, 560));
        this.add(clock);

        undoBtn = new JButton("Undo Move");
        undoBtn.setBounds(150, 580, 100, 40);
        undoBtn.setFocusable(false);
        undoBtn.setBackground(Color.BLACK);
        undoBtn.setForeground(Color.WHITE);

        undoBtnHandler = new UndoBtnHandler();
        undoBtn.addActionListener(undoBtnHandler);

        this.add(undoBtn);

        saveBtn = new JButton("Save Game");
        saveBtn.setBounds(320, 580, 100, 40);
        saveBtn.setFocusable(false);
        saveBtn.setBackground(Color.BLACK);
        saveBtn.setForeground(Color.WHITE);

        saveBtnHandler = new SaveBtnHandler();
        saveBtn.addActionListener(saveBtnHandler);

        this.add(saveBtn);
    }

    public Board(boolean isAgainstEngine, boolean isWhitePerspective, ImagePanel panel, MovePanel movePanel, int level) {

        BoardGrid = new Integer[rows][cols];
        Static_Shapes = new ArrayList();
        Piece_Graphics = new ArrayList();
        White_Pieces = new ArrayList();
        Black_Pieces = new ArrayList();
        this.isAgainstEngine = isAgainstEngine;
        this.isWhitePerspective = isWhitePerspective;
        this.panel = panel;
        this.skillLevel = level;

        this.movePanel = movePanel;
        initGrid(isWhitePerspective);

        this.setBackground(new Color(0x6495ed));
        this.setPreferredSize(new Dimension(560, 560));
        this.setMinimumSize(new Dimension(100, 100));
        this.setMaximumSize(new Dimension(1000, 1000));

        this.addMouseListener(mouseAdapter);
        this.addComponentListener(componentAdapter);
        this.addKeyListener(keyAdapter);


        this.setVisible(true);
        this.requestFocus();
        drawBoard();

//        ChessClock2 chessClock2 = new ChessClock2(ChessClock2.Player.LEFT);
//        Instant start = chessClock2.gameStart();
//
//        lowerClock = new ChessClock();
//        lowerClock.setLocation(550, 580);
//        lowerClock.minimumSize();
//        upperClock = new ChessClock();
//
//        this.add("UpperClock", upperClock);
//        this.add("LowerClock", lowerClock);

        clock = new GameClock(this);
        clock.setSize(new Dimension(200, 80));
        clock.setLocation(new Point(0, 560));
        this.add(clock);

        undoBtn = new JButton("Undo Move");
        undoBtn.setBounds(250, 580, 100, 40);
        undoBtn.setFocusable(false);
        undoBtn.setBackground(Color.BLACK);
        undoBtn.setForeground(Color.WHITE);

        undoBtnHandler = new UndoBtnHandler();
        undoBtn.addActionListener(undoBtnHandler);

        this.add(undoBtn);

        saveBtn = new JButton("Back to Menu");
        saveBtn.setBounds(400, 580, 120, 40);
        saveBtn.setFocusable(false);
        saveBtn.setBackground(Color.BLACK);
        saveBtn.setForeground(Color.WHITE);

        saveBtnHandler = new SaveBtnHandler();
        saveBtn.addActionListener(saveBtnHandler);

        this.add(saveBtn);

        if(isAgainstEngine){
            stockfish = new Stockfish();
            stockfish.startEngine();
            stockfish.sendCommand("uci");
            stockfish.sendCommand("ucinewgame");
            stockfish.sendCommand("setoption name Skill Level value " + skillLevel);
            if(!isWhitePerspective) {
                doEngineMove();
            }
        }
    }
    class SaveBtnHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            saveGame(isAgainstEngine);
            panel.setVisible(true);
        }
    }

    class UndoBtnHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!Moves.isEmpty()){
                displayedMessage = false;
                Move lastMove = Moves.get(Moves.size()-1);
                Piece lastMovedPiece = lastMove.getMovedPiece();
                Piece capturedPiece = lastMove.getCapturedPiece();
                Class<? extends Piece> lastMovedPieceClass = lastMovedPiece.getClass();

                if(lastMovedPiece.isBlack()){
                    fullMoveCounter--;
                }

                if(lastMovedPieceClass.equals(Pawn.class) || lastMove.getCapturedPiece() != null){
                    halfMoveCounter = prevHalfMoveCounter;
                }else{
                    halfMoveCounter--;
                }

                if(lastMovedPieceClass.equals(Pawn.class)){
                    Pawn castedPawn = (Pawn)lastMovedPiece;
                    if (castedPawn.getIsFirstMove()){ castedPawn.setHasMoved(false);}
                    if (lastMove.isEnPassant(board, lastMove)) {castedPawn.setIsFirstMove(false);}
                }else if(lastMovedPieceClass.equals(Rook.class)){
                    Rook castedRook = (Rook)lastMovedPiece;
                    if (castedRook.getIsFirstMove()){ castedRook.setHasMoved(false);}
                }else if(lastMovedPieceClass.equals(King.class)){
                    King castedKing = (King)lastMovedPiece;
                    if (castedKing.getIsFirstMove()){ castedKing.setHasMoved(false);}
                    if(castedKing.getIsCastleMove()){
                        castedKing.setIsCastleMove(false);
                        castedKing.setHasMoved(false);
                        if(castedKing.getX() == 6){
                            Rook kingRook = (Rook)getPiece(5, castedKing.getY());
                            kingRook.setHasMoved(false);
                            kingRook.setX(7);
                            kingRook.setY(castedKing.getY());
                        }else if(castedKing.getX() == 2){
                            Rook queenRook = (Rook)getPiece(3, castedKing.getY());
                            queenRook.setHasMoved(false);
                            queenRook.setX(0);
                            queenRook.setY(castedKing.getY());
                        }
                    }
                }

                if(capturedPiece != null){
                    if(capturedPiece.isWhite()){
                        White_Pieces.add(capturedPiece);
                    }else{
                        Black_Pieces.add(capturedPiece);
                    }

                    // if(!lastMove.isEnPassantCapture(board)){
                    capturedPiece.setX(lastMove.getFinalSpot().getX());
                    capturedPiece.setY(lastMove.getFinalSpot().getY());
//                    }else{
//                        Move prevMove = Moves.get(Moves.size()-2);
//                        capturedPiece.setX(prevMove.getFinalSpot().getX());
//                        capturedPiece.setY(prevMove.getFinalSpot().getY());
//                    }
                }

                lastMovedPiece.setX(lastMove.getInitialSpot().getX());
                lastMovedPiece.setY(lastMove.getInitialSpot().getY());

                Moves.remove(lastMove);
                turnCounter++;
                clock.switchClocks();
                drawBoard();
            }
        }
    }

    public void drawBoard()
    {
        Piece_Graphics.clear();
        Static_Shapes.clear();
        Image active_square = loadImage(active_square_file_path);
        //initGrid();
        Image board;

        board = isWhitePerspective ? loadImage(board_white_perspective_file_path) : loadImage(board_black_perspective_file_path);

        Static_Shapes.add(new DrawingImage(board, new Rectangle2D.Double(0, 0, board.getWidth(null), board.getHeight(null))));
        if(whiteKing.isCheck()){
            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*whiteKing.getX(),Square_Width*whiteKing.getY(), active_square.getWidth(null), active_square.getHeight(null))));
        }else if(blackKing.isCheck()){
            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*blackKing.getX(),Square_Width*blackKing.getY(), active_square.getWidth(null), active_square.getHeight(null))));
        }
        if (Active_Piece != null)
        {
            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Active_Piece.getX(),Square_Width*Active_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
            Active_Piece.moves.forEach((move) -> Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*move.getX(),Square_Width*move.getY(), active_square.getWidth(null), active_square.getHeight(null)))));
        }

        for (Piece white_piece : White_Pieces) {
            int COL = white_piece.getX();
            int ROW = white_piece.getY();
            Image piece = loadImage("images" + File.separator + "white_pieces" + File.separator + white_piece.getFilePath());
            Piece_Graphics.add(new DrawingImage(piece, new Rectangle2D.Double(Square_Width * COL + 10, Square_Width * ROW + 10, piece.getWidth(null), piece.getHeight(null))));
        }
        for (Piece black_piece : Black_Pieces) {
            int COL = black_piece.getX();
            int ROW = black_piece.getY();
            Image piece = loadImage("images" + File.separator + "black_pieces" + File.separator + black_piece.getFilePath());
            Piece_Graphics.add(new DrawingImage(piece, new Rectangle2D.Double(Square_Width * COL + 10, Square_Width * ROW + 10, piece.getWidth(null), piece.getHeight(null))));
        }

        this.repaint();
        displayedMessage = checkResult();

    }

    public void resetBoard(){

        White_Pieces.clear();
        Black_Pieces.clear();
        Moves.clear();
        this.clock = new GameClock(this);
        clock.setSize(new Dimension(200, 80));
        clock.setLocation(new Point(0, 560));
        this.add(clock);

        initGrid(isWhitePerspective);

    }

    public boolean checkResult(){
        String message = "", header = "";
        Object[] options = {"Play Again","Close",};
        int n;
        boolean status = false;


        if(whiteKing.isCheckmate() || blackKing.isCheckmate()){
            message = (whiteKing.isCheckmate())?"Black Wins!":"White Wins!";
            header = "Checkmate";
            status = true;
        }else if(whiteKing.isDraw() || blackKing.isDraw()){
            message = "Stalemate";
            header = "Draw";
            status = true;
        }
        if(clock != null){
            String time = clock.timeLeft();
            if(time != "none"){
                message = time;
                header = "Ran out of time";
                status = true;
            }
        }

        if(status && !displayedMessage){
            n = JOptionPane.showOptionDialog(this, message, header, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,null, options, null);
            if(n==0){
                resetBoard();
                drawBoard();
                return false;
            }
        }

        return status;

    }

    public void promotePawn(Pawn promotedPawn){

        int ndx, x, y;

        if(promotedPawn.isWhite()){
            ndx = board.White_Pieces.indexOf(promotedPawn);
        }else{
            ndx = board.Black_Pieces.indexOf(promotedPawn);
        }

        x = promotedPawn.getX();
        y = promotedPawn.getY();

        Object[] options = {"Bishop", "Knight", "Rook", "Queen"};
        int n = JOptionPane.showOptionDialog(this,
                "Promote pawn to: ",
                "Pawn Promotion",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                null);

        switch (n) {
            case 0 -> {
                if(promotedPawn.isWhite()){
                    board.White_Pieces.set(ndx, new Bishop(x, y, true, "Bishop.png", board));
                }else{
                    board.Black_Pieces.set(ndx, new Bishop(x, y, false, "Bishop.png", board));
                }
            }
            case 1 -> {
                if(promotedPawn.isWhite()){
                    board.White_Pieces.set(ndx, new Knight(x, y, true, "Knight.png", board));
                }else{
                    board.Black_Pieces.set(ndx, new Knight(x, y, false, "Knight.png", board));
                }
            }
            case 2 -> {
                if(promotedPawn.isWhite()){
                    board.White_Pieces.set(ndx, new Rook(x, y, true, "Rook.png", board));
                }else{
                    board.Black_Pieces.set(ndx, new Rook(x, y, false, "Rook.png", board));
                }
            }
            case 3 -> {
                if(promotedPawn.isWhite()){
                    board.White_Pieces.set(ndx, new Queen(x, y, true, "Queen.png", board));
                }else{
                    board.Black_Pieces.set(ndx, new Queen(x, y, false, "Queen.png", board));
                }
            }
        }
        board.drawBoard();
    }


    public Piece getPiece(int x, int y) {
        for (Piece p : White_Pieces)
        {
            if (p.getX() == x && p.getY() == y)
            {
                return p;
            }
        }
        for (Piece p : Black_Pieces)
        {
            if (p.getX() == x && p.getY() == y)
            {
                return p;
            }
        }
        return null;
    }

    public void saveGame(boolean isAgainstEngine){
        try{
            FileWriter saveWrite;
            if(isAgainstEngine){
                saveWrite = new FileWriter("save2.dat");
            }else{
                saveWrite = new FileWriter("save.dat");
            }
            saveWrite.write(getFen());
            saveWrite.close();
            System.out.println("successfully wrote to the file");
            clock.stop();
        }catch(IOException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }


    public void loadGame(boolean isAgainstEngine){
        String data = null;
        Scanner saveReader = null;
        int lastNdx = 0;
        char charAtLastNdx;
        StringBuffer castlingRights = new StringBuffer();
        StringBuffer moveCtr = new StringBuffer();
        String stringCastlingRights;

        try{
            if(isAgainstEngine){
                File saveFile = new File("save2.dat");
                saveReader = new Scanner(saveFile);
                while(saveReader.hasNextLine()){
                    data = saveReader.nextLine();
                }
            }else{
                File saveFile = new File("save.dat");
                saveReader = new Scanner(saveFile);
                while(saveReader.hasNextLine()){
                    data = saveReader.nextLine();
                }
            }
            saveReader.close();
        } catch(FileNotFoundException e){
            System.out.println("An error has occurred");
            e.printStackTrace();
        }

        //set pieces
        outerloop:
        for(int r = 0; r <= 7; r++){
            for(int f = 0; f <= 7; f++){
                charAtLastNdx = data.charAt(lastNdx);
                if(Character.isUpperCase(charAtLastNdx)){
                    switch (charAtLastNdx) {
                        case 'R' -> {
                            White_Pieces.add(new Rook(f, r, true, "Rook.png", this));
//                            System.out.println("Created R at " + f + " and " + r);
                        }
                        case 'N' -> {
                            White_Pieces.add(new Knight(f, r, true, "Knight.png", this));
//                            System.out.println("Created N at " + f + " and " + r);
                        }
                        case 'B' -> {
                            White_Pieces.add(new Bishop(f, r, true, "Bishop.png", this));
//                            System.out.println("Created B at " + f + " and " + r);
                        }
                        case 'Q' -> {
                            White_Pieces.add(new Queen(f, r, true, "Queen.png", this));
//                            System.out.println("Created Q at " + f + " and " + r);
                        }
                        case 'K' -> {
                            White_Pieces.add(new King(f, r, true, "King.png", this));
//                            System.out.println("Created K at " + f + " and " + r);
                            this.whiteKing = (King) White_Pieces.get(White_Pieces.size() - 1);
                        }
                        case 'P' -> {
                            White_Pieces.add(new Pawn(f, r, true, "Pawn.png", this));
//                            System.out.println("Created P at " + f + " and " + r);

                            Pawn piece = (Pawn) White_Pieces.get(White_Pieces.size()-1);
                            if( (isWhitePerspective && piece.getY() != 6) || (piece.getY() != 1 && !isWhitePerspective) ){
                                piece.setHasMoved(true);
                            }                     }
                    }
                    lastNdx++;
                }else if(Character.isLowerCase(data.charAt(lastNdx))){
                    switch (data.charAt(lastNdx)) {
                        case 'r' -> {
                            Black_Pieces.add(new Rook(f, r, false, "Rook.png", this));
//                            System.out.println("Created r at" + f + " and " + r);
                        }
                        case 'n' -> {
                            Black_Pieces.add(new Knight(f, r, false, "Knight.png", this));
//                            System.out.println("Created n at" + f + " and " + r);
                        }
                        case 'b' -> {
                            Black_Pieces.add(new Bishop(f, r, false, "Bishop.png", this));
//                            System.out.println("Created b at" + f + " and " + r);
                        }
                        case 'q' -> {
                            Black_Pieces.add(new Queen(f, r, false, "Queen.png", this));
//                            System.out.println("Created q at" + f + " and " + r);
                        }
                        case 'k' -> {
                            Black_Pieces.add(new King(f, r, false, "King.png", this));
//                            System.out.println("Created k at" + f + " and " + r);
                            this.blackKing = (King) Black_Pieces.get(Black_Pieces.size() - 1);
                        }
                        case 'p' -> {
                            Black_Pieces.add(new Pawn(f, r, false, "Pawn.png", this));
//                            System.out.println("Created p at" + f + " and " + r);

                            Pawn piece = (Pawn) Black_Pieces.get(Black_Pieces.size()-1);
                            if( (isWhitePerspective && piece.getY() != 1) || (piece.getY() != 6 && !isWhitePerspective) ){
                                piece.setHasMoved(true);
                            }
                        }
                    }
                    lastNdx++;
                }else if(data.charAt(lastNdx)== ' '){
                    break outerloop;
                }
                else{
                    if(charAtLastNdx == '/'){
                        f = -1;
                    }else{
                        int emptySpaces = Character.getNumericValue(charAtLastNdx);
                        f += (emptySpaces - 1);
                    }
                    lastNdx++;
                }
            }
        }

        lastNdx++;

        //set turn
        if(data.charAt(lastNdx) == 'w'){
            System.out.println("White turn");
            turnCounter = 0;
            clock.setClockSide("w");
            clock.run();
        }else{
            System.out.println("Black turn");
            turnCounter = 1;
            clock.setClockSide("b");
            clock.run();
        }
        lastNdx += 2;

        //castling rights
        while(data.charAt(lastNdx) != ' '){
            castlingRights.append(data.charAt(lastNdx));
            lastNdx++;
        }

        stringCastlingRights = castlingRights.toString();
        if(stringCastlingRights.contains("-")){
            whiteKing.setHasMoved(true);
            whiteKing.setHasQSideCastlingRights(false);
            whiteKing.setHasKSideCastlingRights(false);
            blackKing.setHasMoved(true);
            blackKing.setHasQSideCastlingRights(false);
            blackKing.setHasKSideCastlingRights(false);
        }else{
            if(!stringCastlingRights.contains("Q")) {
                whiteKing.setHasQSideCastlingRights(false);
            }
            if(!stringCastlingRights.contains("K")) {
                whiteKing.setHasKSideCastlingRights(false);
            }
            if(!stringCastlingRights.contains("q")) {
                blackKing.setHasQSideCastlingRights(false);
            }
            if(!stringCastlingRights.contains("k")) {
                blackKing.setHasKSideCastlingRights(false);
            }
        }

        lastNdx++;

        //en passant target
        if(data.charAt(lastNdx) == '-'){
            lastNdx+=2;
        }else{
            lastNdx+=3;
        }

        //half move
        while(data.charAt(lastNdx) != ' '){
            moveCtr.append(data.charAt(lastNdx));
            lastNdx++;
        }
        this.halfMoveCounter = Integer.parseInt(moveCtr.toString());
        System.out.println("HALFMOVE: " + halfMoveCounter);

        lastNdx++;

        //full move
        moveCtr = new StringBuffer();
        while(lastNdx < data.length()){
            moveCtr.append(data.charAt(lastNdx));
            lastNdx++;
        }
        this.fullMoveCounter = Integer.parseInt(moveCtr.toString());
        System.out.println("FULLMOVE: " + fullMoveCounter);

        //change board perspective to what is saved
        this.isWhitePerspective = true;
        this.drawBoard();

        if(isAgainstEngine){
            stockfish = new Stockfish();
            stockfish.startEngine();
            stockfish.sendCommand("uci");
            stockfish.sendCommand("ucinewgame");
            stockfish.sendCommand("setoption name Skill Level value " + skillLevel);
            if(!isWhitePerspective) {
                doEngineMove();
            }
        }


    }

    public String getFen(){
        StringBuffer fen = new StringBuffer();

        int nullCtr = 0;
        int r, f;
        boolean invalidWhiteKCastle = true;
        boolean invalidWhiteQCastle = true;
        boolean invalidBlackKCastle = true;
        boolean invalidBlackQCastle = true;


        //FEN piece placement
        if(isWhitePerspective){
            //rank
            for(r = 0; r <= 7; r++){
                //file
                for(f = 0; f <= 7; f++){
                    Piece piece = getPiece(f, r);
                    if(piece != null){
                        if(nullCtr != 0){fen.append(nullCtr);}
                        if(piece.isWhite()){
                            fen.append(Character.toUpperCase(piece.getAbbrev()));
                        }else{
                            fen.append(piece.getAbbrev());
                        }
                        nullCtr = 0;
                    }else{
                        nullCtr++;
                    }
                }
                if(nullCtr != 0){ fen.append(nullCtr);}
                if(r < 7){
                    fen.append('/');
                }
                nullCtr = 0;
            }
        }else{
            //rank
            for(r = 7; r > -1; r--){
                //file
                for(f = 7; f > -1; f--){
                    Piece piece = getPiece(f, r);
                    if(piece != null){
                        if(nullCtr != 0){fen.append(nullCtr);}
                        if(piece.isWhite()){
                            fen.append(Character.toUpperCase(piece.getAbbrev()));
                        }else{
                            fen.append(piece.getAbbrev());
                        }
                        nullCtr = 0;
                    }else{
                        nullCtr++;
                    }
                }
                if(nullCtr != 0){ fen.append(nullCtr);}
                if(r > 0){
                    fen.append('/');
                }
                nullCtr = 0;
            }
        }


        //FEN who moves next
        if(turnCounter % 2 != 1){
            //white's turn
            fen.append(" w ");
        }else{
            //black's turn
            fen.append(" b ");
        }

        //FEN castling rights
        if(whiteKing.getHasKSideCastlingRights()){
            fen.append("K");
            invalidWhiteKCastle = false;
        }
        if(whiteKing.getHasQSideCastlingRights()){
            fen.append("Q");
            invalidWhiteQCastle = false;
        }

        if(blackKing.getHasKSideCastlingRights()){
            fen.append("k");
            invalidBlackKCastle = false;
        }
        if(blackKing.getHasQSideCastlingRights()){
            fen.append("q");
            invalidBlackQCastle = false;
        }

        if(invalidWhiteQCastle && invalidWhiteKCastle && invalidBlackQCastle && invalidBlackKCastle){
            fen.append("-");
        }

        //FEN possible En Passant targets
        if(!Moves.isEmpty()) {
            Move lastMove = Moves.get(Moves.size() - 1);
            if (lastMove.getMovedPiece().getClass().equals(Pawn.class) &&
                    Math.abs(lastMove.getFinalSpot().getY() - lastMove.getInitialSpot().getY()) == 2) {
                fen.append(" ");
                if (lastMove.getMovedPiece().isWhite()) {
                    fen.append(lastMove.getFinalSpot().getXLabel()).append(lastMove.getFinalSpot().getYLabel() - 1);
                } else {
                    fen.append(lastMove.getFinalSpot().getXLabel()).append(lastMove.getFinalSpot().getYLabel() + 1);
                }
            } else {
                fen.append(" -");
            }
        }else{
            fen.append(" -");
        }

        //FEN half move clock
        fen.append(" ").append(halfMoveCounter);

        //FEN full move counter
        fen.append(" ").append(fullMoveCounter);

        System.out.println(fen);
        return fen.toString();
    }

    public void doEngineMove(){
        String bestMove = stockfish.getBestMove(getFen(), 20);
        System.out.println("BEST MOVE: " + bestMove);

        Spot initialSpot = convertUci(bestMove.substring(0, 2));
        Spot finalSpot = convertUci(bestMove.substring(2, 4));
        Piece movedPiece = getPiece(initialSpot.getX(), initialSpot.getY());
        Piece capturedPiece = getPiece(finalSpot.getX(), finalSpot.getY());

        Moves.add(new Move(movedPiece, capturedPiece, initialSpot, finalSpot, this));

        //castling
        if (movedPiece.getClass().equals(King.class) && ( Math.abs(initialSpot.getX() - finalSpot.getX()) == 2 )) {
            Rook castleRook;
            if (initialSpot.getX() > finalSpot.getX()) {
                castleRook = (Rook) getPiece(0, finalSpot.getY());
                castleRook.setX(finalSpot.getX() + 1);
            } else {
                castleRook = (Rook) getPiece(7, finalSpot.getY());
                castleRook.setX(finalSpot.getX() - 1);
            }
            ((King) movedPiece).setHasMoved(true);
        }

        //promotion
        if (bestMove.length() > 4) {
            char promotion = bestMove.charAt(4);
            switch (promotion) {
                case 'b' -> {
                    if (movedPiece.isWhite()) {
                        White_Pieces.remove(movedPiece);
                        White_Pieces.add(new Bishop(finalSpot.getX(), finalSpot.getY(), true, "Bishop.png", this));
                    } else {
                        Black_Pieces.remove(movedPiece);
                        Black_Pieces.add(new Bishop(finalSpot.getX(), finalSpot.getY(), false, "Bishop.png", this));
                    }
                }
                case 'n' -> {
                    if (movedPiece.isWhite()) {
                        White_Pieces.remove(movedPiece);
                        White_Pieces.add(new Knight(finalSpot.getX(), finalSpot.getY(), true, "Knight.png", this));
                    } else {
                        Black_Pieces.remove(movedPiece);
                        Black_Pieces.add(new Knight(finalSpot.getX(), finalSpot.getY(), false, "Knight.png", this));
                    }
                }
                case 'r' -> {
                    if (movedPiece.isWhite()) {
                        White_Pieces.remove(movedPiece);
                        White_Pieces.add(new Rook(finalSpot.getX(), finalSpot.getY(), true, "Rook.png", this));
                    } else {
                        Black_Pieces.remove(movedPiece);
                        Black_Pieces.add(new Rook(finalSpot.getX(), finalSpot.getY(), false, "Rook.png", this));
                    }
                }
                case 'q' -> {
                    if (movedPiece.isWhite()) {
                        White_Pieces.remove(movedPiece);
                        White_Pieces.add(new Queen(finalSpot.getX(), finalSpot.getY(), true, "Queen.png", this));
                    } else {
                        Black_Pieces.remove(movedPiece);
                        Black_Pieces.add(new Queen(finalSpot.getX(), finalSpot.getY(), false, "Queen.png", this));
                    }
                }
            }
        }

        //normal capture
        if (capturedPiece != null) {
            if (capturedPiece.isWhite()) {
                White_Pieces.remove(capturedPiece);
            } else {
                Black_Pieces.remove(capturedPiece);
            }
        }

        // en passant capture
        int lastNdx = Moves.size() - 1;
        if (Moves.get(lastNdx).isEnPassantCapture(board)) {
            Piece prevMovedPiece = Moves.get(lastNdx - 1).getMovedPiece();
            if (prevMovedPiece.isWhite()) {
                White_Pieces.remove(prevMovedPiece);
            } else {
                Black_Pieces.remove(prevMovedPiece);
            }
            Moves.get(lastNdx).setCapturedPiece(prevMovedPiece);
        }

        movedPiece.setX(finalSpot.getX());
        movedPiece.setY(finalSpot.getY());

        if (movedPiece.getClass().equals(Pawn.class) || capturedPiece != null) {
            prevHalfMoveCounter = halfMoveCounter;
            halfMoveCounter = 0;
        } else {
            halfMoveCounter++;
        }

        fullMoveCounter++;
        turnCounter++;
        clock.switchClocks();
        drawBoard();
    }

    public Spot convertUci(String uciSpot){
        int convertedX;
        int convertedY;
        if (isWhitePerspective) {
            convertedX = Math.abs(uciSpot.charAt(0) - 'a');
            //56 is decimal equivalent of char 8
            convertedY = Math.abs(uciSpot.charAt(1) - 56);
        }else{
            convertedX =  Math.abs(uciSpot.charAt(0) - 'h');
            convertedY =  Character.getNumericValue(uciSpot.charAt(1)) - 1;
        }
        return new Spot(convertedX, convertedY, isWhitePerspective);
    }

    private MouseAdapter mouseAdapter = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {


        }

        @Override
        public void mousePressed(MouseEvent e) {
            int d_X = e.getX();
            int d_Y = e.getY();
            int Clicked_Row = d_Y / Square_Width;
            int Clicked_Column = d_X / Square_Width;
            boolean is_whites_turn = turnCounter % 2 != 1;
            int movesLastNdx;
            Piece movedPiece, lastCapturedPiece;
            Move lastMove;

            Piece clicked_piece = getPiece(Clicked_Column, Clicked_Row);

            if (Active_Piece == null && clicked_piece != null &&
                    ( (!isAgainstEngine && is_whites_turn == clicked_piece.isWhite() )
                            || (isAgainstEngine && is_whites_turn == isWhitePerspective && clicked_piece.isWhite() == isWhitePerspective)
                    )) {
                Active_Piece = clicked_piece;
                Active_Piece.availableMoves(Active_Piece.getX(), Active_Piece.getY());

            } else if (Active_Piece != null && Active_Piece.getX() == Clicked_Column && Active_Piece.getY() == Clicked_Row) {
                Active_Piece = null;

            } else if (Active_Piece != null && Active_Piece.canMove(Clicked_Column, Clicked_Row) &&
                    ((!isAgainstEngine && is_whites_turn == Active_Piece.isWhite())
                            || (isAgainstEngine && is_whites_turn == isWhitePerspective)
                    )) {

                // availMoves = Active_Piece.availableMoves(Active_Piece.getX(), Active_Piece.getY());
                //if(availMoves.stream().anyMatch(s -> s.getX() == Clicked_Column && s.getY() == Clicked_Row)) {


                Moves.add(new Move(Active_Piece, clicked_piece, new Spot(Active_Piece.getX(), Active_Piece.getY(), isWhitePerspective), new Spot(Clicked_Column, Clicked_Row, isWhitePerspective), board));
                movesLastNdx = Moves.size()-1;
                lastMove = Moves.get(movesLastNdx);
                movedPiece = Moves.get(movesLastNdx).getMovedPiece();
                lastCapturedPiece = Moves.get(movesLastNdx).getCapturedPiece();


                if (clicked_piece != null) {
                    if (clicked_piece.isWhite()) {
                        White_Pieces.remove(clicked_piece);
                    } else {
                        Black_Pieces.remove(clicked_piece);
                    }
                }

                //full move counter increments everytime black moves, set to 1 at the start of the game
                if (movedPiece.isBlack()) {
                    fullMoveCounter++;
                }

                //half move counter is reset after captures or pawn moves
                if (movedPiece.getClass().equals(Pawn.class) || lastCapturedPiece != null) {
                    prevHalfMoveCounter = halfMoveCounter;
                    halfMoveCounter = 0;
                } else {
                    halfMoveCounter++;
                }


                //castling
                if (Active_Piece.getClass().equals(King.class)) {
                    King castedKing = (King) (Active_Piece);
                    if (Clicked_Column - Active_Piece.getX() == 2) {
                        Rook rook = (Rook) getPiece(7, Clicked_Row);
                        rook.setX(Clicked_Column - 1);
                        rook.setIsFirstMove(!rook.getHasMoved());
                        if (!castedKing.getIsCastleMove()) {
                            castedKing.setIsCastleMove(true);
                            castedKing.setIsFirstMove(true);
                        } else {
                            castedKing.setIsCastleMove(false);
                        }

                        rook.setHasMoved(true);

                    } else if (Clicked_Column - Active_Piece.getX() == -2) {
                        Rook rook = (Rook) getPiece(0, Clicked_Row);
                        rook.setX(Clicked_Column + 1);
                        rook.setIsFirstMove(!rook.getHasMoved());

                        if (!castedKing.getIsCastleMove()) {
                            castedKing.setIsCastleMove(true);
                            castedKing.setIsFirstMove(true);
                        } else {
                            castedKing.setIsCastleMove(false);
                        }
                        rook.setHasMoved(true);
                    }

                    castedKing.setIsFirstMove(!castedKing.getHasMoved());
                    if (castedKing.isWhite()) {
                        whiteKing = castedKing;
                    } else {
                        blackKing = castedKing;
                    }
                    castedKing.setHasMoved(true);
                    castedKing.setHasQSideCastlingRights(false);
                    castedKing.setHasKSideCastlingRights(false);
                }

                if (Active_Piece.getClass().equals(Rook.class) && ((Rook)Active_Piece).getHasMoved() == false) {
                    Rook castedRook = (Rook) (Active_Piece);
                    castedRook.setIsFirstMove(!castedRook.getHasMoved());
                    castedRook.setHasMoved(true);
                    if (isWhitePerspective) {
                        if (castedRook.getX() == 0) {
                            if (castedRook.getY() == 7) {
                                whiteKing.setHasQSideCastlingRights(false);
                            } else if (castedRook.getY() == 0){
                                blackKing.setHasQSideCastlingRights(false);
                            }
                        } else if (castedRook.getX() == 7){
                            if (castedRook.getY() == 7) {
                                whiteKing.setHasKSideCastlingRights(false);
                            } else if (castedRook.getY() == 0){
                                blackKing.setHasKSideCastlingRights(false);
                            }
                        }
                    }else{
                        if (castedRook.getX() == 0) {
                            if (castedRook.getY() == 7) {
                                blackKing.setHasKSideCastlingRights(false);
                            } else if (castedRook.getY() == 0){
                                whiteKing.setHasKSideCastlingRights(false);
                            }
                        } else if (castedRook.getX() == 7){
                            if (castedRook.getY() == 7) {
                                blackKing.setHasQSideCastlingRights(false);
                            } else if (castedRook.getY() == 0){
                                whiteKing.setHasQSideCastlingRights(false);
                            }
                        }
                    }
                }


                // do move
                Active_Piece.setX(Clicked_Column);
                Active_Piece.setY(Clicked_Row);

                // if piece is a pawn/rook, set piece's has_moved to true
                if (Active_Piece.getClass().equals(Pawn.class)) {
                    Pawn castedPawn = (Pawn) (Active_Piece);
                    // if pawn moved for the first time, set isFirstMove to true
                    castedPawn.setIsFirstMove(!castedPawn.getHasMoved());
                    castedPawn.setHasMoved(true);

                    //if en passant capture
                    if(lastMove.isEnPassantCapture(board)) {
                        Piece prevMovedPiece = Moves.get(movesLastNdx - 1).getMovedPiece();
                        if (prevMovedPiece.isWhite()) {
                            White_Pieces.remove(prevMovedPiece);
                        } else {
                            Black_Pieces.remove(prevMovedPiece);
                        }
                        Moves.get(movesLastNdx).setCapturedPiece(prevMovedPiece);
                    }

                }

                //if piece is pawn, check if promotable
                if(Clicked_Row == 0 || Clicked_Row == 7){
                    if(Active_Piece.getClass().equals(Pawn.class)){
                        promotePawn((Pawn)Active_Piece);
                        lastMove.setIsPromotion(true);
                    }
                }
                Active_Piece = null;
                getFen();
                turnCounter++;
                clock.switchClocks();

                //test print
                if(!Moves.isEmpty()){
                    //set notation first so each move has its own corresponding notation (should've been in constructor but canMoveChecked needs to be changed to avoid stackoverflow)
                    lastMove.setNotation(lastMove.convertToAlgebraicNotation());
                    System.out.println("NOTATION: " + lastMove.getNotation());
                    movePanel.updateMove(lastMove.getNotation(),fullMoveCounter,turnCounter);

                }

                if (isAgainstEngine && is_whites_turn == isWhitePerspective) {
                    doEngineMove();
                    //test print
                    if(!Moves.isEmpty()){
                        //set notation first so each move has its own corresponding notation (should've been in constructor but canMoveChecked needs to be changed to avoid stackoverflow)
                        Move lastEngineMove = Moves.get(Moves.size()-1);
                        lastEngineMove.setNotation(lastEngineMove.convertToAlgebraicNotation());
                        System.out.println("NOTATION: " + lastEngineMove.getNotation());
                        movePanel.updateMove(lastEngineMove.getNotation(),fullMoveCounter,turnCounter);

                    }
                }

            }

            if(!isAgainstEngine || is_whites_turn == isWhitePerspective) {
                drawBoard();
            }

        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e)
        {
        }


    };

    private void adjustShapePositions(double dx, double dy) {

        Static_Shapes.get(0).adjustPosition(dx, dy);
        this.repaint();

    }



    private Image loadImage(String imageFile) {
        try {
            return ImageIO.read(new File(imageFile));
        }
        catch (IOException e) {
            return NULL_IMAGE;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        drawBackground(g2);
        drawShapes(g2);
    }

    private void drawBackground(Graphics2D g2) {
        g2.setColor(getBackground());
        g2.fillRect(0,  0, getWidth(), getHeight());
    }


    private void drawShapes(Graphics2D g2) {
        for (DrawingShape shape : Static_Shapes) {
            shape.draw(g2);
        }
        for (DrawingShape shape : Piece_Graphics) {
            shape.draw(g2);
        }
    }

    private ComponentAdapter componentAdapter = new ComponentAdapter() {

        @Override
        public void componentHidden(ComponentEvent e) {

        }

        @Override
        public void componentMoved(ComponentEvent e) {

        }

        @Override
        public void componentResized(ComponentEvent e) {

        }

        @Override
        public void componentShown(ComponentEvent e) {

        }
    };

    private KeyAdapter keyAdapter = new KeyAdapter() {

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }
    };

}



interface DrawingShape {
    boolean contains(Graphics2D g2, double x, double y);
    void adjustPosition(double dx, double dy);
    void draw(Graphics2D g2);
}


class DrawingImage implements DrawingShape {

    public Image image;
    public Rectangle2D rect;

    public DrawingImage(Image image, Rectangle2D rect) {
        this.image = image;
        this.rect = rect;
    }

    @Override
    public boolean contains(Graphics2D g2, double x, double y) {
        return rect.contains(x, y);
    }

    @Override
    public void adjustPosition(double dx, double dy) {
        rect.setRect(rect.getX() + dx, rect.getY() + dy, rect.getWidth(), rect.getHeight());
    }

    @Override
    public void draw(Graphics2D g2) {
        Rectangle2D bounds = rect.getBounds2D();
        g2.drawImage(image, (int)bounds.getMinX(), (int)bounds.getMinY(), (int)bounds.getMaxX(), (int)bounds.getMaxY(),
                0, 0, image.getWidth(null), image.getHeight(null), null);
    }

}