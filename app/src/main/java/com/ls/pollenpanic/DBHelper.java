package com.ls.pollenpanic;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;

    private static final String DATABASE_NAME = "PollenPanicLeaderBoard.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "leader_board";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_SCORE = "score";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDB = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT);", TABLE_NAME, COLUMN_ID, COLUMN_USER_NAME, COLUMN_SCORE);
        db.execSQL(createDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropDB = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
    }
    public void addNewScore(String userName, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, userName);
        contentValues.put(COLUMN_SCORE, String.valueOf(score));

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            Toast.makeText(context, "Failed to add new score to leaderboard.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Score added to the leaderboard.", Toast.LENGTH_SHORT).show();
        }
    }
}
