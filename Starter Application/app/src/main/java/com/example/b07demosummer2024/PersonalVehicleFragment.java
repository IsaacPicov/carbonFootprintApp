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

public class PersonalVehicleFragment extends Fragment {

    private EditText distanceTravel;
    private Spinner units, vehicleType;
    private Button submit;
    private FirebaseAuth auth;
    private FirebaseDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personalvehicle, container, false);

        submit = view.findViewById(R.id.buttonPersonalVehicleSubmit);
        distanceTravel = view.findViewById(R.id.editTextDistanceTravel);
        units = view.findViewById(R.id.spinnerUnits);
        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.distance_units, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        units.setAdapter(unitAdapter);

        vehicleType = view.findViewById(R.id.spinnerVehicleType);
        ArrayAdapter<CharSequence> vehicleAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.vehicle_types, android.R.layout.simple_spinner_item);
        vehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleType.setAdapter(vehicleAdapter);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://b07finalproject-4e3be-default-rtdb.firebaseio.com/");

        submit.setOnClickListener(v -> {
            FirebaseUser currentUser = auth.getCurrentUser();
            if (currentUser != null) {
                String distance = distanceTravel.getText().toString().trim();
                String unit = units.getSelectedItem().toString();
                String selectedVehicleType = vehicleType.getSelectedItem().toString();
                addToDatabase(currentUser.getUid(), distance, unit, selectedVehicleType);
            } else {
                Toast.makeText(getContext(), "Login required.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void addToDatabase(String userId, String distanceTravel, String units, String vehicleType) {
        DatabaseReference logRef;

        if (GlobalVariable.getDate() == null) {
            logRef = db.getReference("users").child(userId).child("dailylogs").child(LocalDate.now().toString());
        } else {
            logRef = db.getReference("users").child(userId).child("dailylogs").child(GlobalVariable.getDate());
        }

        String id = logRef.push().getKey();

        if (id != null) {
            Map<String, Object> activityData = Map.of(
                    "activity_type", "transportation",
                    "information", Map.of(
                            "distance", distanceTravel,
                            "units", units,
                            "vehicleType", vehicleType
                    )
            );
            logRef.child(id).setValue(activityData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
                    //Calculate.calculateAndUpdateDailyTotal(userId);
                } else {
                    Toast.makeText(getContext(), "Failed to save user data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Failed to generate unique ID", Toast.LENGTH_SHORT).show();
        }
    }
}
