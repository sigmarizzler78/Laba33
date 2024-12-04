package com.example.m32;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.getAllData();

        TextView textViewData = findViewById(R.id.textViewData);
        StringBuilder sb = new StringBuilder();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LAST_NAME));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FIRST_NAME));
                String middleName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_MIDDLE_NAME));
                long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIMESTAMP));
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
                String formattedDate = sdf.format(new Date(timestamp));

                sb.append("ID: ").append(id).append("\n");
                sb.append("Фамилия: ").append(lastName).append("\n");
                sb.append("Имя: ").append(firstName).append("\n");
                sb.append("Отчество: ").append(middleName).append("\n");
                sb.append("Время добавления: ").append(formattedDate).append("\n\n");
            } while (cursor.moveToNext());
        } else {
            sb.append("Нет данных");
        }

        cursor.close();
        textViewData.setText(sb.toString());
    }
}