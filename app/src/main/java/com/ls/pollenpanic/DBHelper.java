package com.ls.pollenpanic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * This class is responsible for facilitating communication between the application and our database
 */
public class DBHelper extends SQLiteOpenHelper {
    private Context context; // current context

    private static final String DATABASE_NAME = "PollenPanicLeaderBoard.db"; // the database to connect to
    private static final int DATABASE_VERSION = 1; // the current version of our database

    private static final String TABLE_NAME = "leader_board"; // the table inside our database
    private static final String COLUMN_ID = "_id"; // column for id
    private static final String COLUMN_USER_NAME = "user_name"; // column for user name
    private static final String COLUMN_SCORE = "score"; // column for score

    // Constructor
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creates the required tables for our application
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDB = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER);", TABLE_NAME, COLUMN_ID, COLUMN_USER_NAME, COLUMN_SCORE);
        db.execSQL(createDB);
    }

    // Drops the current information and re-creates the required tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropDB = String.format("DROP TABLE IF EXISTS %s;", TABLE_NAME);
        onCreate(db);
    }

    // When a user submits their score to the leaderboard, their name and score are stored in the table.
    public void addNewScore(String userName, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, userName);
        contentValues.put(COLUMN_SCORE, score);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            Toast.makeText(context, "Failed to add new score to leaderboard.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Score added to the leaderboard.", Toast.LENGTH_SHORT).show();
        }
    }

    // Grabs the top n hi-scores to display on the leaderboard
    public Cursor getTopHighScores(int n) {
        String query = String.format("SELECT * FROM %s ORDER BY %s DESC LIMIT 0,%d", TABLE_NAME, COLUMN_SCORE, n);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }
}
