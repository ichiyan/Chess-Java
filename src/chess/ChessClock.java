package chess;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public final class ChessClock extends Canvas {
    private static final long serialVersionUID = 2917618081806596492L;
    private static Font font;
    private boolean running = false;
    long msecleft = 0; // time left, in msec
    private long referenceTime; // system time @ setClock() call
    private long referenceTimeLeft; // argument to setClock() call
    private static final Color colorHighlight = Color.CYAN; //Config.colorClockRunning; // color of
    private static final Color colorIdle = Color.GRAY; //Config.colorClockIdle; // color of idle

    public ChessClock() {
        this.setFont(font);
        this.setForeground(colorIdle);

        ActionListener taskPerformer = evt -> {
            if (running) {
                msecleft = referenceTimeLeft - System.currentTimeMillis()
                        + referenceTime;
                repaint();
            }
        };
        //Config.clockSleepTime
        new Timer(0, taskPerformer).start();
        stopClock();
        setClock(3 * 60 * 1000);
    }

    public static void swap(ChessClock a, ChessClock b) {
        boolean r = a.running;
        long l1 = a.msecleft, l2 = a.referenceTime, l3 = a.referenceTimeLeft;

        a.running = b.running;
        a.msecleft = b.msecleft;
        a.referenceTime = b.referenceTime;
        a.referenceTimeLeft = b.referenceTimeLeft;

        b.running = r;
        b.msecleft = l1;
        b.referenceTime = l2;
        b.referenceTimeLeft = l3;
    }

    public void stopClock() {
        running = false;
        repaint();
    }

    public void startClock() {
        setClock(msecleft); // shouldn't be necessary, but suspend/resume don't
        // work
        running = true;
    }

    public void setClock(long l) { // set clock, in milliseconds
        msecleft = l;
        referenceTimeLeft = l;
        referenceTime = System.currentTimeMillis();
        repaint();
    }

    public void paint(Graphics g) {
        StringBuilder s = new StringBuilder(5);

        int timeLeft = (int) (msecleft / 1000);

        int min = Math.abs(timeLeft / 60);
        int sec;
        if (timeLeft > 0) {
            sec = timeLeft % 60;
        }
        else {
            sec = (-timeLeft) % 60;
        }

        if (timeLeft < 0) {
            s.append("-").append(min).append(":");
        }
        else {
            s.append(min).append(":");
        }
        if (sec < 10) {
            s.append("0").append(sec);
        }
        else {
            s.append(sec);
        }

        if (running) { // highlight this clock
            g.setColor(colorHighlight);
        }
        g.drawString(s.toString(), 500, 580);
    }

    public Dimension minimumSize() {
        return new Dimension(100, 40);
    }

    public Dimension preferredSize() {
        return new Dimension(60, 16);
    }
}