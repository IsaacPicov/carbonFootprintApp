package com.example.b07demosummer2024;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HabitTrackerFragment extends Fragment {
    private RecyclerView recyclerView;
    private HabitAdapter habitAdapter;
    private List<Habit> habitList = new ArrayList<>();

    private Button btnFetchHabit;
    private Spinner spinner;
    private TextView selectedItemText;
    private SearchView searchBar;
    private TextView resultView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Habits");

    public HabitTrackerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_habit_tracker, container, false);

        // Initialize views
        spinner = view.findViewById(R.id.spinner);
        selectedItemText = view.findViewById(R.id.selection);
        btnFetchHabit = view.findViewById(R.id.btnFetchHabit);
        searchBar = view.findViewById(R.id.searchBar);
        resultView = view.findViewById(R.id.resultView); // Result view for displaying habits
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        habitAdapter = new HabitAdapter(habitList, habit -> {
            // Handle button click for a specific habit
            // Example: Display a toast or perform an action
            Toast.makeText(getContext(), "Action clicked for: " + habit.getTitle(), Toast.LENGTH_SHORT).show();
            if (habit.getType().equals("transportation") && habit.getTitle().equals("Personal Vehicle")){
                navigateToPersonalVehicleFragment();

            }
        });
        recyclerView.setAdapter(habitAdapter);


        // Spinner setup
        String[] items = {"consumption", "energy", "transportation"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                items
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedItem = items[position];
                // Display the selected item in the TextView
                selectedItemText.setText("Selected: " + selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedItemText.setText("Nothing selected");
            }
        });

        btnFetchHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected item from the spinner
                String selectedItem = spinner.getSelectedItem().toString();
                // Fetch habits based on the selected item
                fetchHabitsByType(selectedItem);
            }
        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission
                filterData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle text changes for real-time search
                filterData(newText);
                return true;
            }
        });

        return view;
    }

    private void filterData(String query) {
        String selectedType = spinner.getSelectedItem().toString();

        // Reference to the database
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("habits");

        // Query to filter by both title and type
        Query resultQuery = dbref.orderByChild("type").equalTo(selectedType);

        resultQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StringBuilder filteredResults = new StringBuilder();
                for (DataSnapshot habitSnapshot : snapshot.getChildren()) {
                    // Assuming each habit has a 'title' field
                    String habitTitle = habitSnapshot.child("title").getValue(String.class);
                    if (habitTitle != null && habitTitle.toLowerCase().contains(query.toLowerCase())) {
                        filteredResults.append(habitTitle).append("\n");
                    }
                }

                // Display filtered habits in the result view
                if (filteredResults.length() > 0) {
                    resultView.setText(filteredResults.toString());
                } else {
                    resultView.setText("No habits found for the search query.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                resultView.setText("Error: " + error.getMessage());
            }
        });
    }

    private void navigateToPersonalVehicleFragment(){
        PersonalVehicleFragment dest = new PersonalVehicleFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, dest);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void fetchHabitsByType(String selectedItem) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("habits");

        Query query = databaseReference.orderByChild("type").equalTo(selectedItem);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                habitList.clear();
                for (DataSnapshot habitSnapshot : snapshot.getChildren()) {
                    Habit habit = habitSnapshot.getValue(Habit.class);
                    if (habit != null) {
                        habitList.add(habit);
                    }
                }
                habitAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
