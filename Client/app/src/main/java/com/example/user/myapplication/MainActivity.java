package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.user.myapplication.authentication.AuthenticationActivity;
import com.example.user.myapplication.authentication.fragment_login;
import com.example.user.myapplication.authentication.fragment_register;
import com.example.user.myapplication.local.LocalStorageManager;
import com.example.user.myapplication.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements fragment_login.LoginFragmentListener, fragment_register.RegisterFragmentListener {

    /*private LocalStorageManager localStorageManager;
    private TextView nameHeaderTextView, emailHeaderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        localStorageManager = LocalStorageManager.getInstance(this);

    }*/
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
        Log.d("g", "njsj");
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
