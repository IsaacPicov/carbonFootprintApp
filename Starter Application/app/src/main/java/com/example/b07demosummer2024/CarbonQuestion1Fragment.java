package com.example.b07demosummer2024;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;



public class CarbonQuestion1Fragment extends Fragment implements OnOptionSelectedListener{
    private static final String QUESTION = "QUESTION PLACEHOLDER";
    private static final String BUTTON  = "BUTTON PLACEHOLDER";
    private static final String OPTION1 = "OPTION1 PLACEHOLDER";
    private static final String OPTION2 = "OPTION2 PLACEHOLDER";
    private String selectedOption;



//    Generates CarbonQuestion1 Fragment
    public static CarbonQuestion1Fragment newQ1 (String question, String button, String option1, String option2){
        CarbonQuestion1Fragment fragment = new CarbonQuestion1Fragment();
        Bundle args = new Bundle();
        args.putString(QUESTION, question);
        args.putString(BUTTON, button);
        args.putString(OPTION1, option1);
        args.putString(OPTION2, option2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_carbon_question_1, container, false);
//       Getting values passed from constructor
        String questionText, option1, option2;
        if (getArguments() == null){
            questionText = "";
            option1 = "";
            option2 = "";
        }
        else{
            questionText = getArguments().getString(QUESTION);
            option1 = getArguments().getString(OPTION1);
            option2 = getArguments().getString(OPTION2);
        }
//        For the text at the top of the screen
        TextView questionTextView = view.findViewById(R.id.textView3);
        questionTextView.setText(questionText);
//        For the radio Buttons
        RadioButton button1 = view.findViewById(R.id.radio_button1);
        RadioButton button2 = view.findViewById(R.id.radio_button2);
        button1.setText(option1);
        button2.setText(option2);
//        For the radiogroup
        RadioGroup radioGroup = view.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_button1){
                    selectedOption = button1.getText().toString();
                }
                else if (checkedId == R.id.radio_button2){
                    selectedOption = button2.getText().toString();
                }

            }
        });


//       For the next button
        Button nextButton = view.findViewById(R.id.button);
        return view;
    }

    @Override
    public void onOptionSelected(String option) {

    }
}

