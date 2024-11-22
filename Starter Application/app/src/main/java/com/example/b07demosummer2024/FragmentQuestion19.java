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
public class FragmentQuestion19 extends Fragment {
    private SurveyResponseListner listner;
    private int questionID;
    public FragmentQuestion19 (int questionID){
        this.questionID = questionID;
    }
    private Button regularly;
    private Button occasionally;
    private Button no;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_question19, container, false);
        regularly = view.findViewById(R.id.q19option1);
        occasionally = view.findViewById(R.id.q19option2); // Change BUTTONS
        no = view.findViewById(R.id.q19option3);

        regularly.setOnClickListener(this::answer);
        occasionally.setOnClickListener(this::answer);
        no.setOnClickListener(this::answer);

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
        regularly.setBackgroundColor(Color.MAGENTA);
        occasionally.setBackgroundColor(Color.MAGENTA);
        no.setBackgroundColor(Color.MAGENTA);

        regularly.setActivated(true);
        occasionally.setActivated(true);
        no.setActivated(true);
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