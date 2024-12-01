package com.example.b07demosummer2024;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.b07demosummer2024.mvp.presenters.LoginPresenter;
import com.example.b07demosummer2024.mvp.presenters.ForgotPasswordPresenter;
import com.example.b07demosummer2024.SurveyLandingPageActivity;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.LoginView, ForgotPasswordPresenter.ForgotPasswordView {
    private EditText editEmail, editPassword;
    
    private LoginPresenter loginPresenter;
    private ForgotPasswordPresenter forgotPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         editEmail = findViewById(R.id.editEmail);
         editPassword = findViewById(R.id.editPassword);

         ImageButton loginBackButton = findViewById(R.id.loginBackButton);
         Button buttonLogin = findViewById(R.id.buttonLogin);
         Button buttonForgotPassword = findViewById(R.id.buttonForgotPassword);

         loginPresenter = new LoginPresenter(this);
         forgotPasswordPresenter = new ForgotPasswordPresenter(this);

         loginBackButton.setOnClickListener(v -> {
             finish();
         });

         buttonLogin.setOnClickListener(v -> {
             String email = editEmail.getText().toString().trim();
             String password = editPassword.getText().toString().trim();

             loginPresenter.handleLogin(email, password);
         });

         buttonForgotPassword.setOnClickListener(v -> {
             String email = editEmail.getText().toString().trim();

             editPassword.setText("");
             forgotPasswordPresenter.handleForgotPassword(email);
         });
    }

    private void loadSurveyActivity() {
         Intent intent = getIntent();
         String userID = intent.getStringExtra("USERID");
         Intent pass = new Intent(this, SurveyLandingPageActivity.class);
         pass.putExtra("USERID", userID);
         startActivity(pass);

         //other method? Untested
//        Intent intent = new Intent(this, SurveyLandingPageActivity.class);
//        startActivity(intent);
    }

    @Override
    public void showLoading() {
        // progress bar or other
    }

    @Override
    public void closeLoading() {
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
        loadSurveyActivity();
        editEmail.setText("");
        editPassword.setText("");
    }

    @Override
    public void onLoginFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onForgotPasswordSuccess() {
        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onForgotPasswordFail(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}