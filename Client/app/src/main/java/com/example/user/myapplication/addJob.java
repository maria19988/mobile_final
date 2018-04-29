package com.example.user.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.user.myapplication.authentication.fragment_register;
import com.example.user.myapplication.base.BaseFragment;
import com.example.user.myapplication.models.Job;
import com.example.user.myapplication.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class addJob extends BaseFragment {

    public static final String TAG = addJob.class.getSimpleName();

    private addJob.AddFragmentListener listener;

    private JobApiManager jobApiManager;

    @BindView(R.id.title_holder)
    TextInputLayout titleHolder;

    @BindView(R.id.title)
    EditText titleEditText;

    @BindView(R.id.description_holder)
    TextInputLayout descriptionHolder;

    @BindView(R.id.description)
    EditText descriptionEditText;

    @BindView(R.id.phone_holder)
    TextInputLayout phoneHolder;

    @BindView(R.id.phone)
    EditText phoneEditText;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    public addJob() {
        // Required empty public constructor
    }

    public static addJob newInstance() {
        addJob fragment = new addJob();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jobApiManager = JobApiManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_job, container, false);
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
        if (context instanceof addJob.AddFragmentListener) {
            listener = (addJob.AddFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AddFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @OnClick(R.id.add)
    public void attemptAdd() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        boolean flag = true;

        if (TextUtils.isEmpty(title)) {
            titleHolder.setError("title field is required");
            flag = false;
        } else {
            titleHolder.setError(null);
        }

        if (TextUtils.isEmpty(description)) {
            descriptionHolder.setError("description field is required");
            flag = false;
        } else {
            descriptionHolder.setError(null);
        }

        if (TextUtils.isEmpty(phone)) {
            phoneHolder.setError("phone field is required");
            flag = false;
        } else {
            phoneHolder.setError(null);
        }

        if (flag) {
            showProgressBar();
            Job newJob = new Job(title, description, phone);
            jobApiManager
                    .addJob(newJob)
                    .enqueue(new Callback<Job>() {
                        @Override
                        public void onResponse(Call<Job> call, Response<Job> response) {
                            hideProgressBar();
                            Log.d(TAG, "hellooo");
                            if (response.isSuccessful()) {
                                listener.onAddSuccess();
                                showToastMessage(titleHolder.getTransitionName());
                            } else {
                                listener.onAddFailure();
                            }
                        }

                        @Override
                        public void onFailure(Call<Job> call, Throwable t) {
                            hideProgressBar();
                            listener.onAddFailure();
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

    public interface AddFragmentListener {
        void onAddSuccess();

        void onAddFailure();
    }
}
