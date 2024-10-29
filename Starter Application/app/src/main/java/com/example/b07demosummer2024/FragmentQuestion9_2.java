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

public class FragmentQuestion9_2 extends Fragment {
    private SurveyResponseListner listner;
    private int questionID;

    public FragmentQuestion9_2(int questionID) {
        this.questionID = questionID;
    }

    // Buttons for Beef, Pork, Chicken, and Fish/Seafood options
    private Button[] porkButtons = new Button[4];

    private Button pork_never;
    private Button pork_daily;
    private Button pork_frequent;
    private Button pork_occasional;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question9_2, container, false);

        // Assign buttons for each category
        pork_never = view.findViewById(R.id.q9_2option1);
        pork_daily = view.findViewById(R.id.q9_2option2);
        pork_frequent = view.findViewById(R.id.q9_2option3);
        pork_occasional = view.findViewById(R.id.q9_2option4);

        pork_never.setOnClickListener(this::answer);
        pork_daily.setOnClickListener(this::answer);
        pork_frequent.setOnClickListener(this::answer);
        pork_occasional.setOnClickListener(this::answer);

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
        pork_never.setBackgroundColor(Color.MAGENTA);
        pork_daily.setBackgroundColor(Color.MAGENTA);
        pork_frequent.setBackgroundColor(Color.MAGENTA);
        pork_occasional.setBackgroundColor(Color.MAGENTA);
//      Reset the usability of all buttons, so user can press any other button
        pork_never.setActivated(true);
        pork_daily.setActivated(true);
        pork_frequent.setActivated(true);
        pork_occasional.setActivated(true);

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