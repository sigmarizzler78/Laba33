package com.example.myapplication3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "odnogruppniki.db";
    public static final String TABLE_NAME = "odnogruppniki";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIO = "fio";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIO + " TEXT, " +
                COLUMN_TIMESTAMP + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void onCreateDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIO + " TEXT, " +
                COLUMN_TIMESTAMP + " INTEGER)");
        db.close();

    }


    public void insertInitialData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null); // Удаляем все записи перед добавлением новых

        ContentValues values = new ContentValues();
        values.put(COLUMN_FIO, "Кастырин Сегрей Алексеевич");
        values.put(COLUMN_TIMESTAMP, System.currentTimeMillis());
        db.insert(TABLE_NAME, null, values);

        values.put(COLUMN_FIO, "Перов Иван Романович");
        values.put(COLUMN_TIMESTAMP, System.currentTimeMillis());
        db.insert(TABLE_NAME, null, values);

        values.put(COLUMN_FIO, "Касаткин Вадим Владимирович");
        values.put(COLUMN_TIMESTAMP, System.currentTimeMillis());
        db.insert(TABLE_NAME, null, values);

        values.put(COLUMN_FIO, "Мищенко Артем Сергеевич");
        values.put(COLUMN_TIMESTAMP, System.currentTimeMillis());
        db.insert(TABLE_NAME, null, values);

        values.put(COLUMN_FIO, "Апанасов Сергей Батькович");
        values.put(COLUMN_TIMESTAMP, System.currentTimeMillis());
        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public boolean insertData(String fio, long timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIO, fio);
        values.put(COLUMN_TIMESTAMP, timestamp);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    public boolean updateData(String newFio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIO, newFio);

        // Получаем ID последней записи
        long lastId = getLastInsertedId();

        if (lastId != -1) { // Проверка на существование записи
            int updatedRows = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(lastId)});
            db.close();
            return updatedRows > 0;
        } else {
            db.close();
            return false; // Запись не найдена
        }
    }

    // Метод для получения ID последней вставленной записи
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
