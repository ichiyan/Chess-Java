package chess;

import java.io.Serializable;
import java.time.Clock;
import java.time.ZoneId;
import java.time.Instant;
import java.time.ZoneOffset;

public class ChessClock2 extends Clock implements Serializable {

    public enum Player {
        LEFT,
        RIGHT
    }

    private static final long serialVersionUID = 1L;

    private Instant instantStart;
    private Instant instantLeft;
    private Instant instantRight;
    private long timeLeft;
    private long timeRight;
    private Player player;

    public ChessClock2(Player player) {
        this.player = player;
    }

    public Instant gameStart() {

        if (this.instantStart == null) {
            this.timeLeft = 0;
            this.timeRight = 0;
            this.instantStart = Instant.now();
            this.instantLeft = instantStart;
            this.instantRight = instantStart;
            return instantStart;
        }

        throw new IllegalStateException(
                "Game already started. Stop it and try again.");
    }

    public Instant gameEnd() {

        if (this.instantStart != null) {
            instantStart = null;
            return Instant.now();
        }

        throw new IllegalStateException("Game was not started.");
    }

    @Override
    public ZoneId getZone() {
        return ZoneOffset.UTC;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        throw new UnsupportedOperationException(
                "The ChessClock works only in UTC time zone");
    }

    @Override
    public Instant instant() {

        if (this.instantStart != null) {
            if (player == Player.LEFT) {
                player = Player.RIGHT;

                long secondsLeft = Instant.now().getEpochSecond()
                            - instantRight.getEpochSecond();
                instantLeft = instantLeft.plusSeconds(
                            secondsLeft - timeLeft);
                timeLeft = secondsLeft;

                return instantLeft;
            } else {
                player = Player.LEFT;

                long secondsRight = Instant.now().getEpochSecond()
                        - instantLeft.getEpochSecond();
                instantRight = instantRight.plusSeconds(
                        secondsRight - timeRight);
                timeRight = secondsRight;

                return instantRight;
            }
        }

        throw new IllegalStateException("Game was not started.");
    }
}
