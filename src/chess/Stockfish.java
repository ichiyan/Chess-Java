package chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.lang3.StringUtils;

public class Stockfish {
    private BufferedReader processReader;
    private OutputStreamWriter processWriter;

    //Gab's stockfish path. To execute your stockfish, comment out my path and add your own path.
    private static final String PATH = "D:/exe/stockfish_14_win_x64_avx2/stockfish_14_x64_avx2.exe";
     //private static final String PATH = "D:/Dave/Downloads/stockfish_14_win_x64_avx2/stockfish_14_x64_avx2.exe";
    //private static final String PATH = "C:/Users/lenovo/OneDrive - usc.edu.ph/Documents/stockfish_14_win_x64_avx2/stockfish_14_x64_avx2.exe";

    public void startEngine(){
        try {
            Process engineProcess = Runtime.getRuntime().exec(PATH);
            System.out.println("start engine");
            processReader = new BufferedReader(new InputStreamReader(engineProcess.getInputStream()));
            processWriter = new OutputStreamWriter(engineProcess.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopEngine(){
        try{
            sendCommand("quit");
            processReader.close();
            processWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCommand(String command){
        try {
            processWriter.write(command + "\n");
            processWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  String getOutput(int waitTime){
        StringBuffer stringBuffer = new StringBuffer();
        try{
            Thread.sleep(waitTime);
            sendCommand("isready");
            while (true){
                String text = processReader.readLine();
                if(text.equals("readyok")){
                    break;
                }else{
                    stringBuffer.append(text).append("\n");
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("StringBuffer: "+ stringBuffer);
        return stringBuffer.toString();
    }

    public String getOutput(){
        StringBuffer stringBuffer = new StringBuffer();
        try{
            String text = " ";
            while (!text.contains("bestmove")){
                text = processReader.readLine();
                stringBuffer.append(text).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("OUTPUT: " + stringBuffer);
        return  stringBuffer.toString();
    }

    public String getBestMove(String fen, int depth){
        sendCommand("position fen " + fen);
        sendCommand("go depth " + depth);
        String bestMove = getOutput();

        if (bestMove.contains("ponder")) {
            bestMove = StringUtils.substringBetween(bestMove, "bestmove ", " ponder");
        }else{
            bestMove = bestMove.substring(9);
        }

        return bestMove;
    }

    //test
    public static void main(String[] args) throws IOException{
        Stockfish stockfish = new Stockfish();
        stockfish.startEngine();
    }
}
