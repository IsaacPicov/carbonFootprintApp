package com.example.b07demosummer2024;

import static com.example.b07demosummer2024.CountriesConstants.COUNTRIES;
import static com.example.b07demosummer2024.CountriesConstants.countryConstants;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class SurveyResultsActivity extends AppCompatActivity {
    double totalHousing;
    double totalFood;
    double totalTransportation;
    double totalConsumption;
    double totalCarbon;

    private Button homeBtn;

    private FirebaseDatabase db;
    private DatabaseReference itemsRef;

    private FirebaseAuth auth;

    public static final ArrayList<Integer> graphColours = new ArrayList<>();

    static {
        graphColours.add(Color.parseColor("#009999"));
        graphColours.add(Color.parseColor("#002F4B"));
        graphColours.add(Color.parseColor("#A8DADC"));
        graphColours.add(Color.parseColor("#4F83CC"));
    }

    private void setUserTakenSurvery(){
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        db = FirebaseDatabase.getInstance("https://b07finalproject-4e3be-default-rtdb.firebaseio.com/");
        String userId = currentUser.getUid();
        itemsRef = db.getReference("users/" + userId);
        System.out.println(itemsRef);
        // Set the value of "takenSurvey" to 1
        itemsRef.child("takenSurvey").setValue(1).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("FirebaseUpdate", "takenSurvey set to 1 successfully.");
            } else {
                Log.e("FirebaseUpdate", "Error setting takenSurvey: ", task.getException());
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        homeBtn = findViewById(R.id.button);
        homeBtn.setOnClickListener(v -> {
            Intent intent = getIntent();
            Intent pass = new Intent(this, EcoTrackerActivity.class);
            setUserTakenSurvery();
            startActivity(pass);
        });
//       Stuff for making the PieChart
        Intent carbonValues = getIntent();
        totalHousing = carbonValues.getDoubleExtra("HOUSING",0.0);
        totalFood = carbonValues.getDoubleExtra("FOOD", 0.0);
        totalConsumption = carbonValues.getDoubleExtra("CONSUMPTION", 0.0);
        totalTransportation = carbonValues.getDoubleExtra("TRANSPORTATION", 0.0);
        totalCarbon = totalConsumption +totalFood+totalTransportation+totalHousing;
        PieChart breakdown = (PieChart) findViewById(R.id.pieChart);
        BarChart byCountry = (BarChart) findViewById(R.id.barChart);

//        Adding entries for the pie chart
        ArrayList<PieEntry> sections = new ArrayList<>();
        sections.add(new PieEntry((float) totalHousing, "Housing"));
        sections.add(new PieEntry((float) totalFood, "Food"));
        sections.add(new PieEntry((float) totalConsumption, "Consumption"));
        sections.add(new PieEntry((float) totalTransportation, "Transportation"));

//        Styling the pie chart
        PieDataSet pieDataSet = new PieDataSet(sections, "");
        pieDataSet.setColors(graphColours);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16);

//
//        Adding the PieChart to the activity
        PieData pieData = new PieData(pieDataSet);

        breakdown.setData(pieData);
        breakdown.getDescription().setEnabled(false);
        breakdown.setCenterText("Carbon Output per Category (Tons)");
        breakdown.setDrawEntryLabels(false);
        breakdown.animate();

//        Stuff for making the Bar Graph
            String[] labels = {"Yours", "Canada"};
             XAxis xAxis = byCountry.getXAxis();
             xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    int index = (int) value;
                    if(index >= 0 && index < labels.length){
                        return labels[index];
                    }
                    else{
                        return "";
                    }
                 }
                });
             xAxis.setGranularity(1f);
             xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            ArrayList<BarEntry> compareCountry = new ArrayList<>();
            compareCountry.add(new BarEntry(0f, (float)(totalCarbon)));
            compareCountry.add(new BarEntry(1f, Objects.requireNonNull(countryConstants.get("Canada")).floatValue()));

            BarDataSet dataSet = new BarDataSet(compareCountry, "");
            dataSet.setColors(graphColours);
            dataSet.setValueTextColor(Color.BLACK);
            dataSet.setValueTextSize(16f);
            BarData data = new BarData(dataSet);
            byCountry.setFitBars(true);
            byCountry.setData(data);
            byCountry.animateY(2000);
            YAxis leftAxis = byCountry.getAxisLeft();
            YAxis rightAxis = byCountry.getAxisRight();
            XAxis xAxis1 = byCountry.getXAxis();

            byCountry.getDescription().setEnabled(true);
            byCountry.getDescription().setText("Your Carbon Output vs. Global Emissions per Capita");
            byCountry.getDescription().setTextSize(11f);
            byCountry.getDescription().setTextColor(Color.BLACK);
             byCountry.getDescription().setPosition(830f, 50f);

            byCountry.getLegend().setEnabled(false);


            leftAxis.setAxisMinimum(0f);
            leftAxis.setAxisMaximum(80f); // Adjust based on your expected range
            leftAxis.setGranularity(1f);
            rightAxis.setEnabled(false);


            xAxis1.setDrawGridLines(false);
            leftAxis.setDrawGridLines(false);
            data.setBarWidth(0.4f);
            byCountry.setExtraTopOffset(20f);



//        Defining stuff for Bar Graph Spinner
        Spinner countrySpinner = findViewById(R.id.countrySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                COUNTRIES
        );
//        Defining a Spinner for the Bar Graph
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);
        int defaultPosition = COUNTRIES.indexOf("Canada");
        if (defaultPosition != -1) {
            countrySpinner.setSelection(defaultPosition);
        }
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = parent.getItemAtPosition(position).toString();
                String[] userLabels = {"Yours", selectedCountry};
                xAxis1.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        int index = (int) value;
                        if(index >= 0 && index < userLabels.length){
                            return userLabels[index];
                        }
                        else{
                            return "";
                        }
                    }
                });
                ArrayList<BarEntry> updateEntries = new ArrayList<>();
                updateEntries.add(new BarEntry(0f, (float)totalCarbon));
                updateEntries.add(new BarEntry(1f, Objects.requireNonNull(countryConstants.get(selectedCountry)).floatValue()));
                BarDataSet newData = new BarDataSet(updateEntries, "Your Carbon Output vs. Global Emissions per Capita");
                newData.setColors(graphColours);
                newData.setValueTextColor(Color.BLACK);
                newData.setValueTextSize(16f);
                BarData updatedData = new BarData(newData);
                byCountry.setData(updatedData);
                byCountry.invalidate();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




    }



}
