package com.example.b07demosummer2024;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.b07demosummer2024.mvp.presenters.LoginPresenter;

public class LoginFragment extends Fragment implements LoginPresenter.LoginView {
    private EditText editEmail, editPassword;
    private LoginPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        editEmail = view.findViewById(R.id.editEmail);
        editPassword = view.findViewById(R.id.editPassword);
        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        Button buttonForgotPassword = view.findViewById(R.id.buttonForgotPassword);

        presenter = new LoginPresenter(this);

        buttonLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            presenter.handleLogin(email, password);
        });

        buttonForgotPassword.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            presenter.handleForgotPassword(email);
        });

        return view;
    }

    @Override
    public void showLoading() {
        // progress bar or other
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
        
        // move back to the homepage somehow
    }

    @Override
    public void onLoginFail(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
