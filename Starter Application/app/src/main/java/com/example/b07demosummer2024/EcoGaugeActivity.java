package com.example.b07demosummer2024;

import static com.example.b07demosummer2024.CountriesConstants.COUNTRIES;
import static com.example.b07demosummer2024.CountriesConstants.countryConstants;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class EcoGaugeActivity extends AppCompatActivity {
    Button weekly;
    Button yearly;
    Button monthly;
    int counter;
    DatabaseReference ref;
    Double totalHousing;
    Double totalFood;
    Double totalConsumption;
    Double totalTransportation;
    Double totalCarbon;
    Double [] carbonHistory;
    TextView carbonCount;
    TextView timeframe;
    PieChart breakdown;
    BarChart byCountry;
    LineChart history;
    String userID;
    HashMap<String, Double> countryConstantsMonthly ;
    HashMap<String, Double> countryConstantsWeekly;
    XAxis xAxisLine;
    public static final ArrayList <Integer> graphColours = new ArrayList<>();
    static{
        graphColours.add(Color.parseColor("#009999"));
        graphColours.add(Color.parseColor("#002F4B"));
        graphColours.add(Color.parseColor("#A8DADC"));
        graphColours.add(Color.parseColor("#4F83CC"));
    }

    protected void onCreate(Bundle savedInstanceState) {
//      Default setting the gauge to display annual
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecogauge);

        fillHashmaps();

        weekly = findViewById(R.id.Weekly);
        monthly = findViewById(R.id.Monthly);
        yearly = findViewById(R.id.Yearly);
        carbonCount = findViewById(R.id.carbonByTimeframe);
        timeframe = findViewById(R.id.month_day_year);
        breakdown = (PieChart) findViewById(R.id.pieChart);
        byCountry = (BarChart) findViewById(R.id.barChart);
        userID = getIntent().getStringExtra("USERID");
        countryConstantsMonthly = new HashMap<>();
        countryConstantsWeekly = new HashMap<>();
        Spinner countrySpinner = findViewById(R.id.countrySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                COUNTRIES
        );

        ref = FirebaseDatabase.getInstance("https://b07finalproject-4e3be-default-rtdb.firebaseio.com/").getReference();


        setVarsYearly();

        carbonCount.setText(totalCarbon + "kg C02e");
        timeframe.setText("This Year");

//        Pie Chart
//        Dataset
        ArrayList<PieEntry> sections = new ArrayList<>();
        sections.add(new PieEntry(totalHousing.floatValue(), "Housing"));
        sections.add(new PieEntry(totalFood.floatValue(), "Food"));
        sections.add(new PieEntry(totalConsumption.floatValue(), "Consumption"));
        sections.add(new PieEntry(totalTransportation.floatValue(), "Transportation"));

//        Style
        PieDataSet pieDataSet = new PieDataSet(sections, "");
        pieDataSet.setColors(graphColours);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16);

//        lablleing and animating
        PieData pieData = new PieData(pieDataSet);
        breakdown.setData(pieData);
        breakdown.getDescription().setEnabled(false);
        breakdown.setCenterText("Carbon Output per Category (Tons)");
        breakdown.setDrawEntryLabels(false);
        breakdown.animate();
        breakdown.invalidate();
        breakdown.animateY(1000); // <-- everytime thing

//       Bar Graph
//        Styling
        String[] labels = {"Yours", "Canada"};
        XAxis xAxis = byCountry.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < labels.length) {
                    return labels[index];
                } else {
                    return "";
                }
            }
        });
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        ArrayList<BarEntry> compareCountry = new ArrayList<>();
        compareCountry.add(new BarEntry(0f, (totalCarbon.floatValue())));
        compareCountry.add(new BarEntry(1f, Objects.requireNonNull(countryConstants.get("Canada")).floatValue()));

//        Colours and animations and more text styling
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
        byCountry.getDescription().setPosition(720f, 50f);

        byCountry.getLegend().setEnabled(false);


        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(80f);
        leftAxis.setGranularity(1f);
        rightAxis.setEnabled(false);


        xAxis1.setDrawGridLines(false);
        leftAxis.setDrawGridLines(false);
        data.setBarWidth(0.4f);
        byCountry.setExtraTopOffset(20f);


