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

public class FragmentQuestion4 extends Fragment {
    private SurveyResponseListner listner;
    private int questionID;
    //    Sets questionID for fragment which we use in the activity
    public FragmentQuestion4 (int questionID){
        this.questionID = questionID;
    }
    private Button never;
    private Button occasionally;
    private Button frequently;
    private Button always;
    //private Button idk;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_question4, container, false);
//       Defines the buttons
        never = view.findViewById(R.id.q4option1);
        occasionally = view.findViewById(R.id.q4option2);
        frequently = view.findViewById(R.id.q4option3);
        always = view.findViewById(R.id.q4option4);
//      sets the callbacks for the buttons
        never.setOnClickListener(this::answer);
        occasionally.setOnClickListener(this::answer);
        frequently.setOnClickListener(this::answer);
        always.setOnClickListener(this::answer);
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
        never.setBackgroundColor(Color.MAGENTA);
        occasionally.setBackgroundColor(Color.MAGENTA);
        frequently.setBackgroundColor(Color.MAGENTA);
        always.setBackgroundColor(Color.MAGENTA);

//      Reset the usability of all buttons, so user can press any other button
        never.setActivated(true);
        occasionally.setActivated(true);
        frequently.setActivated(true);
        always.setActivated(true);
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
