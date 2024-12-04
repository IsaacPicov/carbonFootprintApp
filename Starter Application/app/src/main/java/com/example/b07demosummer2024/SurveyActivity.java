package com.example.b07demosummer2024;
import static com.example.b07demosummer2024.Constants.ALWAYS_OPTION;
import static com.example.b07demosummer2024.Constants.BEEF_DAILY;
import static com.example.b07demosummer2024.Constants.BEEF_FREQUENT;
import static com.example.b07demosummer2024.Constants.BEEF_OCCASIONALLY;
import static com.example.b07demosummer2024.Constants.CHICKEN_DAILY;
import static com.example.b07demosummer2024.Constants.CHICKEN_FREQUENTLY;
import static com.example.b07demosummer2024.Constants.CHICKEN_OCCASIONALLY;
import static com.example.b07demosummer2024.Constants.DEFAULT_DISTANCE;
import static com.example.b07demosummer2024.Constants.DEFAULT_FUEL_VALUE;
import static com.example.b07demosummer2024.Constants.DIESEL_VALUE;
import static com.example.b07demosummer2024.Constants.ELECTRIC_VALUE;
import static com.example.b07demosummer2024.Constants.FISH_DAILY;
import static com.example.b07demosummer2024.Constants.FISH_FREQUENTLY;
import static com.example.b07demosummer2024.Constants.FISH_OCCASIONALLY;
import static com.example.b07demosummer2024.Constants.FLIGHTS_10_LONG;
import static com.example.b07demosummer2024.Constants.FLIGHTS_10_PLUS;
import static com.example.b07demosummer2024.Constants.FLIGHTS_1_2_LONG;
import static com.example.b07demosummer2024.Constants.FLIGHTS_1_2_SHORT;
import static com.example.b07demosummer2024.Constants.FLIGHTS_3_5_LONG;
import static com.example.b07demosummer2024.Constants.FLIGHTS_3_5_SHORT;
import static com.example.b07demosummer2024.Constants.FLIGHTS_6_10_LONG;
import static com.example.b07demosummer2024.Constants.FLIGHTS_6_10_SHORT;
import static com.example.b07demosummer2024.Constants.FOOD_WASTE_FREQUENTLY;
import static com.example.b07demosummer2024.Constants.FOOD_WASTE_OCCASIONALLY;
import static com.example.b07demosummer2024.Constants.FOOD_WASTE_RARELY;
import static com.example.b07demosummer2024.Constants.FREQUENT_OPTION;
import static com.example.b07demosummer2024.Constants.GAS_VALUE;
import static com.example.b07demosummer2024.Constants.HYBRID_VALUE;
import static com.example.b07demosummer2024.Constants.KM_10000_15000;
import static com.example.b07demosummer2024.Constants.KM_15000_20000;
import static com.example.b07demosummer2024.Constants.KM_20000_25000;
import static com.example.b07demosummer2024.Constants.KM_5000_10000;
import static com.example.b07demosummer2024.Constants.MEATBASED;
import static com.example.b07demosummer2024.Constants.NEVER_OPTION;
import static com.example.b07demosummer2024.Constants.NO_OPTION;
import static com.example.b07demosummer2024.Constants.OCCASIONAL_OPTION;
import static com.example.b07demosummer2024.Constants.PESCATARIAN;
import static com.example.b07demosummer2024.Constants.PORK_DAILY;
import static com.example.b07demosummer2024.Constants.PORK_FREQUENT;
import static com.example.b07demosummer2024.Constants.PORK_OCCASIONALLY;
import static com.example.b07demosummer2024.Constants.UP_TO_5000KM;
import static com.example.b07demosummer2024.Constants.VEGAN;
import static com.example.b07demosummer2024.Constants.VEGETARIAN;
import static com.example.b07demosummer2024.Constants.YES_OPTION;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class SurveyActivity extends AppCompatActivity implements SurveyResponseListner {
    // Define constants

    Intent userID;
    //String email = userID.getStringExtra("EMAIL");
    //     Assume that we've already made sure the user hasn't filled a survey
    Footprint survey;
    int question = 1;
    Button nextButton;
    Button backButton;
    FrameLayout frame;

   protected void onCreate (Bundle savedInstanceState){

       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_survey);
       Intent intent = getIntent();
       String userID = intent.getStringExtra("USERID");
       //     Assume that we've already made sure the user hasn't filled a survey
//       Keeps track of the user so we can add it to the firebase.
//       survey = new Footprint(userID);  <-- this is the real one that should be used
       survey = new Footprint("temp"); // <-- Stand in until I connect everything
       nextButton = findViewById(R.id.button4);
       backButton = findViewById(R.id.button3);
       // backButton.setEnabled(false);//this doesn't work? fix this
       // backButton.setBackgroundColor(Color.GRAY);
       frame = findViewById(R.id.frameLayout);

       if(savedInstanceState == null){
           setInitialFragment(new FragmentQuestion1(question));
       }
   }
