package com.example.b07demosummer2024;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.stream.IntStream;

public class SurveyActivity extends AppCompatActivity implements SurveyResponseListner {
    // Define constants
    private static final int YES_OPTION = 1;
    private static final int NO_OPTION = 0;

    private static final double GAS_VALUE = 0.24;
    private static final double DIESEL_VALUE = 0.27;
    private static final double HYBRID_VALUE = 0.16;
    private static final double ELECTRIC_VALUE = 0.05;
    private static final double DEFAULT_FUEL_VALUE = GAS_VALUE;

    private static final double UP_TO_5000KM = 5000.0;
    private static final double KM_5000_10000 = 10000.0;
    private static final double KM_10000_15000 = 15000.0;
    private static final double KM_15000_20000 = 20000.0;
    private static final double KM_20000_25000 = 25000.0;
    private static final double DEFAULT_DISTANCE = 35000.0;

    private static final double NEVER_OPTION = 0.0;
    private static final double OCCASIONAL_OPTION = 1.0;
    private static final double FREQUENT_OPTION = 2.0;
    private static final double ALWAYS_OPTION = 3.0;

    private static final double UNDER_1_HOUR_OCCASIONAL = 246.0;
    private static final double UNDER_1_HOUR_FREQUENT = 573.0;
    private static final double HOUR_1_3_OCCASIONAL = 819.0;
    private static final double HOUR_1_3_FREQUENT = 1911.0;
    private static final double HOUR_3_5_OCCASIONAL = 1638.0;
    private static final double HOUR_3_5_FREQUENT = 3822.0;
    private static final double HOUR_5_10_OCCASIONAL = 3071.0;
    private static final double HOUR_5_10_FREQUENT = 7166.0;
    private static final double ABOVE_10_HOURS_OCCASIONAL = 4095.0;
    private static final double ABOVE_10_HOURS_FREQUENT = 9555.0;

    private static final double FLIGHTS_1_2_SHORT = 255.0;
    private static final double FLIGHTS_3_5_SHORT = 600.0;
    private static final double FLIGHTS_6_10_SHORT = 1200.0;
    private static final double FLIGHTS_10_PLUS = 1800.0;

    private static final double FLIGHTS_1_2_LONG = 825.0;
    private static final double FLIGHTS_3_5_LONG = 2200.0;
    private static final double FLIGHTS_6_10_LONG = 4400.0;
    private static final double FLIGHTS_10_LONG = 6600.0;

    private static final double VEGETARIAN = 1000.0;
    private static final double VEGAN = 500.0;
    private static final double PESCATARIAN = 1500.0;
    private static final double MEATBASED = 0;

    private static final double BEEF_DAILY = 2500.0;
    private static final double BEEF_FREQUENT = 1900.0;
    private static final double BEEF_OCCASIONALLY = 1300.0;

    private static final double PORK_DAILY = 1450.0;
    private static final double PORK_FREQUENT = 860.0;
    private static final double PORK_OCCASIONALLY = 450.0;

    private static final double CHICKEN_DAILY = 950.0;
    private static final double CHICKEN_FREQUENTLY = 600.0;
    private static final double CHICKEN_OCCASIONALLY = 150.0;

    private static final double FISH_DAILY = 800.0;
    private static final double FISH_FREQUENTLY = 500.0;
    private static final double FISH_OCCASIONALLY = 150.0;

    private static final double FOOD_WASTE_RARELY = 23.4;
    private static final double FOOD_WASTE_OCCASIONALLY = 70.2;
    private static final double FOOD_WASTE_FREQUENTLY = 140.4;

    Intent userID = getIntent();
    String email = userID.getStringExtra("EMAIL");
    //     Assume that we've already made sure the user hasn't filled a survey
    Footprint survey = new Footprint(email);
    int question = 1;
    Button nextButton = findViewById(R.id.button4);
    Button backButton = findViewById(R.id.button3);
    FrameLayout frame = findViewById(R.id.frameLayout);

   protected void onCreate (Bundle savedInstanceState){

       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_survey);

       backButton.setActivated(false);
       backButton.setBackgroundColor(Color.GRAY);

       if(savedInstanceState == null){
           setIntialFragment(new FragmentQuestion1(question));
       }
   }
//This is a method we use to put a fragment into the framelayout
    private void setIntialFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

