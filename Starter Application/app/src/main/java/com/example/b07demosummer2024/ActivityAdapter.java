package com.example.b07demosummer2024;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {
    private List<Activity> activityList;
    private OnActivityClickListener listener;

    public ActivityAdapter(List<Activity> activityList, ActivityAdapter.OnActivityClickListener listener) {
        this.activityList = activityList;
        this.listener = listener;
    }

    public interface OnActivityClickListener {
        void onActivityClick(Activity activity);
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_activity_adapter, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        Activity activity = activityList.get(position);
        holder.textViewActivityType.setText(activity.getActivity_type());
        holder.textViewInformation.setText(activity.getInformation());

        holder.delete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onActivityClick(activity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView textViewInformation, textViewActivityType, textViewCO2e;

        Button delete;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewActivityType = itemView.findViewById(R.id.textViewActivityType);
            textViewInformation = itemView.findViewById(R.id.textViewInformation);
            textViewCO2e = itemView.findViewById(R.id.textViewCO2Emission);
            delete = itemView.findViewById(R.id.buttonDeleteActivity);
        }
    }
}
