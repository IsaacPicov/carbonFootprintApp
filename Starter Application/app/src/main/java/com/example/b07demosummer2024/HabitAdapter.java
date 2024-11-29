package com.example.b07demosummer2024;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder>{
    private List<Habit> habits;
    private OnHabitActionClickListener actionClickListener;

    //The button that will link to the calender to log stuff
    public interface OnHabitActionClickListener {
        void onActionClick(Habit habit);
    }
    //Take in the list of habits as well as a button
    public HabitAdapter(List<Habit> habits, OnHabitActionClickListener listener){
        this.habits = habits;
        this.actionClickListener = listener;

    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit = habits.get(position);
        holder.habitTitle.setText(habit.getTitle());

        holder.btnAction.setOnClickListener(v -> {
            if (actionClickListener != null) {
                actionClickListener.onActionClick(habit);
            }
        });
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public static class HabitViewHolder extends RecyclerView.ViewHolder {
        TextView habitTitle;
        Button btnAction;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            habitTitle = itemView.findViewById(R.id.habitTitle);
            btnAction = itemView.findViewById(R.id.btnAction);
        }
    }
}
