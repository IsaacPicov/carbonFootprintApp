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

    private Calendar calendar;
    private EditText distanceTravel;

    private FirebaseAuth auth;
    private Spinner units, vehicleType;

    private Button submit;
    private FirebaseDatabase db;
    private DatabaseReference itemsRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_daily_co2e_display, container, false);
        calendar = Calendar.getInstance();
        calendarView = view.findViewById(R.id.calendarView);
        LocalDate currentDate = LocalDate.now();
        setDate(currentDate.getDayOfMonth(), currentDate.getMonthValue(), currentDate.getYear());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getContext(), "Data selected" + year + month + dayOfMonth, Toast.LENGTH_SHORT).show();
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


    private void addToDatabase(String userId, String distanceTravel, String units) {
        itemsRef = db.getReference("users/" + userId + "/dailylogs/" + LocalDate.now() + "/transportation/cycle_walk");

        itemsRef.push().setValue(new Object(){
            public String distance = distanceTravel + units;

        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to save user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
