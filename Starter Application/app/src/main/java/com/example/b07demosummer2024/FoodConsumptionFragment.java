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
import java.util.HashMap;
import java.util.Map;

public class FoodConsumptionFragment extends Fragment {

    private EditText servings;
    private Spinner mealType;
    private Button submit;
    private FirebaseAuth auth;
    private FirebaseDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_food_consumption, container, false);

        submit = view.findViewById(R.id.buttonSubmitMeal);
        servings = view.findViewById(R.id.editTextServings);
        mealType = view.findViewById(R.id.spinnerMealType);

        ArrayAdapter<CharSequence> mealTypeAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.meal_types, android.R.layout.simple_spinner_item);
        mealTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealType.setAdapter(mealTypeAdapter);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://b07finalproject-4e3be-default-rtdb.firebaseio.com/");

        submit.setOnClickListener(v -> {
            FirebaseUser currentUser = auth.getCurrentUser();
            if (currentUser != null) {
                String selectedMealType = mealType.getSelectedItem().toString().trim();
                String enteredServings = servings.getText().toString().trim();
                addToDatabase(currentUser.getUid(), selectedMealType, enteredServings);
            } else {
                Toast.makeText(getContext(), "Login required to submit data.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void addToDatabase(String userId, String mealType, String servings) {
        DatabaseReference logRef = db.getReference("users").child(userId).child("dailylogs").child(LocalDate.now().toString());
        String id = logRef.push().getKey();

        if (id != null) {
            Map<String, Object> activityData = new HashMap<>();
            activityData.put("activity_type", "food");
            activityData.put("information", Map.of(
                    "mealType", mealType,
                    "servings", servings
            ));

            logRef.child(id).setValue(activityData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Meal data saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to save meal data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Failed to generate unique ID", Toast.LENGTH_SHORT).show();
        }
    }
}
