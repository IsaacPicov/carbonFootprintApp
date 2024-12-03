package com.example.b07demosummer2024;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;



public class FragmentQuestion5 extends Fragment {
    private SurveyResponseListner listner;
    private int questionID;
    public FragmentQuestion5 (int questionID){
        this.questionID = questionID;
    }
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private Button option5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_question5, container, false);
        option1 = view.findViewById(R.id.q5option1);
        option2 = view.findViewById(R.id.q5option2);
        option3 = view.findViewById(R.id.q5option3);
        option4 = view.findViewById(R.id.q5option4);
        option5 = view.findViewById(R.id.q5option5);
        option1.setOnClickListener(this::answer);
        option2.setOnClickListener(this::answer);
        option3.setOnClickListener(this::answer);
        option4.setOnClickListener(this::answer);
        option5.setOnClickListener(this::answer);
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
        option1.setBackgroundColor(Color.rgb(0, 153, 153));
        option2.setBackgroundColor(Color.rgb(0, 153, 153));
        option3.setBackgroundColor(Color.rgb(0, 153, 153));
        option4.setBackgroundColor(Color.rgb(0, 153, 153));
        option5.setBackgroundColor(Color.rgb(0, 153, 153));
        option1.setActivated(true);
        option2.setActivated(true);
        option3.setActivated(true);
        option4.setActivated(true);
        option5.setActivated(true);
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