//    This is an override so that the answers from the fragments can talk to the activity
    @Override
   public void onOption(int questionID, String selectedOption) {
        switch (questionID) {
//            Questions for Transportation
            case 1:
                survey.setQ1(selectedOption.equals("Yes") ? YES_OPTION : NO_OPTION);
                survey.setTrue(0);
                break;

            case 2:
                switch (selectedOption) {
                    case "Gas":
                        survey.setQ2(GAS_VALUE);
                        break;
                    case "Diesel":
                        survey.setQ2(DIESEL_VALUE);
                        break;
                    case "Hybrid":
                        survey.setQ2(HYBRID_VALUE);
                        break;
                    case "Electric":
                        survey.setQ2(ELECTRIC_VALUE);
                        break;
                    default:
                        survey.setQ2(DEFAULT_FUEL_VALUE);
                }
                survey.setTrue(questionID - 1);
                break;

            case 3:
                switch (selectedOption) {
                    case "Up to 5000km":
                        survey.setQ3(UP_TO_5000KM);
                        break;
                    case "5000-10,000km":
                        survey.setQ3(KM_5000_10000);
                        break;
                    case "10,000-15,000km":
                        survey.setQ3(KM_10000_15000);
                        break;
                    case "15,000-20,000km":
                        survey.setQ3(KM_15000_20000);
                        break;
                    case "20,000-25,000km":
                        survey.setQ3(KM_20000_25000);
                        break;
                    default:
                        survey.setQ3(DEFAULT_DISTANCE);
                }
                survey.setTrue(questionID - 1);
                break;

            case 4:
                switch (selectedOption) {
                    case "Never":
                        survey.setQ4(NEVER_OPTION);
                        break;
                    case "Occasionally(1-2 times/week)":
                        survey.setQ4(OCCASIONAL_OPTION);
                        break;
                    case "Frequently(3-4 times/week)":
                        survey.setQ4(FREQUENT_OPTION);
                        break;
                    default:
                        survey.setQ4(ALWAYS_OPTION);
                }
                survey.setTrue(questionID - 1);
                break;

            case 5:
                double q4Value = survey.getQ4();
                if (selectedOption.equals("Under 1 hour")) {
                    survey.setQ5(q4Value == NEVER_OPTION ? 0 : (q4Value == OCCASIONAL_OPTION ? UNDER_1_HOUR_OCCASIONAL : UNDER_1_HOUR_FREQUENT));
                } else if (selectedOption.equals("1-3 hours")) {
                    survey.setQ5(q4Value == NEVER_OPTION ? 0 : (q4Value == OCCASIONAL_OPTION ? HOUR_1_3_OCCASIONAL : HOUR_1_3_FREQUENT));
                } else if (selectedOption.equals("3-5 hours")) {
                    survey.setQ5(q4Value == NEVER_OPTION ? 0 : (q4Value == OCCASIONAL_OPTION ? HOUR_3_5_OCCASIONAL : HOUR_3_5_FREQUENT));
                } else if (selectedOption.equals("5-10 hours")) {
                    survey.setQ5(q4Value == NEVER_OPTION ? 0 : (q4Value == OCCASIONAL_OPTION ? HOUR_5_10_OCCASIONAL : HOUR_5_10_FREQUENT));
                } else {
                    survey.setQ5(q4Value == NEVER_OPTION ? 0 : (q4Value == OCCASIONAL_OPTION ? ABOVE_10_HOURS_OCCASIONAL : ABOVE_10_HOURS_FREQUENT));
                }
                survey.setTrue(questionID - 1);
                break;

            case 6:
//                Be sure to capitalize "Flights" when making the buttons
                switch (selectedOption){
                    case "None":
                        survey.setQ6(NEVER_OPTION);
                        break;
                    case "1-2 Flights":
                        survey.setQ6(FLIGHTS_1_2_SHORT);
                        break;
                    case "3-5 Flights":
                        survey.setQ6(FLIGHTS_3_5_SHORT);
                        break;
                    case "6-10 Flights":
                        survey.setQ6(FLIGHTS_6_10_SHORT);
                        break;
                    case "More Than 10 Flights":
                        survey.setQ6(FLIGHTS_10_PLUS);
                        break;
                    default: survey.setQ6(FLIGHTS_10_PLUS);
                }
                survey.setTrue(questionID-1);
                break;
            case 7:
                switch (selectedOption){
                    case "None":
                        survey.setQ7(NEVER_OPTION);
                        break;
                    case "1-2 Flights":
                        survey.setQ7(FLIGHTS_1_2_LONG);
                        break;
                    case "3-5 Flights":
                        survey.setQ7(FLIGHTS_3_5_LONG);
                        break;
                    case "6-10 Flights":
                        survey.setQ7(FLIGHTS_6_10_LONG);
                        break;
                    case "More Than 10 Flights":
                        survey.setQ7(FLIGHTS_10_LONG);
                        break;
                    default: survey.setQ7(FLIGHTS_10_LONG);
                }
                survey.setTrue(questionID-1);
                break;
//                Questions for Food
            case 8:
                switch(selectedOption){
                    case "Vegetarian":
                        survey.setQ8(VEGETARIAN);
                        break;
                    case "Vegan":
                        survey.setQ8(VEGAN);
                        break;
                    case "Pescatarian":
                        survey.setQ8(PESCATARIAN);
                        break;
                    case "Meat-Based":
                        survey.setQ8(MEATBASED);
                        break;
                }
//                The logic here is that all of question 9 is optional, so we have to skip like 4 cases if non meat based
//                Cases 9-12 all cover question 9
            case 9:
                switch (selectedOption){
                    case "Daily":
                        survey.setQ9_1(BEEF_DAILY);
                        break;
                    case "Frequently(3-5 times/week)":
                        survey.setQ9_1(BEEF_FREQUENT);
                        break;
                    case "Occasionally(1-2 times/week)":
                        survey.setQ9_1(BEEF_OCCASIONALLY);
                        break;
                    default: survey.setQ9_1(NEVER_OPTION);
                }
                survey.setTrue(questionID-1);
                break;
            case 10:
                switch (selectedOption){
                    case "Daily":
                        survey.setQ9_2(PORK_DAILY);
                        break;
                    case "Frequently(3-5 times/week)":
                        survey.setQ9_2(PORK_FREQUENT);
                        break;
                    case "Occasionally(1-2 times/week)":
                        survey.setQ9_2(PORK_OCCASIONALLY);
                        break;
                    default: survey.setQ9_2(NEVER_OPTION);
                }
                survey.setTrue(questionID-1);
                break;
            case 11:
                switch (selectedOption){
                    case "Daily":
                        survey.setQ9_3(CHICKEN_DAILY);
                        break;
                    case "Frequently(3-5 times/week)":
                        survey.setQ9_3(CHICKEN_FREQUENTLY);
                        break;
                    case "Occasionally(1-2 times/week)":
                        survey.setQ9_3(CHICKEN_OCCASIONALLY);
                        break;
                    default: survey.setQ9_3(NEVER_OPTION);
                }
                survey.setTrue(questionID-1);
                break;
            case 12:
                switch (selectedOption){
                case "Daily":
                    survey.setQ9_4(FISH_DAILY);
                    break;
                case "Frequently(3-5 times/week)":
                    survey.setQ9_4(FISH_FREQUENTLY);
                    break;
                case "Occasionally(1-2 times/week)":
                    survey.setQ9_4(FISH_OCCASIONALLY);
                    break;
                default: survey.setQ9_4(NEVER_OPTION);
                }
                survey.setTrue(questionID-1);
                break;
            case 13:
                switch (selectedOption){
                    case "Rarely":
                        survey.setQ10(FOOD_WASTE_RARELY);
                        break;
                    case "Occasionally":
                        survey.setQ10(FOOD_WASTE_OCCASIONALLY);
                        break;
                    case "Frequently":
                        survey.setQ10(FOOD_WASTE_FREQUENTLY);
                        break;
                    default: survey.setQ10(NEVER_OPTION);
                }
                survey.setTrue(questionID-1);
                break;
//                We're gonna have to do add all these questions with if statements
            case 14:
                switch(selectedOption){
                    case "Detached House":
                        survey.setQ11(0);
                        break;
                    case "Semi-detached House":
                        survey.setQ11(1);
                        break;
                    case "Townhouse":
                        survey.setQ11(2);
                        break;
                    case "Condo/Apartment":
                        survey.setQ11(3);
                        break;
                    case "Other":
                        survey.setQ11(4);
                        break;
                }
                survey.setTrue(questionID-1);
                break;
            case 15:
                switch(selectedOption){
                    case "1":
                        survey.setQ12(0);
                        break;
                    case "2":
                        survey.setQ12(1);
                        break;
                    case "3-4":
                        survey.setQ12(2);
                        break;
                    case "5 or more":
                        survey.setQ12(3);
                        break;
                }
                survey.setTrue(questionID-1);
                break;
//                Questions for Housing
            case 16:
                switch(selectedOption){
                    case "Under 1000 Sqft":
                        survey.setQ13(0);
                        break;
                    case"1000-2000 Sqft":
                        survey.setQ13(1);
                        break;
                    case "Over 2000 Sqft":
                        survey.setQ13(2);
                        break;
                }
                survey.setTrue(questionID-1);
                break;
            case 17:
                switch(selectedOption) {
                    case "Natural Gas":
                        survey.setQ14(0);
                        break;
                    case "Electricity":
                        survey.setQ14(1);
                        break;
                    case "Oil":
                        survey.setQ14(2);
                        break;
                    case "Propane":
                        survey.setQ14(3);
                        break;
                    case "Wood":
                        survey.setQ14(4);
                        break;
                    case "Other":
                        survey.setQ14(5);
                        break;
                }
                survey.setTrue(questionID-1);
                break;
            case 18:
                switch(selectedOption){
                    case "Under $50":
                        survey.setQ15(0);
                        break;
                    case "$50-$100":
                        survey.setQ15(1);
                    case "$100-$150":
                        survey.setQ15(2);
                        break;
                    case "$150-$200":
                        survey.setQ15(3);
                        break;
                    case "Over $200":
                        survey.setQ15(4);
                        break;
                }
            case 19:
                switch(selectedOption){
                    case "Natural Gas":
                        survey.setQ16(0);
                        break;
                    case "Electricity":
                        survey.setQ16(1);
                        break;
                    case "Oil":
                        survey.setQ16(2);
                        break;
                    case "Propane":
                        survey.setQ16(3);
                        break;
                    case "Wood":
                        survey.setQ16(4);
                        break;
                    case "Other":
                        survey.setQ16(5);
                        break;

                }
            case 20:
                switch(selectedOption){
                    case "Primarily":
                        survey.setQ17(0);
                        break;
                    case "Partially":
                        survey.setQ17(1);
                        break;
                    case "No":
                        survey.setQ17(2);
                        break;
                }
                survey.setTrue(questionID-1);
                break;
//                Need to be able to return to the results screen
//                UI needs to be able to adapt to tablets and shit

//            Questions for Consumption
            case 21:
                switch(selectedOption){
                    case "Monthly":
                        survey.setQ18(360);
                        break;
                    case "Quarterly":
                        survey.setQ18(120);
                        break;
                    case "Annually":
                        survey.setQ18(100);
                        break;
                    case "Rarely":
                        survey.setQ18(5);
                        break;
                }
                survey.setTrue(questionID-1);
                break;
            case 22:
                switch(selectedOption){
                    case "Regularly":
                        survey.setQ19(1);
                        break;
                    case "Occasionally":
                        survey.setQ19(2);
                        break;
                    case "No":
                        survey.setQ19(3);
                        break;
                }
                survey.setTrue(questionID-1);
                break;
            case 23:
                switch (selectedOption){
                    case "None":
                        survey.setQ20(0);
                        break;
                    case"1":
                        survey.setQ20(300);
                        break;
                    case "2":
                        survey.setQ20(600);
                        break;
                    case "3":
                        survey.setQ20(900);
                        break;
                    case "4 or More":
                        survey.setQ20(1200);
                }
                survey.setTrue(questionID-1);
                break;
            case 24:
                switch (selectedOption){
                    case "Never":
                        survey.setQ21(1);
                        break;
                    case "Occasionally":
                        survey.setQ21(2);
                        break;
                    case "Frequently":
                        survey.setQ21(3);
                        break;
                    case "Always":
                        survey.setQ21(4);
                        break;
                }
                survey.setTrue(questionID-1);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + questionID);

        }
    }


//    TODO loading the fragments into an array of some kind and figuring out how to parse back and forth between them
   public void next (View view){
//       handles case where question is not answered
       if(!survey.getAnswer(question-1)){
//           Displays an Alert telling user to answer question
           Toast.makeText(this, "No Answer Selected", Toast.LENGTH_SHORT).show();

//           TODO handle the cases where questions are skipped in both forward and backward direction, example car, and meat questions
       }
       if(false){
//           Use this one for editText fields where we save data to footprint when the user presses next
       }
//       If there are no problems
       else{
//         We disabled the back button on the first question b/c can't go back
           if (question == 1){
               backButton.setActivated(true);
               backButton.setBackgroundColor(Color.MAGENTA);
           }
           question++;
//            Figure out how to go to next question
       }


   }

   public void back (View view){
       question--;
       Button back = (Button)view;
       if(question == 0){
           back.setBackgroundColor(Color.GRAY);
           back.setActivated(false);
       }
       //figure out how to go to last fragment
   }

}