//This is a method we use to put a fragment into the framelayout
    private void setInitialFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

//    This is an override so that the answers from the fragments can talk to the activity
    @Override
//    TODO --- log everything to the db :)
   public void onOption(int questionID, String selectedOption) {
        switch (questionID) {
            case 1:
                survey.setQ1(selectedOption.equals("Yes") ? YES_OPTION : NO_OPTION);
                survey.setTrue(0);
                Log.d("SurveyDebug", "Q1 set to: " + survey.getQ1() + ", selectedOption: " + selectedOption);
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
                Log.d("SurveyDebug", "Q2 set to: " + survey.getQ2() + ", selectedOption: " + selectedOption);
                break;

            case 3:
                switch (selectedOption) {
                    case "Up to 5,000 km (3,000 miles)":
                        survey.setQ3(UP_TO_5000KM);
                        break;
                    case "5,000–10,000 km (3,000–6,000 miles)":
                        survey.setQ3(KM_5000_10000);
                        break;
                    case "10,000–15,000 km (6,000–9,000 miles)":
                        survey.setQ3(KM_10000_15000);
                        break;
                    case "15,000–20,000 km (9,000–12,000 miles)":
                        survey.setQ3(KM_15000_20000);
                        break;
                    case "20,000–25,000 km (12,000–15,000 miles)":
                        survey.setQ3(KM_20000_25000);
                        break;
                    default:
                        survey.setQ3(DEFAULT_DISTANCE);
                }
                survey.setTrue(questionID - 1);
                Log.d("SurveyDebug", "Q3 set to: " + survey.getQ3() + ", selectedOption: " + selectedOption);
                break;

            case 4:
                switch (selectedOption) {
                    case "Never":
                        survey.setQ4(NEVER_OPTION);
                        break;
                    case "Occasionally (1-2 times/week)":
                        survey.setQ4(OCCASIONAL_OPTION);
                        break;
                    case "Frequently (3-4 times/week)":
                        survey.setQ4(FREQUENT_OPTION);
                        break;
                    default:
                        survey.setQ4(ALWAYS_OPTION);
                }
                survey.setTrue(questionID - 1);
                Log.d("SurveyDebug", "Q4 set to: " + survey.getQ4() + ", selectedOption: " + selectedOption);
                break;

            case 5:
                double q4Value = survey.getQ4();
                if (selectedOption.equals("Under 1 hour")) {
                    survey.setQ5(1);
                } else if (selectedOption.equals("1-3 hours")) {
                    survey.setQ5(2);
                } else if (selectedOption.equals("3-5 hours")) {
                    survey.setQ5(3);
                } else if (selectedOption.equals("5-10 hours")) {
                    survey.setQ5(4);
                } else if (selectedOption.equals("More than 10 hours")) {
                    survey.setQ5(5);
                }
                survey.setTrue(questionID - 1);
                Log.d("SurveyDebug", "Q5 set to: " + survey.getQ5() + ", selectedOption: " + selectedOption);
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
                Log.d("SurveyDebug", "Q6 set to: " + survey.getQ6() + ", selectedOption: " + selectedOption);
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
                Log.d("SurveyDebug", "Q7 set to: " + survey.getQ7() + ", selectedOption: " + selectedOption);
                break;
            case 8:
                switch(selectedOption){
                    case "Vegetarian":
                        survey.setQ8(VEGETARIAN);
                        break;
                    case "Vegan":
                        survey.setQ8(VEGAN);
                        break;
                    case "Pescatarian (fish/seafood)":
                        survey.setQ8(PESCATARIAN);
                        break;
                    case "Meat-based (all animal products)":
                        survey.setQ8(MEATBASED);
                        Log.d("SurveyDebug", "Q8 set to: " + survey.getQ8() + ", selectedOption: " + selectedOption);
                        break;
                }
                survey.setTrue(questionID-1);
                Log.d("SurveyDebug", "Q8 set to: " + survey.getQ8() + ", selectedOption: " + selectedOption);
                break;
//                The logic here is that all of question 9 is optional, so we have to skip like 4 cases if non meat based
//                Cases 9-12 all cover question 9
            case 9:
                switch (selectedOption){
                    case "Daily":
                        survey.setQ9_1(BEEF_DAILY);
                        break;
                    case "Frequently (3-5 times/week)":
                        survey.setQ9_1(BEEF_FREQUENT);
                        break;
                    case "Occasionally (1-2 times/week)":
                        survey.setQ9_1(BEEF_OCCASIONALLY);
                        break;
                    default: survey.setQ9_1(NEVER_OPTION);
                }
                survey.setTrue(questionID-1);
                Log.d("SurveyDebug", "Q9_1 set to: " + survey.getQ9_1() + ", selectedOption: " + selectedOption);
                break;
            case 10:
                switch (selectedOption){
                    case "Daily":
                        survey.setQ9_2(PORK_DAILY);
                        break;
                    case "Frequently (3-5 times/week)":
                        survey.setQ9_2(PORK_FREQUENT);
                        break;
                    case "Occasionally (1-2 times/week)":
                        survey.setQ9_2(PORK_OCCASIONALLY);
                        break;
                    default: survey.setQ9_2(NEVER_OPTION);
                }
                survey.setTrue(questionID-1);
                Log.d("SurveyDebug", "Q9_2 set to: " + survey.getQ9_2() + ", selectedOption: " + selectedOption);
                break;
            case 11:
                switch (selectedOption){
                    case "Daily":
                        survey.setQ9_3(CHICKEN_DAILY);
                        break;
                    case "Frequently (3-5 times/week)":
                        survey.setQ9_3(CHICKEN_FREQUENTLY);
                        break;
                    case "Occasionally (1-2 times/week)":
                        survey.setQ9_3(CHICKEN_OCCASIONALLY);
                        break;
                    default: survey.setQ9_3(NEVER_OPTION);
                }
                survey.setTrue(questionID-1);
                Log.d("SurveyDebug", "Q9_3 set to: " + survey.getQ9_3() + ", selectedOption: " + selectedOption);
                break;
            case 12:
                switch (selectedOption){
                case "Daily":
                    survey.setQ9_4(FISH_DAILY);
                    break;
                case "Frequently (3-5 times/week)":
                    survey.setQ9_4(FISH_FREQUENTLY);
                    break;
                case "Occasionally (1-2 times/week)":
                    survey.setQ9_4(FISH_OCCASIONALLY);
                    break;
                default: survey.setQ9_4(NEVER_OPTION);
                }
                survey.setTrue(questionID-1);
                Log.d("SurveyDebug", "Q9_4 set to: " + survey.getQ9_4() + ", selectedOption: " + selectedOption);
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
                Log.d("SurveyDebug", "Q10 set to: " + survey.getQ10() + ", selectedOption: " + selectedOption);
                break;
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
                        survey.setQ11(2);
                        break;
                }
                survey.setTrue(questionID-1);
                Log.d("SurveyDebug", "Q11 set to: " + survey.getQ11() + ", selectedOption: " + selectedOption);
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
                Log.d("SurveyDebug", "Q12 set to: " + survey.getQ12() + ", selectedOption: " + selectedOption);
                break;
//                Questions for Housing
            case 16:
                switch(selectedOption){
                    case "Under 1000 sq. ft.":
                        survey.setQ13(0);
                        break;
                    case"1000-2000 sq. ft.":
                        survey.setQ13(1);
                        break;
                    case "Over 2000 sq. ft.":
                        survey.setQ13(2);
                        break;
                }
                survey.setTrue(questionID-1);
                Log.d("SurveyDebug", "Q13 set to: " + survey.getQ13() + ", selectedOption: " + selectedOption);
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
                }
                survey.setTrue(questionID-1);
                Log.d("SurveyDebug", "Q14 set to: " + survey.getQ14() + ", selectedOption: " + selectedOption);
                break;
            case 18:
                switch(selectedOption){
                    case "Under $50":
                        survey.setQ15(0);
                        break;
                    case "$50-$100":
                        survey.setQ15(1);
                        break;
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
                survey.setTrue(questionID-1);
                Log.d("SurveyDebug", "Q15 set to: " + survey.getQ15() + ", selectedOption: " + selectedOption);
                break;
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
                }
                survey.setTrue(questionID-1);
                Log.d("SurveyDebug", "Q16 set to: " + survey.getQ16() + ", selectedOption: " + selectedOption);
                break;
            case 20:
                switch(selectedOption){
                    case "Yes, primarily (more than 50% of energy use)":
                        survey.setQ17(0);
                        break;
                    case "Yes, partially (less than 50% of energy use)":
                        survey.setQ17(1);
                        break;
                    case "No":
                        survey.setQ17(2);
                        break;
                }
                survey.setTrue(questionID-1);
                Log.d("SurveyDebug", "Q17 set to: " + survey.getQ17() + ", selectedOption: " + selectedOption);
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
                Log.d("SurveyDebug", "Q18 set to: " + survey.getQ18() + ", selectedOption: " + selectedOption);
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
                Log.d("SurveyDebug", "Q19 set to: " + survey.getQ19() + ", selectedOption: " + selectedOption);
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
                        break;
                }
                survey.setTrue(questionID-1);
                Log.d("SurveyDebug", "Q20 set to: " + survey.getQ20() + ", selectedOption: " + selectedOption);
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
                Log.d("SurveyDebug", "Q21 set to: " + survey.getQ21() + ", selectedOption: " + selectedOption);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + questionID);

        }
    }
