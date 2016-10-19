package fr.ups.overdrill.database;

/**
 * Describes the score of a player
 */
public class Score {

    private long id;
    private long timestamp;
    private String player;
    private int score;

    /**
     * Constructor for a Score object
     * @param id Internal database ID
     * @param timestamp Timestamp when the score was achieved.
     * @param player The name of the player
     * @param score The score thas has been achieved
     */
    public Score(long id, long timestamp, String player, int score) {
        this.id = id;
        this.timestamp = timestamp;
        this.player = player;
        this.score = score;
    }

    /**
     * Returns the ID of the score
     * @return Score ID
     */
    public long getID() {
        return id;
    }

    /**
     * Returns the timestamp the score was achieved.
     * @return Timestamp of achievement.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the name of the player
     * @return Player name
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Returns the score
     * @return The actual score.
     */
    public int getScore() {
        return score;
    }

}