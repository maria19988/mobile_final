package com.example.user.myapplication.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.user.myapplication.MainActivity;
import com.example.user.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthenticationActivity extends AppCompatActivity implements fragment_login.LoginFragmentListener, fragment_register.RegisterFragmentListener
{
    @BindView(R.id.container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addLoginFragment();
    }

    private void addLoginFragment() {
        fragment_login fragment = fragment_login.newInstance();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailure() {

    }

    @Override
    public void onRequestRegister() {
        fragment_register fragment = fragment_register.newInstance();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, fragment_register.TAG)
                .addToBackStack(fragment_register.TAG)
                .commit();
    }

    @Override
    public void onRegisterSuccess() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onRegisterFailure() {

    }
}
