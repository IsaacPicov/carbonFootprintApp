package com.example.b07demosummer2024;
//What type of energy do you use to heat your home?


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentQuestion14#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentQuestion18 extends Fragment {
    private SurveyResponseListner listner;
    private int questionID;
    public FragmentQuestion18 (int questionID){
        this.questionID = questionID;
    }
    private Button monthly;
    private Button quarterly;
    private Button annually;
    private Button rarely;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_question18, container, false);
        monthly = view.findViewById(R.id.option1);
        quarterly = view.findViewById(R.id.option2); // Change BUTTONS
        annually = view.findViewById(R.id.option3);
        rarely = view.findViewById(R.id.option4);
        return view;

    }

    @Override
    public void onAttach(Context context){

        super.onAttach(context);
        try{
            listner = (SurveyResponseListner) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "doesn't properly implement");
        }
    }


    public void answer(View view){
        monthly.setBackgroundColor(Color.MAGENTA);
        quarterly.setBackgroundColor(Color.MAGENTA);
        annually.setBackgroundColor(Color.MAGENTA);
        rarely.setBackgroundColor(Color.MAGENTA);

        monthly.setActivated(true);
        quarterly.setActivated(true);
        annually.setActivated(true);
        rarely.setActivated(true);
        Button option = (Button)view;
        String selectedOption = option.getText().toString();
        option.setBackgroundColor(Color.GRAY);
        option.setActivated(false);
        saveOption(selectedOption);
    }
    private void saveOption(String selectedOption){
        if (listner != null){
            listner.onOption(questionID, selectedOption);
        }
    }
}