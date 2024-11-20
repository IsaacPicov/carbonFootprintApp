package com.example.b07demosummer2024.mvp.presenters;

import androidx.annotation.NonNull;

import com.example.b07demosummer2024.mvp.models.ForgotPasswordModel;

public class ForgotPasswordPresenter {
    public interface ForgotPasswordView {
        void showLoading();
        void closeLoading();
        void onForgotPasswordSuccess();
        void onForgotPasswordFail(String message);
    }

    private final ForgotPasswordModel model;
    private final ForgotPasswordView view;

    public ForgotPasswordPresenter(ForgotPasswordView view) {
        this.view = view;
        this.model = new ForgotPasswordModel();
    }

    public void handleForgotPassword(@NonNull String email) {
        if (email.isEmpty()) {
            view.onForgotPasswordFail("Please enter your email address");
            return;
        }

        view.showLoading();
        model.sendPasswordReset(email, new ForgotPasswordModel.ForgotPasswordCallback() {
            @Override
            public void onForgotPasswordSuccess() {
                view.closeLoading();
                view.onForgotPasswordSuccess();
            }

            @Override
            public void onForgotPasswordFail(String errorMessage) {
                view.closeLoading();
                view.onForgotPasswordFail("An error occurred");
            }
        });
    }
}
