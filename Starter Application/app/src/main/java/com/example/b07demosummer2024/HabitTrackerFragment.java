package com.example.b07demosummer2024;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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
            if (habit.getType().equals("transportation") && habit.getTitle().equals("Drive Personal Vehicle")){
                navigateToPersonalVehicleFragment();

            }

            if (habit.getType().equals("transportation") && habit.getTitle().equals("Take Public Transportation")){
                navigateToTakePublicTransportation();
            }

            if (habit.getType().equals("transportation") && habit.getTitle().equals("Cycling or Walking")){
                navigateToCycleOrWalk();
            }

            if (habit.getType().equals("transportation") && habit.getTitle().equals("Flight (Short-Haul or Long-Haul)")){
                navigateToFlight();
            }

            if (habit.getType().equals("food consumption") && habit.getTitle().equals("Meal")){
                navigateToMeal();
            }

            if (habit.getType().equals("consumption and shopping activities") && habit.getTitle().equals("Buy New Clothes") ){
                navigateToBuyClothes();
            }

            if (habit.getType().equals("consumption and shopping activities") && habit.getTitle().equals("Buy Electronics")){
                navigateToBuyElectronics();
            }

            if (habit.getType().equals("consumption and shopping activities") && habit.getTitle().equals("Other purchases")){
                navigateToOtherPurchases();
            }

            if (habit.getType().equals("consumption and shopping activities") && habit.getTitle().equals("Energy Bills")){
                navigateToEnergyBills();
            }
        });
        recyclerView.setAdapter(habitAdapter);


        // Spinner setup
        String[] items = {"Food & Consumption Activities", "Consumption & Shopping Activities", "transportation"};
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
                filterData(searchBar.getQuery().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filterData(searchBar.getQuery().toString());
            }
        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return true;
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
        boolean isSpinnerUsed = spinner.getSelectedItemPosition() != AdapterView.INVALID_POSITION; // Check if spinner is used
        boolean isSearchViewUsed = query != null && !query.isEmpty(); // Check if search query is entered

        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("habits");
        Query resultQuery;

        if (isSpinnerUsed && isSearchViewUsed) {
            // Filter by both type and search query
            resultQuery = dbref.orderByChild("type").equalTo(selectedType);
        } else if (isSpinnerUsed) {
            // Filter by type only
            resultQuery = dbref.orderByChild("type").equalTo(selectedType);
        } else if (isSearchViewUsed) {
            // Filter by search query only
            resultQuery = dbref.orderByChild("title");
        } else {
            // Neither spinner nor search is used; fetch all
            resultQuery = dbref;
        }

        resultQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                habitList.clear();
                for (DataSnapshot habitSnapshot : snapshot.getChildren()) {
                    Habit habit = habitSnapshot.getValue(Habit.class);
                    if (habit != null) {
                        boolean matches = true;

                        if (isSpinnerUsed && !habit.getType().equalsIgnoreCase(selectedType)) {
                            matches = false;
                        }
                        if (isSearchViewUsed && (habit.getTitle() == null || !habit.getTitle().toLowerCase().contains(query.toLowerCase()))) {
                            matches = false;
                        }

                        if (matches) {
                            habitList.add(habit);
                        }
                    }
                }

                habitAdapter.notifyDataSetChanged();
                if (habitList.isEmpty()) {
                    resultView.setText("No habits match your filters.");
                } else {
                    resultView.setText(""); // Clear the result view if habits are displayed
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                resultView.setText("Error: " + error.getMessage());
            }
        });
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

    private static final String CHANNEL_ID = "habit_reminder_channel";
    private static final int NOTIFICATION_ID = 1; // Unique ID for the notification

    private void remindActivity(String title) {
        // Step 1: Create a NotificationChannel (required for API 26+)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "Habit Reminder";
            String description = "Channel for habit reminder notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Step 2: Build the notification
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
//                .setSmallIcon(R.drawable.notification_icon) // Replace with your app's icon
//                .setContentTitle("Remember to log your activity: " + title + " today")
//                .setContentText("Tap to log your activity now!")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Set priority for older devices
//                .setAutoCancel(true); // Dismiss notification when clicked
//
//        // Step 3: Get the NotificationManager and send the notification
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
//        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void navigateToPersonalVehicleFragment(){
        PersonalVehicleFragment dest = new PersonalVehicleFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, dest);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToTakePublicTransportation(){
        PublicTransportFragment dest = new PublicTransportFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, dest);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToCycleOrWalk(){
        CyclingWalkingFragment dest = new CyclingWalkingFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, dest);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToFlight(){
        FlightFragment dest = new FlightFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, dest);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToMeal(){
        FoodConsumptionFragment dest = new FoodConsumptionFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, dest);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToBuyClothes(){
        ClothesFragment dest = new ClothesFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, dest);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToBuyElectronics(){
        ElectronicsFragment dest = new ElectronicsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, dest);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToOtherPurchases(){
        BillsFragment dest = new BillsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, dest);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToEnergyBills(){
        BillsFragment dest = new BillsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, dest);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
