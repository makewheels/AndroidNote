package com.eg.note.database;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.eg.note.bean.DaoMaster;
import com.eg.note.bean.DaoSession;

public class GreenDaoApplication extends Application {
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    public static GreenDaoApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        createDatabase();
    }

    private void createDatabase() {
        MyOpenHelper helper = new MyOpenHelper(this, "notedb", null);
        SQLiteDatabase writableDatabase = helper.getWritableDatabase();
        daoMaster = new DaoMaster(writableDatabase);
        daoSession = daoMaster.newSession();
    }

    public static GreenDaoApplication getInstance() {
        return application;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
