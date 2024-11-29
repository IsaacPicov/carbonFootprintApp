package com.example.b07demosummer2024.mvp.models;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordModel {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public ForgotPasswordModel() {
    }

    public interface ForgotPasswordCallback {
        void onForgotPasswordSuccess();

        void onForgotPasswordFail(String errorMessage);
    }

    public void sendPasswordReset(String email, final ForgotPasswordCallback callback) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onForgotPasswordSuccess();
                    } else {
                        callback.onForgotPasswordFail("An error occurred");
                    }
                });
    }
}
