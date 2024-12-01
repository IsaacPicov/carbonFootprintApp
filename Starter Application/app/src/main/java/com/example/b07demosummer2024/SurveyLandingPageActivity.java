package com.example.b07demosummer2024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SurveyLandingPageActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_landing_page);
    }

    public void next(View view){
        Intent intent = getIntent();
        String userID = intent.getStringExtra("USERID");
        Intent pass = new Intent(this, SurveyActivity.class);
        pass.putExtra("USERID", userID);
        startActivity(pass);
    }
}
