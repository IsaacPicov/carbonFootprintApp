package com.example.b07demosummer2024;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
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

public class CO2eDisplayFragment extends Fragment {


    private CalendarView calendarView;
    private RecyclerView activityRecyclerView;
    private Calendar calendar;

    private List<Activity> activityLists;

    private FirebaseAuth auth;

    private ActivityAdapter activityAdapter;

    private Button add;
    private FirebaseDatabase db;
    private DatabaseReference itemsRef;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://b07finalproject-4e3be-default-rtdb.firebaseio.com/");

        View view = inflater.inflate(R.layout.fragment_daily_co2e_display, container, false);
        calendar = Calendar.getInstance();
        calendarView = view.findViewById(R.id.calendarView);
        LocalDate currentDate = LocalDate.now();
        setDate(currentDate.getDayOfMonth(), currentDate.getMonthValue(), currentDate.getYear());
//
        activityRecyclerView = view.findViewById(R.id.activityRecyclerView);
        activityRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
        activityLists = new ArrayList<>();
        activityAdapter = new ActivityAdapter(activityLists, listener -> {
            // Handle button click for a specific habit
            // Example: Display a toast or perform an action
               deleteFromDatabase(listener.getId());

            });
        activityRecyclerView.setAdapter(activityAdapter);

        add = view.findViewById(R.id.buttonAddActivity);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                setDate(dayOfMonth, month, year);
                month += 1;
                String monthValue = String.valueOf(month).length() > 1 ? String.valueOf(month): "0" + month;
                String dayValue = String.valueOf(dayOfMonth).length() > 1 ? String.valueOf(dayOfMonth): "0" + dayOfMonth;
                String date = year + "-" + monthValue + "-" + dayValue;
                System.out.println(date);
                fetchItemsFromDatabase(date);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariable.setDate(calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
                loadFragment(new EcoTrackerFragment());
            }
        });
        return view;
    }



//    private void
    private void setDate(int day, int month, int year){
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        long milli = calendar.getTimeInMillis();
        calendarView.setDate(milli);
    }


    private void fetchItemsFromDatabase(String date) {
        FirebaseUser currentUser = auth.getCurrentUser();
        String userId = currentUser.getUid();
        itemsRef = db.getReference("users/" + userId + "/dailylogs/" + date);
        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                activityLists.clear();
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        System.out.println(snapshot.getKey());
                        System.out.println(snapshot.child("activity_type").getValue());
                        System.out.println(snapshot.child("information").getValue());
                        Activity activity = new Activity(snapshot.getKey(), snapshot.child("activity_type").getValue().toString(), snapshot.child("information").getValue().toString());
                        activityLists.add(activity);
                    }
                }
                else{
                    activityLists.add(new Activity("","No Activity", ""));
                }

                activityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });

    }

    private void deleteFromDatabase(String id) {
        FirebaseUser currentUser = auth.getCurrentUser();
        String userId = currentUser.getUid();
        String month = String.valueOf(Math.abs(calendar.get(Calendar.MONTH))).length() > 1 ? String.valueOf(Math.abs(calendar.get(Calendar.MONTH) + 1)) : "0" + calendar.get(Calendar.MONTH) + 1;
        String day = String.valueOf(Math.abs(calendar.get(Calendar.DAY_OF_MONTH))).length() > 1 ? String.valueOf(Math.abs(calendar.get(Calendar.DAY_OF_MONTH))) : "0" + calendar.get(Calendar.DAY_OF_MONTH);
        String date = calendar.get(Calendar.YEAR) + "-" + month + "-" + day;
        System.out.println(userId);
        System.out.println(date);
        System.out.println(id);
        itemsRef = db.getReference("users/" + userId + "/dailylogs/" + date);
        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(id)) {
                        snapshot.getRef().removeValue().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Activity deleted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Failed to delete activity", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

