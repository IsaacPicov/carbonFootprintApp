package com.example.b07demosummer2024;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.example.b07demosummer2024.mvp.models.LoginModel;
import com.example.b07demosummer2024.mvp.presenters.LoginPresenter;
import com.example.b07demosummer2024.mvp.presenters.ForgotPasswordPresenter;
import com.example.b07demosummer2024.SurveyLandingPageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.LoginView, ForgotPasswordPresenter.ForgotPasswordView {
    private EditText editEmail, editPassword;
    
    private LoginPresenter loginPresenter;
    private ForgotPasswordPresenter forgotPasswordPresenter;

    private FirebaseDatabase db;
    private DatabaseReference itemsRef;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         editEmail = findViewById(R.id.editEmail);
         editPassword = findViewById(R.id.editPassword);

         ImageButton loginBackButton = findViewById(R.id.loginBackButton);
         Button buttonLogin = findViewById(R.id.buttonLogin);
         Button buttonForgotPassword = findViewById(R.id.buttonForgotPassword);

         loginPresenter = new LoginPresenter(this, new LoginModel());
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
        // Initialize FirebaseAuth and FirebaseDatabase
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://b07finalproject-4e3be-default-rtdb.firebaseio.com/");
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference itemsRef = db.getReference("users/" + userId + "/takenSurvey");

            itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Long value = dataSnapshot.getValue(Long.class); // Retrieve as String
                        if (value != null) {
                            navigateToActivity(value == 1 ? 1 : 0, userId);
                        } else {
                            Log.d("FirebaseCheck", "takenSurvey is null.");
                        }
                    } else {
                        Log.d("FirebaseCheck", "takenSurvey does not exist.");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("FirebaseCheck", "Error reading takenSurvey: ", databaseError.toException());
                }
            });
        } else {
            Log.e("FirebaseCheck", "No authenticated user.");
        }
    }

    // Helper function to navigate to an activity
    private void navigateToActivity(int indicator, String userId) {
        Intent intent;
        if (indicator == 1){
            intent = new Intent(this, EcoTrackerActivity.class);
        }
        else{
            intent = new Intent(this, SurveyLandingPageActivity.class);
        }

        intent.putExtra("USERID", userId);
        startActivity(intent);
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