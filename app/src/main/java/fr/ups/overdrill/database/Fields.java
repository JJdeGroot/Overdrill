package fr.ups.overdrill.database;

/**
 * Interface containing all database fields
 * @author JJ
 */
public interface Fields {

	// Settings
	String TABLE_SETTINGS = "settings",
           SETTING_ID = "id",
           SETTING_KEY = "name",
           SETTING_VALUE = "value";

	// Score table
	String TABLE_SCORES = "scores",
           SCORE_ID = "id",
           SCORE_TIMESTAMP = "timestamp",
           SCORE_PLAYER = "player",
           SCORE_SCORE = "score";

}
