package com.example.b07demosummer2024.mvp.models;

import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginModel {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    
    public LoginModel() {
    }

    public interface LoginCallback {
        void onLoginSuccess();
        void onLoginFail(String errorMessage);
    }

    public void authenticateUser(@NonNull String email, @NonNull String password, final LoginCallback callback) {
        // Sidenote: Do not do exception handling (empty email or empty password) here, it will be done
        // in the Presenter which has access to the View and will show the error message to the user

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    callback.onLoginSuccess();
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        callback.onLoginFail("User does not exist");
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        callback.onLoginFail("Invalid password");
                    } catch (FirebaseAuthException e) {
                        callback.onLoginFail("An error occurred");
                    } catch (Exception e) {
                        callback.onLoginFail("An error occurred");
                    }
                }
            });
    }

    public void sendPasswordReset(String email, final LoginCallback callback) {
        auth.sendPasswordReset(email)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    callback.onLoginSuccess();
                } else {
                    callback.onLoginFail("An error occurred");
                }
            });
    }
}
