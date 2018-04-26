package com.example.user.myapplication.base;

import android.app.Fragment;
import android.widget.Toast;

import com.example.user.myapplication.models.ApiError;
import com.google.gson.Gson;

public class BaseFragment extends Fragment
{
    private Gson gson = new Gson();

    public void showToastMessage(String text) {
        //Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public ApiError parseApiErrorString(String error) {
        return gson.fromJson(error, ApiError.class);
    }
}
