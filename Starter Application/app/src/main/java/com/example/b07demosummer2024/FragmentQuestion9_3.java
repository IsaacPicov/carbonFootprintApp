package com.example.b07demosummer2024;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FragmentQuestion9_3 extends Fragment {
    private SurveyResponseListner listner;
    private int questionID;

    public FragmentQuestion9_3(int questionID) {
        this.questionID = questionID;
    }

    // Buttons for Beef, Pork, Chicken, and Fish/Seafood options
    private Button[] chickenButtons = new Button[4];

    private Button chicken_never;
    private Button chicken_daily;
    private Button chicken_frequent;
    private Button chicken_occasional;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question9_3, container, false);

        // Assign buttons for each category
        chicken_never = view.findViewById(R.id.q9_3option1);
        chicken_daily = view.findViewById(R.id.q9_3option2);
        chicken_frequent = view.findViewById(R.id.q9_3option3);
        chicken_occasional = view.findViewById(R.id.q9_3option4);

        chicken_never.setOnClickListener(this::answer);
        chicken_daily.setOnClickListener(this::answer);
        chicken_frequent.setOnClickListener(this::answer);
        chicken_occasional.setOnClickListener(this::answer);

        return view;
    }

    //    Steal this code for all fragments
    @Override
    public void onAttach(@NonNull Context context){

        super.onAttach(context);
        try{
            listner = (SurveyResponseListner) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context + "doesn't properly implement");
        }
    }


    public void answer(View view){
//        Reset the colours so that user can see they unselected the previous button
        chicken_never.setBackgroundColor(Color.rgb(0, 153, 153));
        chicken_daily.setBackgroundColor(Color.rgb(0, 153, 153));
        chicken_frequent.setBackgroundColor(Color.rgb(0, 153, 153));
        chicken_occasional.setBackgroundColor(Color.rgb(0, 153, 153));
//      Reset the usability of all buttons, so user can press any other button
        chicken_never.setActivated(true);
        chicken_daily.setActivated(true);
        chicken_frequent.setActivated(true);
        chicken_occasional.setActivated(true);

//        Disables the button the user selected, so they can't keep pressing it
        Button option = (Button)view;
        String selectedOption = option.getText().toString();
        option.setBackgroundColor(Color.GRAY);
        option.setActivated(false);
//        Calls the function we override in the activity
        saveOption(selectedOption);
    }
    //    This guy deals with the cases
    private void saveOption(String selectedOption){
        if (listner != null){
            listner.onOption(questionID, selectedOption);
        }
    }
}