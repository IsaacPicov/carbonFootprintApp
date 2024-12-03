package com.example.b07demosummer2024;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Map;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {
    private List<Activity> activityList;
    private OnActivityClickListener listener;

    public ActivityAdapter(List<Activity> activityList, OnActivityClickListener listener) {
        this.activityList = activityList;
        this.listener = listener;
    }

    public interface OnActivityClickListener {
        void onActivityDeleteClick(Activity activity);
        void onActivityEditClick(Activity activity);
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

        // Convert the information map to a readable string
        Map<String, String> informationMap = activity.getInformationAsMap();
        StringBuilder informationString = new StringBuilder();
        for (Map.Entry<String, String> entry : informationMap.entrySet()) {
            informationString.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        holder.textViewInformation.setText(informationString.toString().trim()); // Set the formatted string

        holder.delete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onActivityDeleteClick(activity);
            }
        });

        holder.edit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onActivityEditClick(activity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView textViewInformation, textViewActivityType, textViewCO2e;
        Button delete, edit;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewActivityType = itemView.findViewById(R.id.textViewActivityType);
            textViewInformation = itemView.findViewById(R.id.textViewInformation);
            textViewCO2e = itemView.findViewById(R.id.textViewCO2Emission);
            delete = itemView.findViewById(R.id.buttonDeleteActivity);
            edit = itemView.findViewById(R.id.buttonEdit);
        }
    }
}
