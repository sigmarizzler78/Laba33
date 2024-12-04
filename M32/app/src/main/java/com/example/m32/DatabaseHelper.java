package com.example.m32;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "odnogruppniki.db";
    public static final String TABLE_NAME = "odnogruppniki";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LAST_NAME = "lastName";
    public static final String COLUMN_FIRST_NAME = "firstName";
    public static final String COLUMN_MIDDLE_NAME = "middleName";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_MIDDLE_NAME + " TEXT, " +
                COLUMN_TIMESTAMP + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public void onCreateDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_MIDDLE_NAME + " TEXT, " +
                COLUMN_TIMESTAMP + " INTEGER)");
        db.close();
    }

    public void insertInitialData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);

        ContentValues values = new ContentValues();
        values.put(COLUMN_LAST_NAME, "Кастырин");
        values.put(COLUMN_FIRST_NAME, "Сергей");
        values.put(COLUMN_MIDDLE_NAME, "Алексеевич");
        values.put(COLUMN_TIMESTAMP, System.currentTimeMillis());
        db.insert(TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(COLUMN_LAST_NAME, "Перов");
        values.put(COLUMN_FIRST_NAME, "Иван");
        values.put(COLUMN_MIDDLE_NAME, "Романович");
        values.put(COLUMN_TIMESTAMP, System.currentTimeMillis());
        db.insert(TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(COLUMN_LAST_NAME, "Касаткин");
        values.put(COLUMN_FIRST_NAME, "Вадим");
        values.put(COLUMN_MIDDLE_NAME, "Владимирович");
        values.put(COLUMN_TIMESTAMP, System.currentTimeMillis());
        db.insert(TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(COLUMN_LAST_NAME, "Мищенко");
        values.put(COLUMN_FIRST_NAME, "Артем");
        values.put(COLUMN_MIDDLE_NAME, "Сергеевич");
        values.put(COLUMN_TIMESTAMP, System.currentTimeMillis());
        db.insert(TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(COLUMN_LAST_NAME, "Апанасов");
        values.put(COLUMN_FIRST_NAME, "Сергей");
        values.put(COLUMN_MIDDLE_NAME, "Батькович");
        values.put(COLUMN_TIMESTAMP, System.currentTimeMillis());
        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public boolean insertData(String lastName, String firstName, String middleName, long timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_MIDDLE_NAME, middleName);
        values.put(COLUMN_TIMESTAMP, timestamp);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    public boolean updateData(String lastName, String firstName, String middleName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_MIDDLE_NAME, middleName);

        long lastId = getLastInsertedId();
        if (lastId != -1) {
            int updatedRows = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(lastId)});
            db.close();
            return updatedRows > 0;
        } else {
            db.close();
            return false;
        }
    }

    private long getLastInsertedId() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(_id) FROM " + TABLE_NAME, null);
        long lastId = -1;
        if (cursor.moveToFirst()) {
            lastId = cursor.getLong(0);
        }
        cursor.close();
        return lastId;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
