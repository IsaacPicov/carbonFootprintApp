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
public class FragmentQuestion21 extends Fragment {
    private SurveyResponseListner listner;
    private int questionID;
    public FragmentQuestion21 (int questionID){
        this.questionID = questionID;
    }
    private Button never;
    private Button occasionally;
    private Button frequently;
    private Button always;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_question21, container, false);
        never = view.findViewById(R.id.q21option1);
        occasionally = view.findViewById(R.id.q21option2); // Change BUTTONS
        frequently = view.findViewById(R.id.q21option3);
        always = view.findViewById(R.id.q21option4);

        never.setOnClickListener(this::answer);
        occasionally.setOnClickListener(this::answer);
        frequently.setOnClickListener(this::answer);
        always.setOnClickListener(this::answer);

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
        never.setBackgroundColor(Color.rgb(0, 153, 153));
        occasionally.setBackgroundColor(Color.rgb(0, 153, 153));
        frequently.setBackgroundColor(Color.rgb(0, 153, 153));
        always.setBackgroundColor(Color.rgb(0, 153, 153));
        never.setActivated(true);
        occasionally.setActivated(true);
        frequently.setActivated(true);
        always.setActivated(true);
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