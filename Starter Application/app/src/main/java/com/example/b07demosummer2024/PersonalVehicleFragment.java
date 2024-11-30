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

public class PersonalVehicleFragment extends Fragment {


    private EditText distanceTravel;

    private FirebaseAuth auth;
    private Spinner units, vehicleType;

    private Button submit;
    private FirebaseDatabase db;
    private DatabaseReference itemsRef;

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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                db = FirebaseDatabase.getInstance("https://b07finalproject-4e3be-default-rtdb.firebaseio.com/");
                FirebaseUser currentUser = auth.getCurrentUser();
                if(currentUser != null){
                    addToDatabase(currentUser.getUid(), distanceTravel.getText().toString().trim(), units.getSelectedItem().toString(), vehicleType.getSelectedItem().toString());
                }
                else{
                    Toast.makeText(getContext(), "Logged in requried.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


    private void addToDatabase(String userId, String distanceTravel, String units, String vehicleType) {
        if (GlobalVariable.getDate().equals(null)){
            itemsRef = db.getReference("users/" + userId + "/dailylogs/" + LocalDate.now() + "/transportation/vehicle");
        }
        else{
            itemsRef = db.getReference("users/" + userId + "/dailylogs/" + GlobalVariable.getDate() + "/transportation/vehicle");
        }



        itemsRef.push().setValue(new Object(){
            public String distance = distanceTravel + units;
            public String type = vehicleType;
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to save user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
