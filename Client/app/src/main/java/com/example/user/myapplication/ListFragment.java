package com.example.user.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.myapplication.authenticated.AuthenticatedApiManager;
import com.example.user.myapplication.base.BaseFragment;
import com.example.user.myapplication.dao.DaoSession;
import com.example.user.myapplication.local.LocalStorageManager;
import com.example.user.myapplication.models.ApiError;
import com.example.user.myapplication.models.Job;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListFragment extends BaseFragment {

    private ListFragmentListener lListener;
    private JobApiManager jobApiManager;
    private LocalStorageManager localStorageManager;

    @BindView(R.id.list)
    RecyclerView jobsList;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jobApiManager = JobApiManager.getInstance();
        localStorageManager = LocalStorageManager.getInstance(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        showProgressBar();
        fetchAllJobs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        jobsList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void showJobsList(List<Job> jobs) {
        jobsList.setAdapter(new JobsListAdapter(jobs));
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void fetchAllJobs() {
        jobApiManager.getJobs()
                .enqueue(new Callback<Job>() {
                    @Override
                    public void onResponse(Call<Job> call, Response<Job> response) {
                        hideProgressBar();
                        if(response.isSuccessful()){
                            List<Job> jobs = (List<Job>) response.body();
                            showJobsList(jobs);
                        }else{
                            try {
                                String errorJson = response.errorBody().string();
                                ApiError apiError = parseApiErrorString(errorJson);
                                //actBasedOnApiErrorCode(apiError);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Job> call, Throwable t) {
                        hideProgressBar();
                    }
                });


    }

    /*private void saveEventsInLocalDatabase(List<Job> events) {
        DaoSession daoSession = ((MyApplication) (getActivity().getApplication())).getDaoSession();
        localStorageManager.saveEventsInLocalDatabase(daoSession, events);
    }*/

    /*private void getEventsInLocalDatabase() {
        DaoSession daoSession = ((MyApplication) (getActivity().getApplication())).getDaoSession();
        List<Event> eventsFromDatabase = localStorageManager.getEventsInLocalDatabase(daoSession);
        showEventsList(eventsFromDatabase);
    }
*/
    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.new_event)
    public void requestShowNewJobPage() {
        if (lListener != null) {
            lListener.onRequestCreateNewJob();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListFragmentListener) {
            lListener = (ListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EventsListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        lListener = null;
    }

   // @Override
   /* public void notLoggedInAnymore() {
        if (lListener != null) {
            lListener.onErrorFetchingEvents();
        }
    }
*/
    class JobsListAdapter extends RecyclerView.Adapter<JobsListAdapter.ViewHolder> {

        private List<Job> jobs;

        JobsListAdapter(List<Job> jobs) {
            this.jobs = jobs;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_activity, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Job job = jobs.get(position);
            holder.titleTextView.setText(job.getTitle());
            holder.descriptionTextView.setText(job.getDescription());
            holder.phoneTextView.setText(job.getPhone());
        }

        @Override
        public int getItemCount() {
            return jobs.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.title_holder)
            TextView titleTextView;
            @BindView(R.id.description_holder)
            TextView descriptionTextView;
            @BindView(R.id.phone_holder)
            TextView phoneTextView;
            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


    public interface ListFragmentListener {
        void onRequestCreateNewJob();

        void onErrorFetchingJobs();
    }
}
