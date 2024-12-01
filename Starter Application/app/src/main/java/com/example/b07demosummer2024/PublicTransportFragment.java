package com.example.b07demosummer2024;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.Map;

public class PublicTransportFragment extends Fragment {

    private EditText timeSpent;
    private Spinner transportation, unit;
    private Button submit;
    private FirebaseAuth auth;
    private FirebaseDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_publictransport, container, false);

        submit = view.findViewById(R.id.buttonSubmit);
        timeSpent = view.findViewById(R.id.editTextTimeSpent);
        transportation = view.findViewById(R.id.spinnerPublicTransport);
        ArrayAdapter<CharSequence> transportationAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.public_transport, android.R.layout.simple_spinner_item);
        transportationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transportation.setAdapter(transportationAdapter);

        unit = view.findViewById(R.id.spinnerTimeUnit);
        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.time_units, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit.setAdapter(unitAdapter);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://b07finalproject-4e3be-default-rtdb.firebaseio.com/");

        submit.setOnClickListener(v -> {
            FirebaseUser currentUser = auth.getCurrentUser();
            if (currentUser != null) {
                String transportType = transportation.getSelectedItem().toString().trim();
                String duration = timeSpent.getText().toString().trim();
                String timeUnit = unit.getSelectedItem().toString().trim();
                addToDatabase(currentUser.getUid(), transportType, duration, timeUnit);
            } else {
                Toast.makeText(getContext(), "Login required.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void addToDatabase(String userId, String transportType, String duration, String timeUnit) {
        DatabaseReference logRef = db.getReference("users").child(userId).child("dailylogs").child(LocalDate.now().toString());
        String id = logRef.push().getKey();

        if (id != null) {
            logRef.child(id).setValue(Map.of(
                    "activity_type", "transportation",
                    "information", Map.of(
                            "transportType", transportType,
                            "duration", duration,
                            "timeUnit", timeUnit
                    )
            )).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to save user data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Failed to generate unique ID", Toast.LENGTH_SHORT).show();
        }
    }
}
