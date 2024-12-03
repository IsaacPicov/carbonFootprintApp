package com.example.b07demosummer2024.mvp.presenters;

import androidx.annotation.NonNull;

import com.example.b07demosummer2024.mvp.models.LoginModel;


public class LoginPresenter {
    public interface LoginView {
        void onLoginSuccess();
        void onLoginFail(String message);
    }

    private final LoginModel model;
    private final LoginView view;

    public LoginPresenter(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;
    }

    public void handleLogin(@NonNull String email, @NonNull String password) {
        if (email.isEmpty() || password.isEmpty()) {
            view.onLoginFail("Please fill out all fields");
            return;
        }

        model.authenticateUser(email, password, new LoginModel.LoginCallback() {
            public void onLoginSuccess() {view.onLoginSuccess();}
            public void onLoginFail(String errorMessage) {view.onLoginFail("An error occurred");}
        });
    }
}
