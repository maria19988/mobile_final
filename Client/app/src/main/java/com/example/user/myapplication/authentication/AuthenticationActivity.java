package com.example.user.myapplication.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.user.myapplication.Main2Activity;
import com.example.user.myapplication.MainActivity;
import com.example.user.myapplication.R;
import com.example.user.myapplication.addJob;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.*;
import static java.security.AccessController.getContext;

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
                .addToBackStack(fragment_login.TAG)
                .commit();
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this, Main2Activity.class);
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
