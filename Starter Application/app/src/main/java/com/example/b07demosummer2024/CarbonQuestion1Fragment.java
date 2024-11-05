package com.example.b07demosummer2024;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class CarbonQuestion1Fragment extends Fragment{
    private static final String QUESTION = "QUESTION PLACEHOLDER";
    private static final String BUTTON  = "BUTTON PLACEHOLDER";
    private static final String OPTION1 = "OPTION1 PLACEHOLDER";
    private static final String OPTION2 = "OPTION2 PLACEHOLDER";

//    This is for the RadioButtons
    private interface OnOptionSelectedListener{
        void onOptionSelected(String option);
    }

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

    }
}

