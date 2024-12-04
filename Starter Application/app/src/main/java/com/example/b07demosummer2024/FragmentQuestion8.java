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

//Food Questions
public class FragmentQuestion8 extends Fragment {
    private SurveyResponseListner listner;
    private int questionID;
    //    Sets questionID for fragment which we use in the activity
    public FragmentQuestion8 (int questionID){
        this.questionID = questionID;
    }
    private Button vegetarian;
    private Button vegan;
    private Button pescatarian;
    private Button meatbased;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_question8, container, false);
//       Defines the buttons
        vegetarian = view.findViewById(R.id.vegetarian);
        vegan = view.findViewById(R.id.vegan);
        pescatarian = view.findViewById(R.id.pescatarian);
        meatbased = view.findViewById(R.id.meatbased);
//      sets the callbacks for the buttons
        vegetarian.setOnClickListener(this::answer);
        vegan.setOnClickListener(this::answer);
        pescatarian.setOnClickListener(this::answer);
        meatbased.setOnClickListener(this::answer);
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
        vegetarian.setBackgroundColor(Color.rgb(0, 153, 153));
        vegan.setBackgroundColor(Color.rgb(0, 153, 153));
        pescatarian.setBackgroundColor(Color.rgb(0, 153, 153));
        meatbased.setBackgroundColor(Color.rgb(0, 153, 153));
//      Reset the usability of all buttons, so user can press any other button
        vegetarian.setActivated(true);
        vegan.setActivated(true);
        pescatarian.setActivated(true);
        meatbased.setActivated(true);
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