//        Defining stuff for Bar Graph Spinner

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
                        if (index >= 0 && index < userLabels.length) {
                            return userLabels[index];
                        } else {
                            return "";
                        }
                    }
                });
                ArrayList<BarEntry> updateEntries = new ArrayList<>();
//                Changes the scale of carbon by country depending on if it's weeks,months, years
                if(counter == 3){
                updateEntries.add(new BarEntry(0f, totalCarbon.floatValue()));
                updateEntries.add(new BarEntry(1f, Objects.requireNonNull(countryConstants.get(selectedCountry)).floatValue()));}
                if(counter == 2){
                    updateEntries.add(new BarEntry(0f, totalCarbon.floatValue()));
                    updateEntries.add(new BarEntry(1f, Objects.requireNonNull(countryConstantsMonthly.get(selectedCountry)).floatValue()));
                }
                if(counter == 1){
                    updateEntries.add(new BarEntry(0f, totalCarbon.floatValue()));
                    updateEntries.add(new BarEntry(1f, Objects.requireNonNull(countryConstantsWeekly.get(selectedCountry)).floatValue()));
                }
//                sets the values
                BarDataSet newData = new BarDataSet(updateEntries, "Your Carbon Output vs. Global Emissions per Capita");
                newData.setColors(graphColours);
                newData.setValueTextColor(Color.BLACK);
                newData.setValueTextSize(16f);
                BarData updatedData = new BarData(newData);
                byCountry.setData(updatedData);
                byCountry.invalidate();
                byCountry.animate();


//              Defining Stuff for the line chart
//                TODO(not a todo just wanted green) From line 221 - 236 + line 242-243 is what you need to put into the other modules
//                String [] xLabels = arrayOf{ assume I will get all the years from the final product);
//                TODO implement xLabels once I know how I'm reciving the data

                history = findViewById(R.id.lineChart);
                ArrayList<Entry> entries = new ArrayList<>();
                entries.add(new Entry(0, totalCarbon.floatValue())); //for now we only know of year one from carbon survey
//                TODO add more entries when I know how the data is stored

                LineDataSet dataSet = new LineDataSet(entries, "The default is for years");
                dataSet.setColor(Color.parseColor("#009999"));
                LineData lineData = new LineData(dataSet);
                history.setData(lineData);

                xAxisLine = history.getXAxis();
