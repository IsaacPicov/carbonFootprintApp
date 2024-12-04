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

//Short Term Flights Questions
public class FragmentQuestion6 extends Fragment {
    private SurveyResponseListner listner;
    private int questionID;
    //    Sets questionID for fragment which we use in the activity
    public FragmentQuestion6 (int questionID){
        this.questionID = questionID;
    }
    private Button None;
    private Button flights1;
    private Button flights2;
    private Button flights3;
    private Button flights4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_question6, container, false);
//       Defines the buttons
        None = view.findViewById(R.id.q6option1);
        flights1 = view.findViewById(R.id.q6option2);
        flights2 = view.findViewById(R.id.q6option3);
        flights3 = view.findViewById(R.id.q6option4);
        flights4 = view.findViewById(R.id.q6option5);
//      sets the callbacks for the buttons
        None.setOnClickListener(this::answer);
        flights1.setOnClickListener(this::answer);
        flights2.setOnClickListener(this::answer);
        flights3.setOnClickListener(this::answer);
        flights4.setOnClickListener(this::answer);
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
        None.setBackgroundColor(Color.rgb(0, 153, 153));
        flights1.setBackgroundColor(Color.rgb(0, 153, 153));
        flights2.setBackgroundColor(Color.rgb(0, 153, 153));
        flights3.setBackgroundColor(Color.rgb(0, 153, 153));
        flights4.setBackgroundColor(Color.rgb(0, 153, 153));
//      Reset the usability of all buttons, so user can press any other button
        None.setActivated(true);
        flights1.setActivated(true);
        flights2.setActivated(true);
        flights3.setActivated(true);
        flights4.setActivated(true);
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
