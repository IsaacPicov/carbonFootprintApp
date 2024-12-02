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


public class FragmentQuestion2 extends Fragment {
    private SurveyResponseListner listner;
    private int questionID;
//    Sets questionID for fragment which we use in the activity
    public FragmentQuestion2 (int questionID){
        this.questionID = questionID;
    }
    private Button gas;
    private Button diesel;
    private Button hybrid;
    private Button electric;
    private Button idk;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_question2, container, false);
//       Defines the buttons
        gas = view.findViewById(R.id.gasoline);
        diesel = view.findViewById(R.id.diesel);
        hybrid = view.findViewById(R.id.hybrid);
        electric = view.findViewById(R.id.electric);
        idk = view.findViewById(R.id.idk);
//      sets the callbacks for the buttons
        gas.setOnClickListener(this::answer);
        diesel.setOnClickListener(this::answer);
        hybrid.setOnClickListener(this::answer);
        electric.setOnClickListener(this::answer);
        idk.setOnClickListener(this::answer);
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
        gas.setBackgroundColor(Color.MAGENTA);
        diesel.setBackgroundColor(Color.MAGENTA);
        hybrid.setBackgroundColor(Color.MAGENTA);
        electric.setBackgroundColor(Color.MAGENTA);
        idk.setBackgroundColor(Color.MAGENTA);
//      Reset the usability of all buttons, so user can press any other button
        gas.setActivated(true);
        diesel.setActivated(true);
        hybrid.setActivated(true);
        electric.setActivated(true);
        idk.setActivated(true);
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
