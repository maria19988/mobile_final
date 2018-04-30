package com.example.user.myapplication;

import android.app.Application;

import com.example.user.myapplication.dao.DaoMaster;
import com.example.user.myapplication.dao.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * @author fouad
 */
public class MyApplication extends Application{
    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "events-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
