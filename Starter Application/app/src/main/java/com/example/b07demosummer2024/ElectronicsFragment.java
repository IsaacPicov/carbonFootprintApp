package com.example.b07demosummer2024;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ElectronicsFragment extends Fragment {

    private EditText deviceTypeInput, deviceCountInput;
    private Button submitButton;
    private FirebaseAuth auth;
    private FirebaseDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_electronics, container, false);

        submitButton = view.findViewById(R.id.buttonElectronicsSubmit);
        deviceTypeInput = view.findViewById(R.id.editTextDeviceType);
        deviceCountInput = view.findViewById(R.id.editTextDeviceCount);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://b07finalproject-4e3be-default-rtdb.firebaseio.com/");

        submitButton.setOnClickListener(v -> {
            FirebaseUser currentUser = auth.getCurrentUser();
            if (currentUser != null) {
                String deviceType = deviceTypeInput.getText().toString().trim();
                String deviceCount = deviceCountInput.getText().toString().trim();
                addToDatabase(currentUser.getUid(), deviceType, deviceCount);
            } else {
                Toast.makeText(getContext(), "Login required", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void addToDatabase(String userId, String deviceType, String deviceCount) {

        DatabaseReference logRef;

        if (GlobalVariable.getDate() == null) {
            logRef = db.getReference("users").child(userId).child("dailylogs").child(LocalDate.now().toString());
        } else {
            logRef = db.getReference("users").child(userId).child("dailylogs").child(GlobalVariable.getDate());
        }
        String id = logRef.push().getKey();

        if (id != null) {
            Map<String, Object> activityData = new HashMap<>();
            activityData.put("activity_type", "consumption");
            activityData.put("information", Map.of(
                    "deviceType", deviceType,
                    "quantity", deviceCount
            ));

            logRef.child(id).setValue(activityData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
                    Calculate.calculateAndUpdateDailyTotal(userId);
                } else {
                    Toast.makeText(getContext(), "Failed to save user data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Failed to generate unique ID", Toast.LENGTH_SHORT).show();
        }
    }
}
