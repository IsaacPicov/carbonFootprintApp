package com.example.b07demosummer2024;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

public class BillsFragment extends Fragment {

    private Spinner billTypeSpinner;
    private EditText billAmountInput;
    private Button submitButton;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference itemsRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bills, container, false);

        submitButton = view.findViewById(R.id.buttonBillsSubmit);
        billTypeSpinner = view.findViewById(R.id.spinnerBillType);
        billAmountInput = view.findViewById(R.id.editTextBillAmount);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://b07finalproject-4e3be-default-rtdb.firebaseio.com/");

        ArrayAdapter<CharSequence> billTypeAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.bill_types, android.R.layout.simple_spinner_item);
        billTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        billTypeSpinner.setAdapter(billTypeAdapter);

        submitButton.setOnClickListener(v -> {
            FirebaseUser currentUser = auth.getCurrentUser();
            if (currentUser != null) {
                String billType = billTypeSpinner.getSelectedItem().toString();
                String billAmount = billAmountInput.getText().toString().trim();
                addToDatabase(currentUser.getUid(), billType, billAmount);
            } else {
                Toast.makeText(getContext(), "Login required", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void addToDatabase(String userId, String billType, String billAmount) {

        DatabaseReference logRef;

        if (GlobalVariable.getDate() == null) {
            logRef = db.getReference("users").child(userId).child("dailylogs").child(LocalDate.now().toString());
        } else {
            logRef = db.getReference("users").child(userId).child("dailylogs").child(GlobalVariable.getDate());
        }
        String id = logRef.push().getKey(); // Generate a unique ID for the activity

        if (id != null) {
            Map<String, Object> activityData = new HashMap<>();
            activityData.put("activity_type", "energy");
            activityData.put("information", Map.of(
                    "billType", billType,
                    "amount", billAmount
            ));

            logRef.child(id).setValue(activityData).addOnCompleteListener(task -> {
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


