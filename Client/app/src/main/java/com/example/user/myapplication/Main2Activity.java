package com.example.user.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.myapplication.addJob;
import com.example.user.myapplication.authentication.fragment_login;
import com.example.user.myapplication.authentication.fragment_register;
import com.example.user.myapplication.base.BaseFragment;
import com.example.user.myapplication.local.LocalStorageManager;
import com.example.user.myapplication.models.Job;
import com.example.user.myapplication.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity implements addJob.AddFragmentListener,  ListFragment.ListFragmentListener {
    String hello = "hio";
    LocalStorageManager localStorageManager;
    AuthenticationApiManager authenticationApiManager;
    public static final String TAG = Main2Activity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Button button = findViewById(R.id.button);

        displayJobs();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addJobFragment();
                Snackbar.make(view, "Add Job", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                logout();
            }

        });

    }



    private void saveJob()
    {
        User user = localStorageManager.getUser();
        String title = findViewById(R.id.title_holder).toString();
        String description = findViewById(R.id.description_holder).toString();
        String phone = findViewById(R.id.phone_holder).toString();
        Job newJob = new Job(title, description, phone);

        User newUser = new User(user.getName(), user.getEmail(), user.getPassword());
        authenticationApiManager
                .save(newUser, newJob)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "success");
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
    }

    private void logout() {
        LocalStorageManager.getInstance(this).deleteUser();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void displayJobs() {
        ListFragment fragment = ListFragment.newInstance();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();


    }

    public static Main2Activity newInstance() {
        Main2Activity fragment = new Main2Activity();
        return fragment;
    }

    private void addJobFragment() {
        addJob fragment = addJob.newInstance();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(addJob.TAG)
                .commit();
    }

    @Override
    public void onAddSuccess() {
        getFragmentManager().popBackStack();
    }
    @Override
    public void onAddFailure() {


    }


    @Override
    public void onRequestCreateNewJob() {

    }

    @Override
    public void onErrorFetchingJobs() {

    }

}
