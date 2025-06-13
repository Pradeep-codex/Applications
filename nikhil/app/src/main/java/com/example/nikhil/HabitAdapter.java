package com.example.nikhil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {

    private Context context;
    private ArrayList<Habit> habitList;
    private HabitDBHelper dbHelper;

    public HabitAdapter(Context context, ArrayList<Habit> habitList, HabitDBHelper dbHelper) {
        this.context = context;
        this.habitList = habitList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.habit_item, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit = habitList.get(position);
        holder.habitName.setText(habit.getName());

        holder.deleteBtn.setOnClickListener(v -> {
            dbHelper.deleteHabit(habit.getId());
            habitList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, habitList.size());
            Toast.makeText(context, "Habit deleted", Toast.LENGTH_SHORT).show();

            // Show "no habits" if empty
            if (habitList.isEmpty() && context instanceof MainActivity) {
                ((MainActivity) context).runOnUiThread(() -> {
                    ((MainActivity) context).findViewById(R.id.noHabitsText).setVisibility(View.VISIBLE);
                    ((MainActivity) context).findViewById(R.id.recyclerView).setVisibility(View.GONE);
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    static class HabitViewHolder extends RecyclerView.ViewHolder {
        TextView habitName;
        ImageButton deleteBtn;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            habitName = itemView.findViewById(R.id.habitNameTextView);
            deleteBtn = itemView.findViewById(R.id.deleteHabitBtn);
        }
    }
}
