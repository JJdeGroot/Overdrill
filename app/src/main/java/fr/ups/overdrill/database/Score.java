package fr.ups.overdrill.database;

/**
 * Describes the score of a player
 */
public class Score {

    private long id;
    private String player;
    private int score;

    /**
     * Constructor for a Score object
     * @param id Internal database ID
     * @param player The name of the player
     * @param score The score thas has been achieved
     */
    public Score(long id, String player, int score) {
        this.id = id;
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