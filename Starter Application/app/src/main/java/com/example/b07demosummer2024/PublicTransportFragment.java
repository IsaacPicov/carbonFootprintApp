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

public class PublicTransportFragment extends Fragment {


    private EditText timeSpent;

    private FirebaseAuth auth;
    private Spinner transportation, unit;

    private Button submit;
    private FirebaseDatabase db;
    private DatabaseReference itemsRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_publictransport, container, false);

        submit = view.findViewById(R.id.buttonSubmit);
        timeSpent = view.findViewById(R.id.editTextTimeSpent);
        transportation= view.findViewById(R.id.spinnerPublicTransport);
        ArrayAdapter<CharSequence> transportationAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.public_transport, android.R.layout.simple_spinner_item);
        transportationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transportation.setAdapter(transportationAdapter);



        unit = view.findViewById(R.id.spinnerTimeUnit);
        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.time_units, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit.setAdapter(unitAdapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                db = FirebaseDatabase.getInstance("https://b07finalproject-4e3be-default-rtdb.firebaseio.com/");
                FirebaseUser currentUser = auth.getCurrentUser();
                if(currentUser != null){
                    addToDatabase(currentUser.getUid(), transportation.getSelectedItem().toString().trim(), timeSpent.getText().toString().trim(), unit.getSelectedItem().toString().trim());
                }
                else{
                    Toast.makeText(getContext(), "Logged in requried.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


    private void addToDatabase(String userId, String transportation, String timeSpent, String unit) {
        itemsRef = db.getReference("users/" + userId + "/dailylogs/" + LocalDate.now() + "/transportation/publicTransport");

        itemsRef.push().setValue(new Object(){
            public String type = transportation;
            public String duration = timeSpent + " " + unit;
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to save user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
