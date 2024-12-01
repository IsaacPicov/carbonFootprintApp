package com.example.b07demosummer2024;

import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.b07demosummer2024.mvp.models.LoginModel;
import com.example.b07demosummer2024.mvp.presenters.LoginPresenter;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


public class ExampleUnitTest {





        @Mock
        private LoginPresenter.LoginView mockView;

        @Mock
        private LoginModel mockModel;

        private LoginPresenter presenter;


        @Test
        public void testHandleLogin_EmptyFields_ShowsError() {

            String email = "";
            String password = "";


            presenter.handleLogin(email, password);


            verify(mockView).onLoginFail("Please fill out all fields");

        }

        @Test
        public void testHandleLogin_ValidCredentials_Success() {

            String email = "test@example.com";
            String password = "password123";

            // LoginModel.LoginCallback callback = new LoginModel.LoginCallback()





            presenter.handleLogin(email, password);


            verify(mockView).showLoading();
            verify(mockView).closeLoading();
            verify(mockView).onLoginSuccess();
        }

        @Test
        public void testHandleLogin_InvalidCredentials_ShowsError() {

            String email = "test@example.com";
            String password = "wrongpassword";

            // LoginModel.LoginCallback callback = LoginModel.LoginCallback.class;





            presenter.handleLogin(email, password);


            verify(mockView).showLoading();
            verify(mockView).closeLoading();
            verify(mockView).onLoginFail("An error occurred");
        }


    }


