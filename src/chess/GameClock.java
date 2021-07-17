package chess;

import java.awt.*;
import java.awt.image.*;
import java.io.Serial;
import javax.swing.*;
//import org.apache.log4j.Logger;

public class GameClock extends JPanel implements Runnable
{
    @Serial
    private static final long serialVersionUID = 1L;
    //private static final Logger LOG = org.apache.log4j.Logger.getLogger(GameClock.class);
    private Clock clockWhitePlayer;
    private Clock clockBlackPlayer;
    private Clock activeClock;
    private Thread thread;
    private Board board;
    private BufferedImage background;

    GameClock(Board board)
    {
        super();
        this.clockWhitePlayer = new Clock();
        this.clockBlackPlayer = new Clock();
        this.activeClock = this.clockWhitePlayer;
        this.board = board;
        this.background = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);

        //int time = 300;
        int time = board.timeLimit;

        this.setTimes(time, time);

        this.thread = new Thread(this);
        thread.start();
        this.drawBackground();
        this.setDoubleBuffered(true);
    }

    GameClock(Board board, int wTime, int bTime)
    {
        super();
        this.clockWhitePlayer = new Clock();
        this.clockBlackPlayer = new Clock();
        this.setTimes(wTime, bTime);
        this.activeClock = this.clockWhitePlayer;
        this.board = board;
        this.background = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
        this.thread = new Thread(this);
        thread.start();
        this.drawBackground();
        this.setDoubleBuffered(true);
    }

    public void start()
    {
        this.thread.start();
    }

    public void stop()
    {
        this.activeClock = null;

        try
        {
            this.thread.wait();
        }
        catch (InterruptedException | IllegalMonitorStateException exc)
        {
            //LOG.error("Error blocking thread: ", exc);
        }
    }

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
        new Font("Serif", Font.ITALIC, 16);
        //g2d.drawString(settings.getPlayerWhite().getName(), 10, 50);
        g2d.setColor(Color.WHITE);
        //g2d.drawString(settings.getPlayerBlack().getName(), 100, 50);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        String whiteClockString = this.clockWhitePlayer.getAsString();
        String blackClockString = this.clockBlackPlayer.getAsString();
        Graphics2D g2d = (Graphics2D) g;
        //g2d.drawImage(this.background, 0, 0, this);
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        Font font = new Font("Serif", Font.ITALIC, 20);
        //g2d.drawImage(this.background, 0, 0, this);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(5, 25, 80, 25);
        g2d.setFont(font);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(85, 25, 90, 25);
        g2d.drawRect(5, 25, 170, 25);
        g2d.drawRect(5, 50, 170, 25);
        g2d.drawLine(85, 25, 85, 75);
        font = new Font("Serif", Font.ITALIC, 14);
        //g2d.drawImage(this.background, 0, 0, this);
        g2d.setFont(font);
        g.drawString("WHITE", 10, 43);
        g.setColor(Color.WHITE);
        g.drawString("BLACK", 100, 43);
        g2d.setFont(font);
        g.setColor(Color.BLACK);
        g2d.drawString(whiteClockString, 10, 70);
        g2d.drawString(blackClockString, 100, 70);
    }

    @Override
    public void update(Graphics g)
    {
        paint(g);
    }

    public void switchClocks()
    {
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
        if (side == "b")
        {
            this.activeClock = this.clockBlackPlayer;
        }
        else
        {
            this.activeClock = this.clockWhitePlayer;
        }
    }

    public void setTimes(int t1, int t2)
    {
        this.clockWhitePlayer.init(t1);
        this.clockBlackPlayer.init(t2);
    }


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
                    this.timeOver();
                    //System.out.print("out");
                }
            }
        }
    }

    public String outOfTime()
    {
        String ret = "none";
        if (this.clockWhitePlayer.getLeftTime() == 0)
        {
            ret = "Black wins";
        }
        else if (this.clockBlackPlayer.getLeftTime() == 0)
        {
            ret = "White wins";
        }
        return ret;
    }

    public int timeLeftW()
    {
        return this.clockWhitePlayer.getLeftTime();
    }
    public int timeLeftB()
    {
        return this.clockBlackPlayer.getLeftTime();
    }

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
        //this.board.("Time is over! " + color + " player win the game.");
        this.stop();
    }
}
