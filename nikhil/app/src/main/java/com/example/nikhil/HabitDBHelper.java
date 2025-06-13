package com.example.nikhil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class HabitDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "habittracker.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_HABITS = "habits";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    public HabitDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HABITS_TABLE = "CREATE TABLE " + TABLE_HABITS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT"
                + ")";
        db.execSQL(CREATE_HABITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For now just drop and recreate
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HABITS);
        onCreate(db);
    }

    public void insertHabit(String habitName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, habitName);
        db.insert(TABLE_HABITS, null, values);
        db.close();
    }

    public ArrayList<Habit> getAllHabits() {
        ArrayList<Habit> habits = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HABITS,
                new String[]{COLUMN_ID, COLUMN_NAME},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                habits.add(new Habit(id, name));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return habits;
    }

    public void deleteHabit(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HABITS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