//    Loading Different Fragments
    Fragment [] questions = new Fragment[]{
        new FragmentQuestion1(1),
        new FragmentQuestion2(2),
        new FragmentQuestion3(3),
        new FragmentQuestion4(4),
        new FragmentQuestion5(5),
        new FragmentQuestion6(6),
        new FragmentQuestion7(7),
        new FragmentQuestion8(8),
        new FragmentQuestion9_1(9),
        new FragmentQuestion9_2(10),
        new FragmentQuestion9_3(11),
        new FragmentQuestion9_4(12),
        new FragmentQuestion10(13),
        new FragmentQuestion11(14),
        new FragmentQuestion12(15),
        new FragmentQuestion13(16),
        new FragmentQuestion14(17),
        new FragmentQuestion15(18),
        new FragmentQuestion16(19),
        new FragmentQuestion17(20),
        new FragmentQuestion18(21),
        new FragmentQuestion19(22),
        new FragmentQuestion20(23),
        new FragmentQuestion21(24)
    };

   public void next (View view){
//       Handles case when we've reached the end of the survey and need to see results
       if(question == 24){
           Intent intent = new Intent(this, SurveyResultsActivity.class);
           survey.setTotalConsumption();
           survey.setTotalFood();
           survey.setTotalHousing();
           survey.setTotalTransport();
           intent.putExtra("HOUSING", survey.getTotalHousing());
           intent.putExtra("FOOD", survey.getTotalFood());
           intent.putExtra("CONSUMPTION", survey.getTotalConsumption());
           intent.putExtra("TRANSPORTATION", survey.getTotalTransport());
           survey.addToDB();
           startActivity(intent);

       }
//       handles case where question is not answered
       else if(!survey.getAnswer(question-1)){
//           Displays an Alert telling user to answer question
           Toast.makeText(this, "No Answer Selected", Toast.LENGTH_SHORT).show();
//           Also need to handle the case where they don't have a car and a bunch of questions are skipped
       }
//       If there are no problems
       else{
//         We disabled the back button on the first question b/c can't go back
           if (question == 1){
               backButton.setEnabled(true);
               // backButton.setBackgroundColor(Color.GRAY);
               if(survey.getQ1() == 0) question = 3;
           }
           else if (question == 8 && (survey.getQ8() != 0)) question = 12;
           question++;
           getSupportFragmentManager().beginTransaction()
                   .replace(R.id.frameLayout, questions[question-1])
                   .commit();
       }
   }
   public void back (View view){
       if(question == 4 && (survey.getQ1() == 0)) question = 2;
       if(question == 13 && (survey.getQ8() != 0)) question = 9;
       question--;
       Button back = (Button)view;
       // if(question == 1){
           // back.setBackgroundColor(Color.GRAY);
           // back.setEnabled(false);
       // }
       getSupportFragmentManager().beginTransaction()
               .replace(R.id.frameLayout, questions[question-1])
               .commit();
   }

}


