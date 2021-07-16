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

    /**
     * default constructor, sets timeLeft to 0 minutes, 0 seconds.
     */
    Clock() {
        init(timeLeftInSeconds);
    }

    Clock(int timeLeftInSeconds) {
        init(timeLeftInSeconds);
    }

    public final void init(int timeLeftInSeconds) {
        this.timeLeftInSeconds = timeLeftInSeconds;
    }

    /**
     * Method to decrement value of left time
     *
     * @return bool true if time_left > 0, else returns false
     */
    public boolean decrement() {
        if (timeLeftInSeconds > 0) {
            timeLeftInSeconds = timeLeftInSeconds - 1;
            return true;
        }
        return false;
    }

    /**
     * Method to get left time in seconds
     *
     * @return Player int integer of seconds
     */
    public int getLeftTime() {
        return timeLeftInSeconds;
    }

    /**
     * Method to get player (owner of this clock)
     *
     * @param player player to set as owner of clock
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Method to get player (owner of this clock)
     *
     * @return Reference to player class object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Method to prepare time in nice looking String
     *
     * @return String of actual left game time with ':' digits in mm:ss format
     */
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
