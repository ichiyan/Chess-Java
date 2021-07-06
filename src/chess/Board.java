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

public class Board extends JComponent {

    public boolean isAgainstEngine;
    public Stockfish stockfish;
    public int turnCounter = 0;
    public int fullMoveCounter = 0;
    public int halfMoveCounter = 0;
    public int prevHalfMoveCounter = 0;  //stores halfMoveCounter before reset in case of undo
    private static Image NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
    public boolean displayedMessage = false;

    private final int Square_Width = 65;
    public Board board = this;
    public ArrayList<Piece> White_Pieces;
    public ArrayList<Piece> Black_Pieces;
    

    public King whiteKing;
    public King blackKing;
    public ArrayList<Move> Moves;

    public ArrayList<DrawingShape> Static_Shapes;
    public ArrayList<DrawingShape> Piece_Graphics;

    public Piece Active_Piece;

    private final int rows = 8;
    private final int cols = 8;
    public Integer[][] BoardGrid;
    private String board_file_path = "images" + File.separator + "board3.png";
    private String active_square_file_path = "images" + File.separator + "active_square.png";

    JButton undoBtn;
    UndoBtnHandler undoBtnHandler;
    JButton saveBtn;
    SaveBtnHandler saveBtnHandler;

    public void loadGrid(){

        System.out.println(White_Pieces.size());

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                BoardGrid[i][j] = 0;
            }
        }

        loadGame();
        Moves = new ArrayList<>();
        fullMoveCounter = 0;
        halfMoveCounter = 0;
        prevHalfMoveCounter = 0;
    }

    public void initGrid()
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

        White_Pieces.add(new King(4,7,true,"King.png", this));
        White_Pieces.add(new Queen(3,7,true,"Queen.png",this));
        White_Pieces.add(new Bishop(2,7,true,"Bishop.png",this));
        White_Pieces.add(new Bishop(5,7,true,"Bishop.png",this));
        White_Pieces.add(new Knight(1,7,true,"Knight.png",this));
        White_Pieces.add(new Knight(6,7,true,"Knight.png",this));
        White_Pieces.add(new Rook(0,7,true,"Rook.png",this));
        White_Pieces.add(new Rook(7,7,true,"Rook.png",this));
        White_Pieces.add(new Pawn(0,6,true,"Pawn.png",this));
        White_Pieces.add(new Pawn(1,6,true,"Pawn.png",this));
        White_Pieces.add(new Pawn(2,6,true,"Pawn.png",this));
        White_Pieces.add(new Pawn(3,6,true,"Pawn.png",this));
        White_Pieces.add(new Pawn(4,6,true,"Pawn.png",this));
        White_Pieces.add(new Pawn(5,6,true,"Pawn.png",this));
        White_Pieces.add(new Pawn(6,6,true,"Pawn.png",this));
        White_Pieces.add(new Pawn(7,6,true,"Pawn.png",this));


        Black_Pieces.add(new King(4,0,false,"King.png",this));
        Black_Pieces.add(new Queen(3,0,false,"Queen.png",this));
        Black_Pieces.add(new Bishop(2,0,false,"Bishop.png",this));
        Black_Pieces.add(new Bishop(5,0,false,"Bishop.png",this));
        Black_Pieces.add(new Knight(1,0,false,"Knight.png",this));
        Black_Pieces.add(new Knight(6,0,false,"Knight.png",this));
        Black_Pieces.add(new Rook(0,0,false,"Rook.png",this));
        Black_Pieces.add(new Rook(7,0,false,"Rook.png",this));
        Black_Pieces.add(new Pawn(0,1,false,"Pawn.png",this));
        Black_Pieces.add(new Pawn(1,1,false,"Pawn.png",this));
        Black_Pieces.add(new Pawn(2,1,false,"Pawn.png",this));
        Black_Pieces.add(new Pawn(3,1,false,"Pawn.png",this));
        Black_Pieces.add(new Pawn(4,1,false,"Pawn.png",this));
        Black_Pieces.add(new Pawn(5,1,false,"Pawn.png",this));
        Black_Pieces.add(new Pawn(6,1,false,"Pawn.png",this));
        Black_Pieces.add(new Pawn(7,1,false,"Pawn.png",this));

        whiteKing = (King)getPiece(4, 7);
        blackKing = (King)getPiece(4,0);

        Moves = new ArrayList<>();
        fullMoveCounter = 0;
        halfMoveCounter = 0;
        prevHalfMoveCounter = 0;
    }
    public Board(boolean isAgainstEngine, boolean loadedGame){
        BoardGrid = new Integer[rows][cols];
        Static_Shapes = new ArrayList();
        Piece_Graphics = new ArrayList();
        White_Pieces = new ArrayList();
        Black_Pieces = new ArrayList();
        this.isAgainstEngine = isAgainstEngine;

        loadGrid();

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

        undoBtn = new JButton("Undo Move");
        undoBtn.setBounds(220, 580, 100, 40);
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

    public Board(boolean isAgainstEngine) {

        BoardGrid = new Integer[rows][cols];
        Static_Shapes = new ArrayList();
        Piece_Graphics = new ArrayList();
        White_Pieces = new ArrayList();
        Black_Pieces = new ArrayList();
        this.isAgainstEngine = isAgainstEngine;

        initGrid();

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

        undoBtn = new JButton("Undo Move");
        undoBtn.setBounds(220, 580, 100, 40);
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

        // if(isAgainstEngine){
        //     stockfish = new Stockfish();
        //     stockfish.startEngine();
        //     stockfish.sendCommand("uci");
        //     stockfish.sendCommand("ucinewgame");
        // }


    }
    class SaveBtnHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            saveGame();
        }
    }

    class UndoBtnHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!Moves.isEmpty()){
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
        
        Image board = loadImage(board_file_path);
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
        if(whiteKing.isCheckmate() || blackKing.isCheckmate()){
            String message = (whiteKing.isCheckmate())?"Black Wins!":"White Wins!";
            Object[] options = {"Play Again",
            "Close",};
            if(!displayedMessage){
                int n = JOptionPane.showOptionDialog(null, message, "Checkmate", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,null, options, null);
                if(n==0){
                    resetBoard();
                    drawBoard();
                }
            }
            displayedMessage = true;   
        }
            
        
    }
    public void resetBoard(){
        White_Pieces.clear();
        Black_Pieces.clear();
        initGrid();
        
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
    public void saveGame(){
        try{
            FileWriter saveWrite = new FileWriter("save.dat");
            saveWrite.write(getFen());
            saveWrite.close();
            System.out.println("successfully wrote to the file");
        }catch(IOException e){
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }
    public void loadGame(){
        String data = null;
        int lastNdx = 0;
        char charAtLastNdx;

        try{
            File saveFile = new File("save.dat");
            Scanner saveReader = new Scanner(saveFile);
            while(saveReader.hasNextLine()){
                data = saveReader.nextLine();
            }
            saveReader.close();
        } catch(FileNotFoundException e){
            System.out.println("An error has occurred");
            e.printStackTrace();
        }


        outerloop:
        for(int r = 0; r <= 7; r++){
            System.out.println("R is " + r);
            for(int f = 0; f <= 7; f++){
               charAtLastNdx = data.charAt(lastNdx);
                if(Character.isUpperCase(charAtLastNdx)){
                    switch (charAtLastNdx) {
                        case 'R' -> {
                            White_Pieces.add(new Rook(f, r, true, "Rook.png", this));
                            System.out.println("Created R at " + f + " and " + r);
                        }
                        case 'N' -> {
                            White_Pieces.add(new Knight(f, r, true, "Knight.png", this));
                            System.out.println("Created N at " + f + " and " + r);
                        }
                        case 'B' -> {
                            White_Pieces.add(new Bishop(f, r, true, "Bishop.png", this));
                            System.out.println("Created B at " + f + " and " + r);
                        }
                        case 'Q' -> {
                            White_Pieces.add(new Queen(f, r, true, "Queen.png", this));
                            System.out.println("Created Q at " + f + " and " + r);
                        }
                        case 'K' -> {
                            White_Pieces.add(new King(f, r, true, "King.png", this));
                            System.out.println("Created K at " + f + " and " + r);
                            this.whiteKing = (King) White_Pieces.get(White_Pieces.size() - 1);
                        }
                        case 'P' -> {
                            White_Pieces.add(new Pawn(f, r, true, "Pawn.png", this));
                            System.out.println("Created P at " + f + " and " + r);
                        }
                    }
                    lastNdx++;
                }else if(Character.isLowerCase(data.charAt(lastNdx))){
                    switch (data.charAt(lastNdx)) {
                        case 'r' -> {
                            Black_Pieces.add(new Rook(f, r, false, "Rook.png", this));
                            System.out.println("Created r at" + f + " and " + r);
                        }
                        case 'n' -> {
                            Black_Pieces.add(new Knight(f, r, false, "Knight.png", this));
                            System.out.println("Created n at" + f + " and " + r);
                        }
                        case 'b' -> {
                            Black_Pieces.add(new Bishop(f, r, false, "Bishop.png", this));
                            System.out.println("Created b at" + f + " and " + r);
                        }
                        case 'q' -> {
                            Black_Pieces.add(new Queen(f, r, false, "Queen.png", this));
                            System.out.println("Created q at" + f + " and " + r);
                        }
                        case 'k' -> {
                            Black_Pieces.add(new King(f, r, false, "King.png", this));
                            System.out.println("Created k at" + f + " and " + r);
                            this.blackKing = (King) Black_Pieces.get(Black_Pieces.size() - 1);
                        }
                        case 'p' -> {
                            Black_Pieces.add(new Pawn(f, r, false, "Pawn.png", this));
                            System.out.println("Created p at" + f + " and " + r);
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
        System.out.println("White piece size is: " + White_Pieces.size());    
        this.drawBoard();
    }

    public String getFen(){
        StringBuffer fen = new StringBuffer();

        int nullCtr = 0;
        boolean invalidWhiteKCastle = true;
        boolean invalidWhiteQCastle = true;
        boolean invalidBlackKCastle = true;
        boolean invalidBlackQCastle = true;

        //FEN piece placement
        //rank
        for(int r = 0; r <= 7; r++){
            //file
            for(int f = 0; f <= 7; f++){
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

        //FEN who moves next
        if(turnCounter % 2 != 1){
            //white's turn
            fen.append(" w ");
        }else{
            //black's turn
            fen.append(" b ");
        }

        //FEN castling rights
        Piece checkWhiteKing = getPiece(4, 7);
        if(checkWhiteKing != null && checkWhiteKing.getClass().equals(King.class)){
            King whiteKing = (King) checkWhiteKing;
            if(whiteKing.hasKingSideCastlingRights()){
                fen.append("K");
                invalidWhiteKCastle = false;
            }
            if(whiteKing.hasQueenSideCastlingRights()){
                fen.append("Q");
                invalidWhiteQCastle = false;
            }
        }

        if(invalidWhiteQCastle && invalidWhiteKCastle){
            fen.append("-");
        }

        Piece checkBlackKing = getPiece(4, 0);
        if(checkBlackKing != null && checkBlackKing.getClass().equals(King.class)){
            King blackKing = (King) checkBlackKing;
            if(blackKing.hasKingSideCastlingRights()){
                fen.append("k");
                invalidBlackKCastle = false;
            }
            if(blackKing.hasQueenSideCastlingRights()){
                fen.append("q");
                invalidBlackQCastle = false;
            }
        }

       if(invalidBlackQCastle && invalidBlackKCastle){
           fen.append("-");
       }

       //FEN possible En Passant targets
        Move lastMove = Moves.get(Moves.size()-1);
       if(lastMove.getMovedPiece().getClass().equals(Pawn.class) &&
               Math.abs(lastMove.getFinalSpot().getY() - lastMove.getInitialSpot().getY()) == 2){
           if(lastMove.getMovedPiece().isWhite()){
               fen.append(" ").append(lastMove.getFinalSpot().getXLabel()).append(lastMove.getFinalSpot().getYLabel() - 1);
           }else{
               fen.append(" ").append(lastMove.getFinalSpot().getXLabel()).append(lastMove.getFinalSpot().getYLabel() + 1);
           }
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

        //if(bestMove != null){
            Spot initialSpot = convertUci(bestMove.substring(0,2));
            Spot finalSpot = convertUci(bestMove.substring(2,4));
            Piece movedPiece = getPiece(initialSpot.getX(), initialSpot.getY());
            Piece capturedPiece = getPiece(finalSpot.getX(), finalSpot.getY());

            Moves.add(new Move(movedPiece, capturedPiece, initialSpot, finalSpot));

            if(capturedPiece != null){
                if(capturedPiece.isWhite()){
                    White_Pieces.remove(capturedPiece);
                }else{
                    Black_Pieces.remove(capturedPiece);
                }
            }

            movedPiece.setX(finalSpot.getX());
            movedPiece.setY(finalSpot.getY());

            if(movedPiece.getClass().equals(Pawn.class) || capturedPiece != null ){
                prevHalfMoveCounter = halfMoveCounter;
                halfMoveCounter = 0;
            }else{
                halfMoveCounter++;
            }

            fullMoveCounter++;
            turnCounter++;
        //}

    }

    public Spot convertUci(String uciSpot){
        int convertedX = Math.abs(uciSpot.charAt(0) - 'a');
        int convertedY = Math.abs(uciSpot.charAt(1) - 56);     //56 is decimal equivalent of char 8
        return new Spot(convertedX, convertedY);
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
            //ArrayList<Spot> availMoves;

            Piece clicked_piece = getPiece(Clicked_Column, Clicked_Row);

            if (Active_Piece == null && clicked_piece != null &&
                    ((is_whites_turn && clicked_piece.isWhite()) || (!is_whites_turn && clicked_piece.isBlack()))) {

                Active_Piece = clicked_piece;
                Active_Piece.availableMoves(Active_Piece.getX(), Active_Piece.getY());

            } else if (Active_Piece != null && Active_Piece.getX() == Clicked_Column && Active_Piece.getY() == Clicked_Row) {
                Active_Piece = null;

            } else if (Active_Piece != null && Active_Piece.canMove(Clicked_Column, Clicked_Row) &&
                     ((is_whites_turn && Active_Piece.isWhite()) || (!is_whites_turn && Active_Piece.isBlack()))) {

               // availMoves = Active_Piece.availableMoves(Active_Piece.getX(), Active_Piece.getY());
                //if(availMoves.stream().anyMatch(s -> s.getX() == Clicked_Column && s.getY() == Clicked_Row)) {
                if (clicked_piece != null) {
                    if (clicked_piece.isWhite()) {
                        White_Pieces.remove(clicked_piece);
                    } else {
                        Black_Pieces.remove(clicked_piece);
                    }
                }

                Moves.add(new Move(Active_Piece, getPiece(Clicked_Column, Clicked_Row), new Spot(Active_Piece.getX(), Active_Piece.getY()), new Spot(Clicked_Column, Clicked_Row)));

                movesLastNdx = Moves.size()-1;

                //fullmove counter increments everytime black moves
                    if (Moves.get(movesLastNdx).getMovedPiece().isBlack()) {
                        fullMoveCounter++;
                    }

                    //halfmove counter is reset after captures or pawn moves
                    if (Moves.get(movesLastNdx).getMovedPiece().getClass().equals(Pawn.class) || Moves.get(movesLastNdx).getCapturedPiece() != null) {
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
                        if(Moves.get(movesLastNdx).isEnPassantCapture(board)) {
                            Piece prevMovedPiece = board.Moves.get(movesLastNdx - 1).getMovedPiece();
                            if (prevMovedPiece.isWhite()) {
                                White_Pieces.remove(prevMovedPiece);
                            } else {
                                Black_Pieces.remove(prevMovedPiece);
                            }
                            board.Moves.get(movesLastNdx).setCapturedPiece(prevMovedPiece);
                        }

                    } else if (Active_Piece.getClass().equals(Rook.class)) {
                        Rook castedRook = (Rook) (Active_Piece);
                        castedRook.setIsFirstMove(!castedRook.getHasMoved());
                        castedRook.setHasMoved(true);
                    }

                    //if piece is pawn, check if promotable
                    if(Clicked_Row == 0 || Clicked_Row == 7){
                        if(Active_Piece.getClass().equals(Pawn.class)){
                            Pawn promotedPawn = (Pawn) (Active_Piece);
                            promotedPawn.isPromotion(promotedPawn);
                        }
                    }
                    Active_Piece = null;
                    getFen();
                    turnCounter++;
                    if (isAgainstEngine) {
                        doEngineMove();
                    }
                //}
            }

            drawBoard();
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