package com.example.b07demosummer2024;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.b07demosummer2024.mvp.models.LoginModel;
import com.example.b07demosummer2024.mvp.presenters.LoginPresenter;
import org.mockito.runners.MockitoJUnitRunner;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Mock
    private LoginActivity view;

    @Mock
    private LoginModel model;

    @Test
    public void testEmptyInfo(){
        LoginPresenter presenter = new LoginPresenter(view);
        presenter.handleLogin("", "");
        verify(view).onLoginFail("Please fill out all fields");
    }

    @Test
    public void testSuccessLogin() {
        LoginPresenter presenter = new LoginPresenter(view);
        presenter.handleLogin("email", "pwd");
        verify(view).showLoading();


    }








    }


