package com.example.user.myapplication.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.user.myapplication.dao.EventDAO;
import com.example.user.myapplication.dao.DaoMaster;
import com.example.user.myapplication.dao.DaoSession;
import com.example.user.myapplication.dao.EventDAODao;
//import com.example.myevents.models.Event;
//import com.example.myevents.models.User;
//import com.example.myevents.models.dao.DaoSession;
//import com.example.myevents.models.dao.EventDAO;
//import com.example.myevents.models.dao.EventDAODao;
//import com.example.user.myapplication.dao.EventDAO;
import com.example.user.myapplication.models.Job;
import com.example.user.myapplication.models.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class LocalStorageManager {

    private Gson gson;
    private final String USER_KEY = "PROFILE";
    private static LocalStorageManager localStorageManager;
    private SharedPreferences sharedPreferences;

    private LocalStorageManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
    }

    public static LocalStorageManager getInstance(Context context) {
        if (localStorageManager == null) {
            localStorageManager = new LocalStorageManager(context);
        }
        return localStorageManager;
    }

    public User getUser() {
        String userJson = sharedPreferences.getString(USER_KEY, null);
        if (userJson == null) {
            return null;
        }
        try {
            return gson.fromJson(userJson, User.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void saveUser(User user) {
        String userJson = gson.toJson(user);
        sharedPreferences
                .edit()
                .putString(USER_KEY, userJson)
                .commit();
    }

    public void deleteUser() {
        sharedPreferences
                .edit()
                .putString(USER_KEY, null)
                .commit();
    }

    public void saveJobsInLocalDatabase(DaoSession daoSession, List<Job> jobs) {
        EventDAODao eventDao = daoSession.getEventDAODao();
        for (int i = 0; i < jobs.size(); i++) {
            EventDAO eventdao = new EventDAO();
            eventdao.setId(jobs.get(i).getId());
            eventdao.setTitle(jobs.get(i).getTitle());
            eventdao.setDescription(jobs.get(i).getDescription());
            eventdao.setPhone(jobs.get(i).getPhone());
            eventDao.insertOrReplace(eventdao);
        }
    }

    public List<Job> getJobsInLocalDatabase(DaoSession daoSession) {
        EventDAODao eventDao = daoSession.getEventDAODao();
        List<EventDAO> eventDAOS = eventDao.loadAll();
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < eventDAOS.size(); i++) {
            jobs.add(new Job(eventDAOS.get(i).getTitle(), eventDAOS.get(i).getDescription(), eventDAOS.get(i).getPhone()));
        }
        return jobs;
    }


}
