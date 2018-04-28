package com.example.user.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.user.myapplication.addJob;
import com.example.user.myapplication.base.BaseFragment;

public class Main2Activity extends AppCompatActivity implements addJob.AddFragmentListener{
    String hello = "hio";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addJobFragment();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                addJobFragment();
            }
        });
        addJobFragment();
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
                .commit();
    }

    @Override
    public void onAddSuccess() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onAddFailure() {


    }


}
