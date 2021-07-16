package chess;

import java.awt.*;
import java.awt.image.*;
import java.io.Serial;
import javax.swing.JPanel;
//import org.apache.log4j.Logger;

public class GameClock extends JPanel implements Runnable
{
    @Serial
    private static final long serialVersionUID = 1L;
    //private static final Logger LOG = org.apache.log4j.Logger.getLogger(GameClock.class);
    private Clock clockWhitePlayer;
    private Clock clockBlackPlayer;
    private Clock activeClock;
    //private Settings settings;
    private Thread thread;
    private Board board;
    private String whiteClockString;
    private String blackClockString;
    private BufferedImage background;

    GameClock(Board board)
    {
        super();
        this.clockWhitePlayer = new Clock();
        this.clockBlackPlayer = new Clock();
        this.activeClock = this.clockWhitePlayer;
        this.board = board;
        //this.settings = game.getSettings();
        this.background = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);

        int time = 300;

        this.setTimes(time, time);
        //this.setPlayers(this.settings.getPlayerBlack(), this.settings.getPlayerWhite());

        this.thread = new Thread(this);
//        if (this.settings.isTimeLimitSet())
//        {
            thread.start();
        //}
        this.drawBackground();
        this.setDoubleBuffered(true);
    }

    /** Method to init game clock
     */
    public void start()
    {
        this.thread.start();
    }

    /** Method to stop game clock
     */
    public void stop()
    {
        this.activeClock = null;

        try
        {
            //block this thread
            this.thread.wait();
        }
        catch (InterruptedException | IllegalMonitorStateException exc)
        {
            //LOG.error("Error blocking thread: ", exc);
        }
    }

    /** Method of drawing graphical background of clock
     */
    void drawBackground()
    {
        Graphics gr = this.background.getGraphics();
        Graphics2D g2d = (Graphics2D) gr;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.ITALIC, 20);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(5, 30, 80, 30);
        g2d.setFont(font);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(85, 30, 90, 30);
        g2d.drawRect(5, 30, 170, 30);
        g2d.drawRect(5, 60, 170, 30);
        g2d.drawLine(85, 30, 85, 90);
        font = new Font("Serif", Font.ITALIC, 16);
        //g2d.drawString(settings.getPlayerWhite().getName(), 10, 50);
        g2d.setColor(Color.WHITE);
        //g2d.drawString(settings.getPlayerBlack().getName(), 100, 50);
    }

    /**
     Annotation to superclass Graphics drawing the clock graphics
     * @param g Graphics2D Capt object to paint
     */
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        whiteClockString = this.clockWhitePlayer.getAsString();
        blackClockString = this.clockBlackPlayer.getAsString();
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.background, 0, 0, this);
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        Font font = new Font("Serif", Font.ITALIC, 20);
        g2d.drawImage(this.background, 0, 0, this);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(5, 30, 80, 30);
        g2d.setFont(font);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(85, 30, 90, 30);
        g2d.drawRect(5, 30, 170, 30);
        g2d.drawRect(5, 60, 170, 30);
        g2d.drawLine(85, 30, 85, 90);
        font = new Font("Serif", Font.ITALIC, 14);
        g2d.drawImage(this.background, 0, 0, this);
        g2d.setFont(font);
        g.drawString("WHITE", 10, 50);
        g.setColor(Color.WHITE);
        g.drawString("BLACK", 100, 50);
        g2d.setFont(font);
        g.setColor(Color.BLACK);
        g2d.drawString(whiteClockString, 10, 80);
        g2d.drawString(blackClockString, 90, 80);
    }

    /**
     Annotation to superclass Graphics updating clock graphic
     * @param g Graphics2D Capt object to paint
     */
    @Override
    public void update(Graphics g)
    {
        paint(g);
    }

    /** Method of swiching the players clocks
     */
    public void switchClocks()
    {
        /*in documentation this method is called 'switch', but it's restricted name
        to switch block (in pascal called "case") - this've to be repaired in documentation by Wąsu:P*/
        if (this.activeClock == this.clockWhitePlayer)
        {
            this.activeClock = this.clockBlackPlayer;
        }
        else
        {
            this.activeClock = this.clockWhitePlayer;
        }
    }

    public void setClockSide(String side)
    {
        /*in documentation this method is called 'switch', but it's restricted name
        to switch block (in pascal called "case") - this've to be repaired in documentation by Wąsu:P*/
        if (side == "b")
        {
            this.activeClock = this.clockBlackPlayer;
        }
        else
        {
            this.activeClock = this.clockWhitePlayer;
        }
    }

    /** Method with is setting the players clocks time
     * @param t1 Capt the player time
     * @param t2 Capt the player time
     */
    public void setTimes(int t1, int t2)
    {
        this.clockWhitePlayer.init(t1);
        this.clockBlackPlayer.init(t2);
    }

    /** Method with is setting the players clocks
     * @param p1 Capt player information
     * @param p2 Capt player information
     */
//    private void setPlayers(Player p1, Player p2)
//    {
//        if (p1.getColor() == Colors.WHITE)
//        {
//            this.clockWhitePlayer.setPlayer(p1);
//            this.clockBlackPlayer.setPlayer(p2);
//        }
//        else
//        {
//            this.clockWhitePlayer.setPlayer(p2);
//            this.clockBlackPlayer.setPlayer(p1);
//        }
//    }

    /**
     * Method with is running the time on clock
     */
    @Override
    public void run()
    {
        while (true)
        {
            if (this.activeClock != null)
            {
                if (this.activeClock.decrement())
                {
                    repaint();
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e)
                    {
                        //LOG.error("Some error in gameClock thread: " + e);
                    }
                }
                if (this.activeClock != null && this.activeClock.getLeftTime() == 0)
                {
                    //this.timeOver();
                    //System.out.print("out");
                }
            }
        }
    }

    /** Method of checking is the time of the game is not over
     */
    private void timeOver()
    {
        String color = new String();
        if (this.clockWhitePlayer.getLeftTime() == 0)
        {
            color = "Black";
        }
        else if (this.clockBlackPlayer.getLeftTime() == 0)
        {
            color = "White";
        }
//        else
//        {
//            LOG.debug("Time over called when player got time 2 play");
//        }
        //this.game.endGame("Time is over! " + color + " player win the game.");
        this.stop();
    }
}
