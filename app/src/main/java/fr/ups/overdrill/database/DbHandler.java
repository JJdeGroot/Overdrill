package fr.ups.overdrill.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Handler used to put and retrieve data from the local SqLite database
 * @author JJ
 */
public class DbHandler implements Fields {

	private final String TAG = "DbHandler";

	// Database fields
	private SQLiteDatabase db;
	private DbHelper helper;
	
	/**
	 * Constructo to create a new DbHandler
	 * @param context Context reference
	 */
	public DbHandler(Context context) {
		this.helper = new DbHelper(context);
		Log.d(TAG, "DbHandler initialized");
	}
	
	/**
	 * Opens the database connection
	 * @throws SQLException Thrown if database is invalid.
	 */
	public void open() throws SQLException {
		db = helper.getWritableDatabase();
	}
	
	/**
	 * Closes the database connection
	 */
	public void close() {
		if(db != null && db.isOpen()){
			db.close();
		}
	}



	//////////// HISCORE ////////////

	/**
	 * Inserts a Score into the database
	 * @param name Name of the player
	 * @param score Score of the player
	 * @return ID of new row
	 */
	public long insertScore(String name, int score) {
		open();
		
		// Insert app info
		ContentValues values = new ContentValues();
        values.put(SCORE_TIMESTAMP, System.currentTimeMillis());
		values.put(SCORE_PLAYER, name);
		values.put(SCORE_SCORE, score);
		long id = db.insert(TABLE_SCORES, null, values);
		
		close();
		return id;
	}
	
	/**
	 * Retrieves a Group from a Cursor reference 
	 * @param cursor Cursor pointing to a group
	 * @return A Group object
	 */
	private Score getScore(Cursor cursor) {
		long id = cursor.getLong(cursor.getColumnIndex(SCORE_ID));
        long timestamp = cursor.getLong(cursor.getColumnIndex(SCORE_TIMESTAMP));
		String name = cursor.getString(cursor.getColumnIndex(SCORE_PLAYER));
		int score = cursor.getInt(cursor.getColumnIndex(SCORE_SCORE));

		return new Score(id, timestamp, name, score);
	}
	
	/**
	 * Retrieves a score by query and args
	 * @param query The query
	 * @param args  The arguments
	 * @return A Score object, or null
	 */
	private Score getScore(String query, String[] args) {
		Score score = null;

		open();
		Cursor cursor = db.rawQuery(query, args);
		if(cursor.moveToFirst()) {
			score = getScore(cursor);
		}
		cursor.close();
		close();
		
		return score;
	}
	
	/**
	 * Retrieves a Score by its name
	 * @param name Player name
	 * @return A Score object, or null
	 */
	public Score getScore(String name) {
		String query = "SELECT * FROM " + TABLE_SCORES + " WHERE " + SCORE_PLAYER + "=?";
		String[] args = new String[] {name};
		return getScore(query, args);
	}
	
	/**
	 * Retrieves a Score by its ID
	 * @param id Score id
	 * @return A Score object, or null
	 */
	public Score getScore(long id) {
		String query = "SELECT * FROM " + TABLE_SCORES + " WHERE " + SCORE_ID + "=" + id;
		return getScore(query, null);
	}
	
	/**
	 * Returns a list of all Score objects retrieved in the query
	 * @return List of Score objects
	 */
	private ArrayList<Score> getScores(String query, String[] args) {
		ArrayList<Score> list = new ArrayList<Score>();
		
		open();
		Cursor cursor = db.rawQuery(query, args);
		if(cursor.moveToFirst()) {
			do {
                Score score = getScore(cursor);
				list.add(score);
			}
			while(cursor.moveToNext());
		}
		close();
		
		return list;
	}
	
	/**
	 * Returns a list of all Score objects in the database
	 * @return List of all Score objects
	 */
	public ArrayList<Score> getScores() {
		String query = "SELECT * FROM " + TABLE_SCORES;
		return getScores(query, null);
	}

    /**
     * Returns a list of all Scores objects starting with the higheest score
     * @return List of Score objects from high to low.
     */
    public ArrayList<Score> getScoresHighToLow() {
        String query = "SELECT * FROM " + TABLE_SCORES + " ORDER BY " + SCORE_SCORE + " DESC";
        return getScores(query, null);
    }



    ///////////// SETTINGS //////////////

    /**
     * Retrieves the last setting with the specified name
     * @param name Setting name
     * @return Setting text
     */
    private String getSetting(String name) {
        String setting = " ORDER BY name ASC";

        open();
        String query = "SELECT * FROM " + TABLE_SETTINGS + " WHERE " + SETTING_KEY + " LIKE \"" + name + "\" ORDER BY " + SETTING_ID + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            setting = cursor.getString(cursor.getColumnIndex(SETTING_VALUE));
        }
        close();

        return setting;
    }

    /**
     * Inserts a setting into the settings table
     * @param name Setting name
     * @param value Setting value
     * @return Insertion ID
     */
    public long insertSetting(String name, String value) {
        open();

        // Values
        ContentValues values = new ContentValues();
        values.put(SETTING_KEY, name);
        values.put(SETTING_VALUE, value);
        long id = db.insert(TABLE_SETTINGS, null, values);

        close();
        return id;
    }

}