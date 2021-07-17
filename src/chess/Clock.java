package chess;

public class Clock {

    private static final String COLON = ":";

    private final static String ZERO = "0";

    private int timeLeftInSeconds = 0;

    private Player player;

    public enum Player {
        BLACK,
        WHITE
    }

    Clock() {
        init(timeLeftInSeconds);
    }

    Clock(int timeLeftInSeconds) {
        init(timeLeftInSeconds);
    }

    public final void init(int timeLeftInSeconds) {
        this.timeLeftInSeconds = timeLeftInSeconds;
    }

    public boolean decrement() {
        if (timeLeftInSeconds > 0) {
            timeLeftInSeconds = timeLeftInSeconds - 1;
            return true;
        }
        return false;
    }

    public int getLeftTime() {
        return timeLeftInSeconds;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public String getAsString() {
        String minutesAsString;
        Integer minutes = timeLeftInSeconds / 60;
        Integer seconds = timeLeftInSeconds % 60;
        if (minutes < 10) {
            minutesAsString = ZERO + minutes.toString();
        } else {
            minutesAsString = minutes.toString();
        }
        String result = minutesAsString + COLON;
        if (seconds < 10) {
            result = result + ZERO + seconds.toString();
        } else {
            result = result + seconds.toString();
        }

        return result;
    }
}