//                xAxisLine.setValueFormatter(new IndexAxisValueFormatter(xLabels)); TODO implement this line once I have the values from tracker
                xAxisLine.setGranularity(1f);
                xAxisLine.setGranularityEnabled(true);
                xAxisLine.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxisLine.setDrawGridLines(false);

                history.animateXY(1000,1000); //call this for everytime you update
                history.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    void onWeekly(View view) {
//        Reference the firebase with userID as the key and grab the associated data
        counter =1;
        setVarsWeekly();
//        Emissions Overview
        carbonCount.setText(totalCarbon + "kg C02e");
        timeframe.setText("This Week");

//    Pie Chart
        ArrayList<PieEntry> sections = new ArrayList<>();
        sections.add(new PieEntry(totalHousing.floatValue(), "Housing"));
        sections.add(new PieEntry(totalFood.floatValue(), "Food"));
        sections.add(new PieEntry(totalConsumption.floatValue(), "Consumption"));
        sections.add(new PieEntry(totalTransportation.floatValue(), "Transportation"));

        PieDataSet pieDataSet = new PieDataSet(sections, "");
        pieDataSet.setColors(graphColours);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16);

        PieData pieData = new PieData(pieDataSet);
        breakdown.setData(pieData);
        breakdown.invalidate();
        breakdown.animate();
        breakdown.animateY(1000);


    }

        void onMonthly(View view) {
            counter = 2;
            setVarsMonthly();
//        Emissions Overview
            carbonCount.setText(totalCarbon + "kg C02e");
            timeframe.setText("This Month");
//       Pie Chart
            ArrayList<PieEntry> sections = new ArrayList<>();
            sections.add(new PieEntry(totalHousing.floatValue(), "Housing"));
            sections.add(new PieEntry(totalFood.floatValue(), "Food"));
            sections.add(new PieEntry(totalConsumption.floatValue(), "Consumption"));
            sections.add(new PieEntry(totalTransportation.floatValue(), "Transportation"));

            PieDataSet pieDataSet = new PieDataSet(sections, "");
            pieDataSet.setColors(graphColours);
            pieDataSet.setValueTextColor(Color.BLACK);
            pieDataSet.setValueTextSize(16);

            PieData pieData = new PieData(pieDataSet);
            breakdown.setData(pieData);
            breakdown.invalidate();
            breakdown.animate();
            breakdown.animateY(1000);
        }

        void onYearly(View view) {
            counter =3;
            setVarsYearly();
//        Emissions Overview
            carbonCount.setText(totalCarbon + "kg C02e");
            timeframe.setText("This Year");
//        Pie Chart
            ArrayList<PieEntry> sections = new ArrayList<>();
            sections.add(new PieEntry(totalHousing.floatValue(), "Housing"));
            sections.add(new PieEntry(totalFood.floatValue(), "Food"));
            sections.add(new PieEntry(totalConsumption.floatValue(), "Consumption"));
            sections.add(new PieEntry(totalTransportation.floatValue(), "Transportation"));

            PieDataSet pieDataSet = new PieDataSet(sections, "");
            pieDataSet.setColors(graphColours);
            pieDataSet.setValueTextColor(Color.BLACK);
            pieDataSet.setValueTextSize(16);

            PieData pieData = new PieData(pieDataSet);
            breakdown.setData(pieData);
            breakdown.invalidate();
            breakdown.animate();
            breakdown.animateY(1000);



        }

        //    Grabbing vars based on timeframe
        void setVarsYearly() {
            ref.child("users").child(userID).child("SurveyData").child("Transportation").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Get the value at "Total"
                        totalTransportation = dataSnapshot.getValue(Double.class);
                    } else {
                        Log.d("Firebase", "No value found at this path!");
                        totalTransportation = null;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Firebase", "Error fetching data: " + databaseError.getMessage());
                    totalTransportation = null;
                }
            });
            ref.child("users").child(userID).child("SurveyData").child("Food").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Get the value at "Total"
                        totalFood = dataSnapshot.getValue(Double.class);
                    } else {
                        Log.d("Firebase", "No value found at this path!");
                        totalFood = null;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Firebase", "Error fetching data: " + databaseError.getMessage());
                    totalFood = null;
                }
            });
            ref.child("users").child(userID).child("SurveyData").child("Consumption").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Get the value at "Total"
                        totalConsumption = dataSnapshot.getValue(Double.class);
                    } else {
                        Log.d("Firebase", "No value found at this path!");
                        totalConsumption = null;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Firebase", "Error fetching data: " + databaseError.getMessage());
                    totalConsumption = null;
                }
            });
            ref.child("users").child(userID).child("SurveyData").child("Housing").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Get the value at "Total"
                        totalHousing = dataSnapshot.getValue(Double.class);
                    } else {
                        Log.d("Firebase", "No value found at this path!");
                        totalHousing = null;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Firebase", "Error fetching data: " + databaseError.getMessage());
                    totalHousing = null;
                }
            });
            ref.child("users").child(userID).child("SurveyData").child("Total").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Get the value at "Total"
                        totalCarbon = dataSnapshot.getValue(Double.class);
                    } else {
                        Log.d("Firebase", "No value found at this path!");
                        totalCarbon = null;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Firebase", "Error fetching data: " + databaseError.getMessage());
                    totalCarbon = null;
                }
            });

//                ref.child("users").child(userID).child(the path to user history for annual data) {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()) {
//                            // Get the value at "Total"
//                            carbonHistory = dataSnapshot.getValue(Double.class);
//                        } else {
//                            Log.d("Firebase", "No value found at this path!");
//                            carbonHistory = null;
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.e("Firebase", "Error fetching data: " + databaseError.getMessage());
//                        carbonHistory = null;
//                    }
//                });

            }

            void setVarsMonthly() {

            }

            void setVarsWeekly() {

            }

            void fillHashmaps() {
                for (String country : countryConstants.keySet()) {
                    Double value = countryConstants.get(country);
                    countryConstantsMonthly.put(country, value / 12);
                    countryConstantsWeekly.put(country, value / 52);
                }
            }

}