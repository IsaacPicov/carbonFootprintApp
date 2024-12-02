package com.example.b07demosummer2024;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditActivityDialog extends DialogFragment {

    private LinearLayout dynamicFieldContainer;
    private Button saveButton;
    private Map<String, String> informationAsMap;
    private String activityId;
    private DatabaseReference databaseRef;
    private Map<String, EditText> fieldInputs = new HashMap<>();

    public EditActivityDialog(Activity activity, String activityId, String userId, String date) {
        this.informationAsMap = activity.getInformationAsMap();
        this.activityId = activityId;

        databaseRef = FirebaseDatabase.getInstance().getReference("users")
                .child(userId)
                .child("dailylogs")
                .child(date)
                .child(activityId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_activity, container, false);

        dynamicFieldContainer = view.findViewById(R.id.dynamicFieldContainer);
        saveButton = view.findViewById(R.id.buttonSave);

        populateFields(informationAsMap);

        saveButton.setOnClickListener(v -> saveChanges());

        return view;
    }

    private void populateFields(Map<String, String> informationAsMap) {
        if (informationAsMap != null) {
            for (Map.Entry<String, String> entry : informationAsMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                EditText inputField = new EditText(getContext());
                inputField.setHint(key);
                inputField.setText(value);

                dynamicFieldContainer.addView(inputField);
                fieldInputs.put(key, inputField);
            }
        }
    }

    private void saveChanges() {
        Map<String, Object> updatedFields = new HashMap<>();

        for (Map.Entry<String, EditText> entry : fieldInputs.entrySet()) {
            updatedFields.put(entry.getKey(), entry.getValue().getText().toString());
        }

        databaseRef.child("information").updateChildren(updatedFields).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Activity updated successfully", Toast.LENGTH_SHORT).show();
                dismiss();
            } else {
                Toast.makeText(getContext(), "Failed to update activity", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

