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

public class FragmentQuestion9_4 extends Fragment {
    private SurveyResponseListner listner;
    private int questionID;

    public FragmentQuestion9_4(int questionID) {
        this.questionID = questionID;
    }

    // Buttons for Beef, Pork, Chicken, and Fish/Seafood options

    private Button fish_never;
    private Button fish_daily;
    private Button fish_frequent;
    private Button fish_occasional;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question9_4, container, false);

        // Assign buttons for each category
        fish_never = view.findViewById(R.id.fish1);
        fish_daily = view.findViewById(R.id.fish2);
        fish_frequent = view.findViewById(R.id.fish3);
        fish_occasional = view.findViewById(R.id.fish4);


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
        fish_never.setBackgroundColor(Color.MAGENTA);
        fish_daily.setBackgroundColor(Color.MAGENTA);
        fish_frequent.setBackgroundColor(Color.MAGENTA);
        fish_occasional.setBackgroundColor(Color.MAGENTA);
//      Reset the usability of all buttons, so user can press any other button
        fish_never.setActivated(true);
        fish_daily.setActivated(true);
        fish_frequent.setActivated(true);
        fish_occasional.setActivated(true);

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