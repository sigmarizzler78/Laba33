package com.example.m32;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button buttonShowData = findViewById(R.id.buttonShowData);
        Button buttonAddData = findViewById(R.id.buttonAddData);
        Button buttonUpdateData = findViewById(R.id.buttonUpdateData);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.onCreateDatabase();
        dbHelper.insertInitialData();


        buttonShowData.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });

        buttonAddData.setOnClickListener(v -> {
            dbHelper.insertData("Новый", "Одногруппник", "", System.currentTimeMillis());
            Toast.makeText(this, "Запись добавлена", Toast.LENGTH_SHORT).show();
        });

        buttonUpdateData.setOnClickListener(v -> {
            boolean success = dbHelper.updateData("Иванов", "Иван", "Иванович");
            if (success) {
                Toast.makeText(this, "Запись обновлена", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ошибка обновления записи", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}