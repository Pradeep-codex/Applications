package com.example.nikhil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    HabitDBHelper dbHelper;
    RecyclerView recyclerView;
    HabitAdapter adapter;
    ArrayList<Habit> habits;
    Button addHabit;
    TextView noHabitsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new HabitDBHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        addHabit = findViewById(R.id.addHabitBtn);
        noHabitsText = findViewById(R.id.noHabitsText);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadHabits();

        addHabit.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddHabitActivity.class));
        });
    }

    private void loadHabits() {
        habits = dbHelper.getAllHabits();

        if (habits.isEmpty()) {
            noHabitsText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noHabitsText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new HabitAdapter(this, habits, dbHelper);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHabits();
    }
}
