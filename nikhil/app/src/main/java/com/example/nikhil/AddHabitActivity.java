package com.example.nikhil;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddHabitActivity extends AppCompatActivity {

    EditText habitInput;
    Button saveBtn;
    HabitDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        habitInput = findViewById(R.id.habitInput);
        saveBtn = findViewById(R.id.saveHabitBtn);
        dbHelper = new HabitDBHelper(this);

        saveBtn.setOnClickListener(view -> {
            String title = habitInput.getText().toString().trim();
            if (!title.isEmpty()) {
                dbHelper.insertHabit(title);
                Toast.makeText(this, "Habit added!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please enter a habit", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
