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
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.user.myapplication.AuthenticationApiManager;
import com.example.user.myapplication.R;
import com.example.user.myapplication.base.BaseFragment;
import com.example.user.myapplication.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_register extends BaseFragment {
    public static final String TAG = fragment_register.class.getSimpleName();

    private RegisterFragmentListener listener;

    private AuthenticationApiManager authenticationApiManager;

    @BindView(R.id.name_holder)
    TextInputLayout nameHolder;

    @BindView(R.id.name)
    EditText nameEditText;

    @BindView(R.id.email_holder)
    TextInputLayout emailHolder;

    @BindView(R.id.email)
    EditText emailEditText;

    @BindView(R.id.password_holder)
    TextInputLayout passwordHolder;

    @BindView(R.id.password)
    EditText passwordEditText;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    public fragment_register() {
        // Required empty public constructor
    }

    public static fragment_register newInstance() {
        fragment_register fragment = new fragment_register();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationApiManager = AuthenticationApiManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registerfragment, container, false);
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
        if (context instanceof RegisterFragmentListener) {
            listener = (RegisterFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RegisterFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @OnClick(R.id.register)
    public void attemptRegister() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        boolean flag = true;

        if (TextUtils.isEmpty(name)) {
            nameHolder.setError("name field is required");
            flag = false;
        } else {
            nameHolder.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            emailHolder.setError("email field is required");
            flag = false;
        } else {
            emailHolder.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordHolder.setError("password field is required");
            flag = false;
        } else {
            passwordHolder.setError(null);
        }

        if (flag) {
            showProgressBar();
            User registerUser = new User(name, email, password);
            authenticationApiManager
                    .register(registerUser)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            hideProgressBar();
                            if (response.isSuccessful()) {
                                listener.onRegisterSuccess();
                            } else {
                                listener.onRegisterFailure();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            hideProgressBar();
                            listener.onRegisterFailure();
                            showToastMessage(t.getMessage());
                        }
                    });
        }
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public interface RegisterFragmentListener {
        void onRegisterSuccess();

        void onRegisterFailure();
    }
}
