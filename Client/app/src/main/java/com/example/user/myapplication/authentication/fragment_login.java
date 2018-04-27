package com.example.user.myapplication.authentication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.myapplication.AuthenticationApiManager;
import com.example.user.myapplication.R;
import com.example.user.myapplication.base.BaseFragment;
import com.example.user.myapplication.local.LocalStorageManager;
import com.example.user.myapplication.models.ApiError;
import com.example.user.myapplication.models.User;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_login extends BaseFragment {
    private LoginFragmentListener listener;
    private AuthenticationApiManager authenticationApiManager;
    private LocalStorageManager localStorageManager;

    @BindView(R.id.email_holder)
    TextInputLayout emailHolder;

    @BindView(R.id.email)
    EditText emailEditText;

    @BindView(R.id.password_holder)
    TextInputLayout passwordHolder;

    @BindView(R.id.password)
    EditText passwordEditText;

    @BindView(R.id.login)
    Button loginButton;

    @BindView(R.id.register)
    TextView registerTextView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public fragment_login() {
        // Required empty public constructor
    }

    public static fragment_login newInstance() {
        fragment_login fragment = new fragment_login();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationApiManager = AuthenticationApiManager.getInstance();
        localStorageManager = LocalStorageManager.getInstance(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loginfragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragmentListener) {
            listener = (LoginFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LoginFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @OnClick(R.id.login)
    public void attemptLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        boolean flag = true;

        if (TextUtils.isEmpty(email)) {
            emailHolder.setError("email is required");
            flag = false;
        } else {
            emailHolder.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(password)) {
            passwordHolder.setError("password is required");
            flag = false;
        } else {
            passwordHolder.setErrorEnabled(false);
        }

        if (flag) {
            showProgressBar();
            User loginUser = new User(email, password);
            authenticationApiManager.login(loginUser).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    hideProgressBar();
                    if (response.isSuccessful()) {
                        User apiUser = response.body();
                        localStorageManager.saveUser(apiUser);
                        listener.onLoginSuccess();
                    } else {
                        try {
                            String errorString = response.errorBody().string();
                            ApiError error = parseApiErrorString(errorString);
                            showToastMessage(error.getMessage());
                            listener.onLoginFailure();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    hideProgressBar();
                    listener.onLoginFailure();
                    showToastMessage(t.getMessage());
                }
            });
        }
    }

    @OnClick(R.id.register)
    public void requestRegister() {
        listener.onRequestRegister();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public interface LoginFragmentListener {

        void onLoginSuccess();

        void onLoginFailure();

        void onRequestRegister();
    }
}
