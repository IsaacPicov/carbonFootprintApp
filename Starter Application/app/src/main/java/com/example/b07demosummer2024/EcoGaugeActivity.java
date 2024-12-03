package com.example.b07demosummer2024;

import static com.example.b07demosummer2024.CountriesConstants.COUNTRIES;
import static com.example.b07demosummer2024.CountriesConstants.countryConstants;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;


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
    Double[] carbonHistory;
    TextView carbonCount;
    TextView timeframe;
    PieChart breakdown;
    BarChart byCountry;
    LineChart history;
    String userID;
    HashMap<String, Double> countryConstantsMonthly;
    HashMap<String, Double> countryConstantsWeekly;
    XAxis xAxisLine;
    ArrayList<HashMap<String, Float[]>[]> weeks = new ArrayList<>();
    ArrayList<HashMap<String, Float[]>[]> months = new ArrayList<>();


    public static final ArrayList<Integer> graphColours = new ArrayList<>();

    static {
        graphColours.add(Color.parseColor("#009999"));
        graphColours.add(Color.parseColor("#002F4B"));
        graphColours.add(Color.parseColor("#A8DADC"));
        graphColours.add(Color.parseColor("#4F83CC"));
    }

    protected void onCreate(Bundle savedInstanceState) {
//      Default setting the gauge to display annual
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
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


        getWeeksTimeline(userID);
        getMonthsTimeline(userID);

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
                if (counter == 3) {
                    updateEntries.add(new BarEntry(0f, totalCarbon.floatValue()));
                    updateEntries.add(new BarEntry(1f, Objects.requireNonNull(countryConstants.get(selectedCountry)).floatValue()));
                }
                if (counter == 2) {
                    updateEntries.add(new BarEntry(0f, totalCarbon.floatValue()));
                    updateEntries.add(new BarEntry(1f, Objects.requireNonNull(countryConstantsMonthly.get(selectedCountry)).floatValue()));
                }
                if (counter == 1) {
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

                history.animateXY(1000, 1000); //call this for everytime you update
                history.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }



    void onWeekly(View view) {
//        Reference the firebase with userID as the key and grab the associated data
        counter = 1;
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

    void onDaily(View view) {
        counter = 3;
        HashMap<String, Float[]>[] currWeek = weeks.get(weeks.size()-1);
        int i = 0;
        while(currWeek[i] != null) i++;
        for(Object day : currWeek[i-1].keySet()){
            Float[] latestDay = currWeek[i-1].get(day);
            totalCarbon =latestDay[0].doubleValue();
            totalTransportation = latestDay[1].doubleValue();
            totalFood = latestDay[2].doubleValue();
            totalHousing = latestDay[3].doubleValue();
            totalConsumption = latestDay[4].doubleValue();
        }

//        Emissions Overview
        carbonCount.setText(totalCarbon + "kg C02e");
        timeframe.setText("On Most Recent Entry");
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

    private void setVarsMonthly() {
        HashMap<String, Float[]>[] currMonth= months.get(months.size()-1);
        double monthlyTotal = 0;
        double monthlyTransportation = 0;
        double monthlyFood = 0;
        double monthlyHousing = 0;
        double monthlyConsumption = 0;
        for(int i =0; i< currMonth.length; i++){
//          This is ok because there's only one key in each hashmap
            for(String day : currMonth[i].keySet()){
            Float[] currDay = currMonth[i].get(day);
                monthlyTotal += currDay[0];
                monthlyTransportation += currDay[1];
                monthlyFood += currDay[2];
                monthlyHousing += currDay[3];
                monthlyConsumption += currDay[4];
            }
        }
        totalCarbon = monthlyTotal;
        totalTransportation = monthlyTransportation;
        totalFood = monthlyFood;
        totalHousing = monthlyHousing;
        totalConsumption = monthlyConsumption;
    }

    private void setVarsWeekly() {
        HashMap<String, Float[]>[] currWeek= weeks.get(weeks.size()-1);
        double weeklyTotal = 0;
        double weeklyTransportation = 0;
        double weeklyFood = 0;
        double weeklyHousing = 0;
        double weeklyConsumption = 0;
        for(int i =0; i< currWeek.length; i++){
//          This is ok because there's only one key in each hashmap
            for(String day : currWeek[i].keySet()){
                Float[] currDay = currWeek[i].get(day);
                weeklyTotal += currDay[0];
                weeklyTransportation += currDay[1];
                weeklyFood += currDay[2];
                weeklyHousing += currDay[3];
                weeklyConsumption += currDay[4];
            }
        }
        totalCarbon = weeklyTotal;
        totalTransportation = weeklyTransportation;
        totalFood = weeklyFood;
        totalHousing = weeklyHousing;
        totalConsumption = weeklyConsumption;
    }

    private void fillHashmaps() {
        for (String country : countryConstants.keySet()) {
            Double value = countryConstants.get(country);
            countryConstantsMonthly.put(country, value / 12);
            countryConstantsWeekly.put(country, value / 52);
        }
    }

    private void getWeeksTimeline(String UserID) {
        DatabaseReference ref = FirebaseDatabase.getInstance("https://b07finalproject-4e3be-default-rtdb.firebaseio.com/")
                .getReference("users").child(userID).child("dailyLogs");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map<String, Object> dateMap = (Map<String, Object>) dataSnapshot.getValue();
                if (dateMap != null) {
                    ArrayList<String> dateKeys = new ArrayList<>(dateMap.keySet());
                    Collections.sort(dateKeys);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate ogDate = LocalDate.parse(dateKeys.get(0), formatter);

                    HashMap<String, Float[]>[] currWeek = new HashMap[31];
                    int dayIndex = 0;


                    for (String currDateStr : dateKeys) {
                        LocalDate currDate = LocalDate.parse(currDateStr, formatter);

                        long daysBetween = ChronoUnit.DAYS.between(ogDate, currDate);


                        // Handle daily emissions

                        Float emissions = dataSnapshot.child(currDateStr)
                                .child("total_emissions").getValue(Float.class);
                        Float transportation = dataSnapshot.child(currDateStr)
                                .child("emissions_by_category").child("transportation").getValue(Float.class);
                        Float food = dataSnapshot.child(currDateStr)
                                .child("emissions_by_category").child("food").getValue(Float.class);
                        Float housing = dataSnapshot.child(currDateStr)
                                .child("emissions_by_category").child("bills").getValue(Float.class);
                        Float consumption = dataSnapshot.child(currDateStr)
                                .child("emissions_by_category").child("consumption").getValue(Float.class);

                        HashMap<String, Float[]> dayEntry = new HashMap<>();
                        if (emissions != null & transportation != null & consumption != null & housing != null & food != null) {
                            Float [] dailyCarbonInfo = {emissions, transportation, food, housing, consumption};
                            dayEntry.put(currDateStr, dailyCarbonInfo);
                        }

                        // Check if the week has changed
                        if (daysBetween<7) {
                            // Add to the current month
                            currWeek[dayIndex++] = dayEntry;
                        } else {
                            // Save the current month and reset
                            if (dayIndex > 0) {
                                weeks.add(currWeek);
                            }
                            currWeek = new HashMap[7];
                            dayIndex = 0;
                            currWeek[dayIndex++] = dayEntry;
                            ogDate = currDate;
                        }
                    }

                    // Add the last month
                    if (dayIndex > 0) {
                        weeks.add(currWeek);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println("Error: " + databaseError.getMessage());
            }
        });

    }

    private void getMonthsTimeline(String userID) {

        DatabaseReference ref = FirebaseDatabase.getInstance("https://b07finalproject-4e3be-default-rtdb.firebaseio.com/")
                .getReference("users").child(userID).child("dailyLogs");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> dateMap = (Map<String, Object>) dataSnapshot.getValue();
                if (dateMap != null) {
                    ArrayList<String> dateKeys = new ArrayList<>(dateMap.keySet());
                    Collections.sort(dateKeys);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate ogDate = LocalDate.parse(dateKeys.get(0), formatter);

                    HashMap<String, Float[]>[] currMonth = new HashMap[31];
                    int dayIndex = 0;


                    for (String currDateStr : dateKeys) {
                        LocalDate currDate = LocalDate.parse(currDateStr, formatter);

                        // Handle daily emissions

                        Float emissions = dataSnapshot.child(currDateStr)
                                .child("total_emissions").getValue(Float.class);
                        Float transportation = dataSnapshot.child(currDateStr)
                                .child("emissions_by_category").child("transportation").getValue(Float.class);
                        Float food = dataSnapshot.child(currDateStr)
                                .child("emissions_by_category").child("food").getValue(Float.class);
                        Float housing = dataSnapshot.child(currDateStr)
                                .child("emissions_by_category").child("bills").getValue(Float.class);
                        Float consumption = dataSnapshot.child(currDateStr)
                                .child("emissions_by_category").child("consumption").getValue(Float.class);

                        HashMap<String, Float[]> dayEntry = new HashMap<>();
                        if (emissions != null & transportation != null & consumption != null & housing != null & food != null) {
                            Float [] dailyCarbonInfo = {emissions, transportation, food, housing, consumption};
                            dayEntry.put(currDateStr, dailyCarbonInfo);
                        }

                        // Check if the month has changed
                        if (ogDate.getYear() == currDate.getYear() &&
                                ogDate.getMonth() == currDate.getMonth()) {
                            // Add to the current month
                            currMonth[dayIndex++] = dayEntry;
                        } else {
                            // Save the current month and reset
                            if (dayIndex > 0) {
                                months.add(currMonth);
                            }
                            currMonth = new HashMap[31];
                            dayIndex = 0;
                            currMonth[dayIndex++] = dayEntry;
                            ogDate = currDate;
                        }
                    }

                    // Add the last month
                    if (dayIndex > 0) {
                        months.add(currMonth);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println("Error: " + databaseError.getMessage());
            }
        });

    }

    private HashMap<String, Float[]>[] getLast30Days (){
        HashMap <String, Float []>[] last30 = new HashMap [30];
//        Log newest to oldest and then parse in reverse

    }
}