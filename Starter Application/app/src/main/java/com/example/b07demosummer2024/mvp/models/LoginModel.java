package com.example.b07demosummer2024.mvp.models;

import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginModel {
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    
    public LoginModel() {
    }

    public interface LoginCallback {
        void onLoginSuccess();
        void onLoginFailure(String errorMessage);
    }

    public void authenticateUser(@NonNull String email, @NonNull String password, final LoginCallback callback) {
        // Sidenote: Do not do exception handling (empty email or empty password) here, it will be done
        // in the Presenter which has access to the View and will show the error message to the user

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Login success
                    callback.onLoginSuccess();
                } else {
                    // Login failure, get and pass the error message
                    String errorMessage = "Authentication failed.";
                    if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                        errorMessage = "No account found with this email.";
                    } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        errorMessage = "Invalid password. Please try again.";
                    } else if (task.getException() != null) {
                        errorMessage = task.getException().getMessage();
                    }

                    callback.onLoginFailure(errorMessage);
                }
            });
    }

    public void sendPasswordResetEmail(String email, final LoginCallback callback) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    callback.onLoginSuccess();
                } else {
                    callback.onLoginFailure(task.getException().getMessage());
                }
            });
    }
}
