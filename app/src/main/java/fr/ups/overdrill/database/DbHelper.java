package fr.ups.overdrill.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Contains methods to interact with the local SqLite database
 * @author JJ
 */
public class DbHelper extends SQLiteOpenHelper implements Fields {

	private static final String TAG = "DbHelper";
	private static final String DATABASE_NAME = "database.db";
	private static final int DATABASE_VERSION = 1;
	private Context context;

	/**
	 * Constructor to create a Database helper
	 * @param context Context reference
	 */
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
		Log.d(TAG, "Constructor called!");
	}
	
	/** Create the database */
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "Creating database!");

		// Score table
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SCORES +
												   " (" +
												   		SCORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        SCORE_TIMESTAMP + " INTEGER,"+
												   		SCORE_PLAYER + " TEXT," +
												   		SCORE_SCORE + " INTEGER"+
												   ");");

		// Settings table
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SETTINGS +
												   "(" +
												   		SETTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
												   		SETTING_KEY + " TEXT," +
												   		SETTING_VALUE + " TEXT"+
												   ");");
		
		Log.d(TAG, "Database has been created!");
	}

	/** Upgrade the database */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
		// TODO: In future versions: upgrade database structure or keep it the same
	}

}
