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

//Housing Questions
public class FragmentQuestion11 extends Fragment {
    private SurveyResponseListner listner;
    private int questionID;
    //    Sets questionID for fragment which we use in the activity
    public FragmentQuestion11 (int questionID){
        this.questionID = questionID;
    }
    private Button house1;
    private Button house2;
    private Button house3;
    private Button house4;
    private Button other;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_question11, container, false);
//       Defines the buttons
        house1 = view.findViewById(R.id.house1);
        house2 = view.findViewById(R.id.house2);
        house3 = view.findViewById(R.id.house3);
        house4 = view.findViewById(R.id.house4);
        other = view.findViewById(R.id.other);

        //      sets the callbacks for the buttons
        house1.setOnClickListener(this::answer);
        house2.setOnClickListener(this::answer);
        house3.setOnClickListener(this::answer);
        house4.setOnClickListener(this::answer);
        other.setOnClickListener(this::answer);

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
        house1.setBackgroundColor(Color.MAGENTA);
        house2.setBackgroundColor(Color.MAGENTA);
        house3.setBackgroundColor(Color.MAGENTA);
        house4.setBackgroundColor(Color.MAGENTA);
        other.setBackgroundColor(Color.MAGENTA);

//      Reset the usability of all buttons, so user can press any other button
        house1.setActivated(true);
        house2.setActivated(true);
        house3.setActivated(true);
        house4.setActivated(true);
        other.setActivated(true);
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
